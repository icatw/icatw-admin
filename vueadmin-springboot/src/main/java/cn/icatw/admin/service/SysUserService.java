package cn.icatw.admin.service;

import cn.icatw.admin.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

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
}

