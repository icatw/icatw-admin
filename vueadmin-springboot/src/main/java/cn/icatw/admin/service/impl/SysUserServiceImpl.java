package cn.icatw.admin.service.impl;

import cn.icatw.admin.domain.SysMenu;
import cn.icatw.admin.domain.SysRole;
import cn.icatw.admin.domain.SysUser;
import cn.icatw.admin.mapper.SysUserMapper;
import cn.icatw.admin.service.SysMenuService;
import cn.icatw.admin.service.SysRoleService;
import cn.icatw.admin.service.SysUserService;
import cn.icatw.admin.utils.RedisUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * (SysUser)表服务实现类
 *
 * @author icatw
 * @since 2022-05-04 19:24:33
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    SysRoleService sysRoleService;
    @Autowired
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
}

