package cn.icatw.admin.service;

import cn.icatw.admin.common.vo.SysMenuVo;
import cn.icatw.admin.domain.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
}

