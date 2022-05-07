package cn.icatw.admin.service;

import cn.icatw.admin.common.vo.PassVo;
import cn.icatw.admin.common.vo.UserVo;
import cn.icatw.admin.domain.SysUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.security.Principal;
import java.util.List;

/**
 * (SysUser)表服务接口
 *
 * @author icatw
 * @since 2022-05-04 19:24:33
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 根据用户名查询
     *
     * @param username 用户名
     * @return {@link SysUser}
     */
    SysUser getByUsername(String username);

    /**
     * 获取用户权限信息
     *
     * @param userId 用户id
     * @return {@link String}
     */
    String getUserAuthorityInfo(Long userId);

    /**
     * 清除用户权限信息
     *
     * @param username 用户名
     */
    void clearUserAuthorityInfo(String username);

    /**
     * 清除用户权限信息根据角色id
     *
     * @param roleId 角色id
     */
    void clearUserAuthorityInfoByRoleId(Long roleId);

    /**
     * 清除用户权限信息根据菜单id
     *
     * @param menuId 菜单id
     */
    void clearUserAuthorityInfoByMenuId(Long menuId);

    /**
     * ids得到导航菜单
     *
     * @param userId 用户id
     * @return {@link List}<{@link Long}>
     */
    List<Long> getNavMenuIds(Long userId);

    /**
     * 用户列表分页
     *
     * @param name    名字
     * @param current 当前
     * @param size    大小
     * @return {@link Page}<{@link SysUser}>
     */
    Page<SysUser> listPage(String name, int current, int size);

    /**
     * 获取当前用户
     *
     * @param principal 主要
     * @return {@link UserVo}
     */
    UserVo getCurrentUser(Principal principal);

    /**
     * 通过用户id获得用户信息及其角色信息
     *
     * @param id id
     * @return {@link SysUser}
     */
    SysUser getUserAndRolesByUserId(Serializable id);

    /**
     * 新增用户
     *
     * @param sysUser 系统用户
     * @return boolean
     */
    boolean saveUser(SysUser sysUser);

    /**
     * 重置密码为888888
     *
     * @param userId 用户id
     * @return boolean
     */
    boolean resetPassword(Long userId);

    /**
     * 更新密码
     *
     * @param passVo    通过签证官
     * @param principal 主要
     */
    void updatePassword(PassVo passVo, Principal principal);

    /**
     * 更新用户
     *
     * @param sysUser 系统用户
     * @return boolean
     */
    boolean updateByUser(SysUser sysUser);

    /**
     * 删除
     *
     * @param ids id
     */
    void delete(List<Long> ids);

    /**
     * 分配权限
     *
     * @param userId  用户id
     * @param roleIds 角色id
     */
    void assignPermissions(Long userId, List<Long> roleIds);
}

