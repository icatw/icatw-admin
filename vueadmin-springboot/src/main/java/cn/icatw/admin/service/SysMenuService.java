package cn.icatw.admin.service;

import cn.hutool.core.map.MapUtil;
import cn.icatw.admin.common.vo.SysMenuVo;
import cn.icatw.admin.domain.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * (SysMenu)表服务接口
 *
 * @author icatw
 * @since 2022-05-04 19:24:31
 */
public interface SysMenuService extends IService<SysMenu> {
    /**
     * 获得当前用户导航
     *
     * @return {@link List}<{@link SysMenuVo}>
     */
    List<SysMenuVo> getCurrentUserNav();

    /**
     * 树列表
     *
     * @return {@link List}<{@link SysMenu}>
     */
    List<SysMenu> treeList();

    /**
     * 得到导航菜单
     *
     * @param principal
     * @return {@link MapUtil}
     */
    Map<Object, Object> getNavMenus(Principal principal);

    /**
     * 新增菜单
     *
     * @param sysMenu 系统菜单
     * @return boolean
     */
    boolean insert(SysMenu sysMenu);

    /**
     * 更新菜单
     *
     * @param sysMenu 系统菜单
     * @return boolean
     */
    boolean updateMenu(SysMenu sysMenu);

    /**
     * 删除菜单
     *
     * @param id id
     */
    void deleteMenu(Long id);
}

