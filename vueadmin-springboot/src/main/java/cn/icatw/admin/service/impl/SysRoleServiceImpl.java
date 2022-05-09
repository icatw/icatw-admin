package cn.icatw.admin.service.impl;

import cn.icatw.admin.common.ResultStatusEnum;
import cn.icatw.admin.common.exception.CustomException;
import cn.icatw.admin.domain.SysRole;
import cn.icatw.admin.domain.SysRoleMenu;
import cn.icatw.admin.domain.SysUserRole;
import cn.icatw.admin.mapper.SysRoleMapper;
import cn.icatw.admin.service.SysRoleMenuService;
import cn.icatw.admin.service.SysRoleService;
import cn.icatw.admin.service.SysUserRoleService;
import cn.icatw.admin.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (SysRole)表服务实现类
 *
 * @author icatw
 * @since 2022-05-04 19:24:32
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    SysRoleMenuService sysRoleMenuService;
    @Autowired
    @Lazy
    SysUserService sysUserService;
    @Autowired
    SysUserRoleService sysUserRoleService;

    @Override
    public List<SysRole> listRolesByUserId(Serializable id) {
        return this.list(new QueryWrapper<SysRole>()
                .inSql("id", "select role_id from sys_user_role where user_id =" + id));
    }

    @Override
    public Page<SysRole> pageList(String name, int current, int size) {
        QueryWrapper<SysRole> wrapper = new QueryWrapper<SysRole>()
                .like(StringUtils.isNotBlank(name), "name", name);
        //if (name != null && !"".equals(name)) {
        //    wrapper = new QueryWrapper<SysRole>().like("name", name);
        //}
        Page<SysRole> page = new Page<>(current, size);
        return this.page(page, wrapper);
    }

    @Override
    public SysRole selectById(Serializable id) {
        SysRole sysRole = this.getById(id);
        //获取角色相关联的menuIds
        List<SysRoleMenu> roleMenus = sysRoleMenuService.list(new QueryWrapper<SysRoleMenu>().eq("role_id", sysRole.getId()));
        List<Long> menuIds = roleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
        sysRole.setMenuIds(menuIds);
        return sysRole;
    }

    @Override
    public boolean updateRole(SysRole sysRole) {
        if ("超级管理员".equals(sysRole.getName()) || "admin".equals(sysRole.getCode())) {
            throw new CustomException(400, "不可更改超级管理员信息！");
        }

        sysRole.setUpdated(LocalDateTime.now());
        //同步删除缓存权限信息
        sysUserService.clearUserAuthorityInfoByRoleId(sysRole.getId());
        try {
            this.updateById(sysRole);
        } catch (Exception e) {
            throw new CustomException(400, "请检查名称和唯一编码！");
        }

        return true;
    }

    @Override
    public void delete(List<Long> ids) {
        //查询管理员信息重复，可抽取出方法，或者存入本地线程池中方便使用
        SysRole admin = this.getOne(new QueryWrapper<SysRole>().eq("name", "超级管理员"));
        for (Long id : ids) {
            if (id.equals(admin.getId())) {
                throw new CustomException(400, "不可删除超级管理员信息！");
            }
        }
        try {
            this.removeByIds(ids);
            //同步删除中间表记录
            sysUserRoleService.remove(new QueryWrapper<SysUserRole>()
                    .in("role_id", ids));
            sysRoleMenuService.remove(new QueryWrapper<SysRoleMenu>()
                    .in("role_id", ids));
            //同步删除缓存权限信息
            for (Long id : ids) {
                sysUserService.clearUserAuthorityInfoByRoleId(id);
            }
        } catch (Exception e) {
            throw new CustomException(ResultStatusEnum.SYSTEM_EXCEPTION);
        }

    }

    @Override
    public void assignPermissions(Long roleId, Long[] menuIds) {
        //此处为了方便直接将管理员id写死，实际开发中不能这样！
        SysRole admin = this.getOne(new QueryWrapper<SysRole>().eq("name", "超级管理员"));
        if (roleId.equals(admin.getId())) {
            throw new CustomException(400, "不可更改超级管理员信息！");
        }

        try {
            ArrayList<SysRoleMenu> roleMenus = new ArrayList<>();
            Arrays.stream(menuIds).forEach(menuId -> {
                SysRoleMenu roleMenu = new SysRoleMenu();
                roleMenu.setMenuId(menuId);
                roleMenu.setRoleId(roleId);
                roleMenus.add(roleMenu);
            });
            //先删除原来的记录再保存新记录
            sysRoleMenuService.remove(new QueryWrapper<SysRoleMenu>().eq("role_id", roleId));
            sysRoleMenuService.saveBatch(roleMenus);
            //同步删除缓存
            sysUserService.clearUserAuthorityInfoByRoleId(roleId);
        } catch (Exception e) {
            throw new CustomException(ResultStatusEnum.SYSTEM_EXCEPTION);
        }

    }
}

