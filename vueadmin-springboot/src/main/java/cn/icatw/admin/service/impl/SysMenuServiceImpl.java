package cn.icatw.admin.service.impl;

import cn.icatw.admin.common.vo.SysMenuVo;
import cn.icatw.admin.domain.SysMenu;
import cn.icatw.admin.domain.SysUser;
import cn.icatw.admin.mapper.SysMenuMapper;
import cn.icatw.admin.service.SysMenuService;
import cn.icatw.admin.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * (SysMenu)表服务实现类
 *
 * @author icatw
 * @since 2022-05-04 19:24:31
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Autowired
    SysUserService sysUserService;

    @Override
    public List<SysMenuVo> getCurrentUserNav() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser user = sysUserService.getByUsername(username);
        List<Long> menuIds = sysUserService.getNavMenuIds(user.getId());
        List<SysMenu> menus = this.listByIds(menuIds);
        //转树状结构
        List<SysMenu> menuTree = buildTreeMenu(menus);
        //转vo对象
        return convert(menuTree);
    }

    private List<SysMenuVo> convert(List<SysMenu> menuTree) {
        List<SysMenuVo> menuVos = new ArrayList<>();
        menuTree.forEach(m -> {
            SysMenuVo vo = new SysMenuVo();
            vo.setId(m.getId());
            vo.setName(m.getPerms());
            vo.setTitle(m.getName());
            vo.setComponent(m.getComponent());
            vo.setIcon(m.getIcon());
            vo.setPath(m.getPath());
            if (m.getChildren().size() > 0) {
                vo.setChildren(convert(m.getChildren()));
            }
            menuVos.add(vo);
        });
        return menuVos;
    }

    /**
     * 构建树菜单
     *
     * @param menus 菜单
     * @return {@link List}<{@link SysMenu}>
     */
    private List<SysMenu> buildTreeMenu(List<SysMenu> menus) {
        List<SysMenu> finalMenus = new ArrayList<>();
        for (SysMenu menu : menus) {

            // 先寻找各自的孩子
            for (SysMenu e : menus) {
                if (e.getParentId().equals(menu.getId())) {
                    menu.getChildren().add(e);
                }
            }
            // 提取出父节点
            if (menu.getParentId() == 0L) {
                finalMenus.add(menu);
            }
        }
        return finalMenus;
    }
}

