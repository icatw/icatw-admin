package cn.icatw.admin.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.icatw.admin.common.ResultStatusEnum;
import cn.icatw.admin.common.exception.CustomException;
import cn.icatw.admin.common.vo.SysMenuVo;
import cn.icatw.admin.domain.SysMenu;
import cn.icatw.admin.domain.SysRoleMenu;
import cn.icatw.admin.domain.SysUser;
import cn.icatw.admin.mapper.SysMenuMapper;
import cn.icatw.admin.service.SysMenuService;
import cn.icatw.admin.service.SysRoleMenuService;
import cn.icatw.admin.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @Autowired
    SysRoleMenuService sysRoleMenuService;

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

    @Override
    public List<SysMenu> treeList() {
        //获取所有菜单信息
        List<SysMenu> sysMenus = this.list(new QueryWrapper<SysMenu>().orderByDesc("orderNum"));
        //转成树状结构
        return buildTreeMenu(sysMenus);
    }

    @Override
    public Map<Object, Object> getNavMenus(Principal principal) {
        String[] authorityInfoArray = new String[0];
        List<SysMenuVo> navs = null;
        try {
            SysUser sysUser = sysUserService.getByUsername(principal.getName());
            //    获取权限信息
            String authorityInfo = sysUserService.getUserAuthorityInfo(sysUser.getId());
            authorityInfoArray = StringUtils.tokenizeToStringArray(authorityInfo, ",");
            //    获取导航栏信息
            navs = this.getCurrentUserNav();
        } catch (Exception e) {
            throw new CustomException(ResultStatusEnum.SYSTEM_EXCEPTION);
        }
        return MapUtil.builder()
                .put("authoritys", authorityInfoArray)
                .put("nav", navs)
                .map();
    }

    @Override
    public boolean insert(SysMenu sysMenu) {
        SysMenu menu = this.getOne(new QueryWrapper<SysMenu>().eq("name", sysMenu.getName()));
        if (menu != null) {
            throw new CustomException(405, "该菜单已经存在！");
        }
        sysMenu.setCreated(LocalDateTime.now());
        this.save(sysMenu);
        return true;
    }

    @Override
    public boolean updateMenu(SysMenu sysMenu) {
        SysMenu menu = this.getOne(new QueryWrapper<SysMenu>().eq("name", sysMenu.getName()));
        if (menu != null) {
            throw new CustomException(405, "该菜单已经存在！");
        }
        sysMenu.setUpdated(LocalDateTime.now());
        //更新缓存信息
        sysUserService.clearUserAuthorityInfoByMenuId(sysMenu.getId());
        return this.updateById(sysMenu);
    }

    @Override
    public void deleteMenu(Long id) {
        //判断是否有子菜单
        //查找是否有菜单的父id等于当前要删除的菜单的id
        long count = this.count(new QueryWrapper<SysMenu>().eq("parent_id", id));
        if (count > 0) {
            throw new CustomException(400, "请先删除子菜单");
        }
        //当没有子菜单之后
        //清除所有与该菜单相关的权限缓存
        sysUserService.clearUserAuthorityInfoByMenuId(id);
        //清除中间表信息
        this.removeById(id);
        sysRoleMenuService.remove(new QueryWrapper<SysRoleMenu>().eq("menu_id", id));
    }

    /**
     * 转换
     *
     * @param menuTree 菜单树
     * @return {@link List}<{@link SysMenuVo}>
     */
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

