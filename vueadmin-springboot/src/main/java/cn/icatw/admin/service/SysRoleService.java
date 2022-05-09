package cn.icatw.admin.service;

import cn.icatw.admin.domain.SysRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.List;

/**
 * (SysRole)表服务接口
 *
 * @author icatw
 * @since 2022-05-04 19:24:32
 */
public interface SysRoleService extends IService<SysRole> {
    /**
     * 根据用户id得到角色list
     *
     * @param id id
     * @return {@link List}<{@link SysRole}>
     */
    List<SysRole> listRolesByUserId(Serializable id);

    /**
     * 分页列表查询
     *
     * @param name    名字
     * @param current 当前
     * @param size    大小
     * @return {@link Page}<{@link SysRole}>
     */
    Page<SysRole> pageList(String name, int current, int size);

    /**
     * 通过主键id查询
     *
     * @param id id
     * @return {@link SysRole}
     */
    SysRole selectById(Serializable id);

    /**
     * 更新角色信息
     *
     * @param sysRole 系统作用
     * @return boolean
     */
    boolean updateRole(SysRole sysRole);

    /**
     * 删除
     *
     * @param ids id
     */
    void delete(List<Long> ids);

    /**
     * 分配权限
     *
     * @param roleId  角色id
     * @param menuIds 菜单id
     */
    void assignPermissions(Long roleId, Long[] menuIds);
}

