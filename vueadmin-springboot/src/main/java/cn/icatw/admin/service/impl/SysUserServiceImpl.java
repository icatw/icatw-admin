package cn.icatw.admin.service.impl;

import cn.icatw.admin.common.Const;
import cn.icatw.admin.common.ResultStatusEnum;
import cn.icatw.admin.common.exception.CustomException;
import cn.icatw.admin.common.vo.PassVo;
import cn.icatw.admin.common.vo.UserVo;
import cn.icatw.admin.domain.SysMenu;
import cn.icatw.admin.domain.SysRole;
import cn.icatw.admin.domain.SysUser;
import cn.icatw.admin.domain.SysUserRole;
import cn.icatw.admin.mapper.SysUserMapper;
import cn.icatw.admin.service.SysMenuService;
import cn.icatw.admin.service.SysRoleService;
import cn.icatw.admin.service.SysUserRoleService;
import cn.icatw.admin.service.SysUserService;
import cn.icatw.admin.utils.RedisUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (SysUser)表服务实现类
 *
 * @author icatw
 * @since 2022-05-04 19:24:33
 */
@Service("sysUserService")
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    SysUserRoleService sysUserRoleService;

    @Autowired
    @Lazy
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    @Lazy
    SysMenuService sysMenuService;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public SysUser getByUsername(String username) {
        return getOne(new QueryWrapper<SysUser>().eq("username", username));
    }

    @Override
    public String getUserAuthorityInfo(Long userId) {
        SysUser user = this.baseMapper.selectById(userId);
        if (redisUtil.hasKey("GrantedAuthority:" + user.getUsername())) {
            return (String) redisUtil.get("GrantedAuthority:" + user.getUsername());
        }
        String authority = "";
        //获取角色
        //select * from sys_role where id in ( select role_id from sys_user_role where userid = #)
        List<SysRole> roles = sysRoleService.list(new QueryWrapper<SysRole>()
                .inSql("id", "select role_id from sys_user_role where user_id =" + userId));
        if (roles.size() > 0) {
            String roleCodes = roles.stream().map(r -> "ROLE_" + r.getCode()).collect(Collectors.joining(","));
            authority = roleCodes.concat(",");
        }
        //获取菜单操作编码
        List<Long> menuIds = this.baseMapper.getNavMenuIds(userId);
        if (menuIds.size() > 0) {
            List<SysMenu> sysMenus = sysMenuService.listByIds(menuIds);
            String menuPerms = sysMenus.stream().map(SysMenu::getPerms).collect(Collectors.joining(","));
            authority = authority.concat(menuPerms);
        }
        //存入缓存
        redisUtil.set("GrantedAuthority:" + user.getUsername(), authority, 60 * 60);
        return authority;
    }

    @Override
    public void clearUserAuthorityInfo(String username) {
        redisUtil.del("GrantedAuthority:" + username);
    }

    @Override
    public void clearUserAuthorityInfoByRoleId(Long roleId) {
        List<SysUser> users = this.list(new QueryWrapper<SysUser>()
                .inSql("id", "select user_id from sys_user_role where role_id =" + roleId));
        users.forEach(u -> {
            this.clearUserAuthorityInfo(u.getUsername());
        });
    }

    @Override
    public void clearUserAuthorityInfoByMenuId(Long menuId) {
        List<SysUser> sysUsers = this.baseMapper.listByMenuId(menuId);
        sysUsers.forEach(u -> {
            this.clearUserAuthorityInfo(u.getUsername());
        });
    }

    @Override
    public List<Long> getNavMenuIds(Long userId) {
        return this.baseMapper.getNavMenuIds(userId);
    }

    @Override
    public Page<SysUser> listPage(String name, int current, int size) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<SysUser>()
                .like(StringUtils.isNotBlank(name), "username", name);
        Page<SysUser> page = new Page<>(current, size);
        Page<SysUser> userPage = this.page(page, wrapper);
        //查出分页数据之后遍历设置角色信息
        userPage.getRecords().forEach(u -> {
            u.setRoles(sysRoleService.listRolesByUserId(u.getId()));
        });
        return userPage;
    }

    @Override
    public UserVo getCurrentUser(Principal principal) {
        SysUser user = this.getByUsername(principal.getName());
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }

    @Override
    public SysUser getUserAndRolesByUserId(Serializable id) {
        //一个用户有多个角色
        SysUser user = this.getById(id);
        List<SysRole> roles = sysRoleService.listRolesByUserId(user.getId());
        user.setRoles(roles);
        return user;
    }

    @Override
    public boolean saveUser(SysUser sysUser) {
        sysUser.setCreated(LocalDateTime.now());
        //初始密码
        String initialPassword = bCryptPasswordEncoder.encode(Const.INITIAL_PASSWORD);
        sysUser.setPassword(initialPassword);
        //默认头像
        sysUser.setAvatar(Const.INITIAL_AVATAR);
        try {
            save(sysUser);
        } catch (Exception e) {
            throw new CustomException(ResultStatusEnum.USER_EXISTS);
        }

        return true;
    }

    @Override
    public boolean resetPassword(Long userId) {
        SysUser user = this.getById(userId);
        user.setPassword(bCryptPasswordEncoder.encode(Const.INITIAL_PASSWORD));
        user.setUpdated(LocalDateTime.now());
        return this.updateById(user);
    }

    @Override
    public void updatePassword(PassVo passVo, Principal principal) {
        String username = principal.getName();
        SysUser user = this.getByUsername(username);
        //将旧密码加密
        log.info("------------------------------------------------------");
        log.info("输入的旧密码为：" + passVo.getOldPassword());
        log.info("------------------------------------------------------");
        log.info("输入的新密码为：" + passVo.getNewPassword());
        log.info("------------------------------------------------------");

        //传入的旧密码跟数据库密码对比，错误则返回错误信息，正确则修改密码；
        boolean b = bCryptPasswordEncoder.matches(passVo.getOldPassword(), user.getPassword());
        if (!b) {
            throw new CustomException(ResultStatusEnum.PASSWORD_ERROR);
        } else {
            String newPass = bCryptPasswordEncoder.encode(passVo.getNewPassword());
            user.setPassword(newPass);
            user.setUpdated(LocalDateTime.now());
            //更新信息
            this.updateById(user);
        }
    }

    @Override
    public boolean updateByUser(SysUser sysUser) {
        SysUser user = getByUsername(sysUser.getUsername());
        if (user != null) {
            throw new CustomException(ResultStatusEnum.USER_EXISTS);
        }
        sysUser.setUpdated(LocalDateTime.now());
        return this.updateById(sysUser);
    }

    @Override
    public void delete(List<Long> ids) {
        this.removeByIds(ids);
        //同步删除中间表数据
        sysUserRoleService.remove(new QueryWrapper<SysUserRole>()
                .in("user_id", ids));
    }

    @Override
    public void assignPermissions(Long userId, List<Long> roleIds) {

        //更新中间表信息
        ArrayList<SysUserRole> sysUserRoles = new ArrayList<>();
        for (Long roleId : roleIds) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(roleId);
            sysUserRole.setUserId(userId);
            sysUserRoles.add(sysUserRole);
        }
        try {
            sysUserRoleService.remove(new QueryWrapper<SysUserRole>()
                    .eq("user_id", userId));
            sysUserRoleService.saveBatch(sysUserRoles);
            //同时删除缓存
            SysUser user = this.getById(userId);
            this.clearUserAuthorityInfo(user.getUsername());
        } catch (Exception e) {
            throw new CustomException(ResultStatusEnum.SYSTEM_EXCEPTION);
        }
    }
}

