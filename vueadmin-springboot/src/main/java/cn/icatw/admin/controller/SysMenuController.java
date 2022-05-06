package cn.icatw.admin.controller;

import cn.hutool.core.map.MapUtil;
import cn.icatw.admin.common.R;
import cn.icatw.admin.common.vo.SysMenuVo;
import cn.icatw.admin.domain.SysMenu;
import cn.icatw.admin.domain.SysRoleMenu;
import cn.icatw.admin.domain.SysUser;
import cn.icatw.admin.service.SysMenuService;
import cn.icatw.admin.service.SysRoleMenuService;
import cn.icatw.admin.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜单表(SysMenu)表控制层
 *
 * @author icatw
 * @since 2022-05-05 08:46:26
 */
@Api(tags = "菜单表(SysMenu)")
@RestController
@RequestMapping("sys/menu")
public class SysMenuController {

    /**
     * 服务对象
     */
    @Resource
    private SysMenuService sysMenuService;
    @Autowired
    SysRoleMenuService sysRoleMenuService;
    @Autowired
    SysUserService sysUserService;

    @ApiOperation("获取导航菜单")
    @GetMapping("/nav")
    public R nav(Principal principal) {
        SysUser sysUser = sysUserService.getByUsername(principal.getName());
        //    获取权限信息
        String authorityInfo = sysUserService.getUserAuthorityInfo(sysUser.getId());
        String[] authorityInfoArray = StringUtils.tokenizeToStringArray(authorityInfo, ",");
        //    获取导航栏信息
        List<SysMenuVo> navs = sysMenuService.getCurrentUserNav();
        return R.ok(MapUtil.builder()
                .put("authoritys", authorityInfoArray)
                .put("nav", navs)
                .map());
    }

    /**
     * 分页查询所有数据
     */
    @ApiOperation(value = "分页查询所有数据 菜单表")
    @GetMapping
    public R page(@RequestParam int current, @RequestParam int size) {
        Page<SysMenu> page = new Page<>(current, size);
        return R.ok(this.sysMenuService.page(page));
    }

    /**
     * 查询所有数据（树状结构）
     */
    @ApiOperation(value = "查询所有数据 菜单表")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    @GetMapping("/list")
    public R list() {
        List<SysMenu> menus = this.sysMenuService.treeList();
        return R.ok(menus);
    }

    /**
     * 通过主键查询单条数据
     */
    @ApiOperation(value = "通过主键查询单条数据 菜单表")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    @GetMapping("info/{id}")
    public R selectOne(@PathVariable Serializable id) {
        return R.ok(this.sysMenuService.getById(id));
    }

    /**
     * 新增数据
     */
    @ApiOperation(value = "新增数据 菜单表")
    @PreAuthorize("hasAuthority('sys:menu:save')")
    @PostMapping("/save")
    public R save(@Validated @RequestBody SysMenu sysMenu) {
        sysMenu.setCreated(LocalDateTime.now());
        return R.ok(this.sysMenuService.save(sysMenu));
    }

    /**
     * 修改数据
     */
    @ApiOperation(value = "修改数据 菜单表")
    @PreAuthorize("hasAuthority('sys:menu:update')")
    @PostMapping("/update")
    public R updateById(@Validated @RequestBody SysMenu sysMenu) {
        sysMenu.setUpdated(LocalDateTime.now());
        //更新缓存信息
        sysUserService.clearUserAuthorityInfoByMenuId(sysMenu.getId());
        return R.ok(this.sysMenuService.updateById(sysMenu));
    }

    /**
     * 单条/批量删除数据
     */
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    @ApiOperation(value = "单条/批量删除数据 菜单表")
    @DeleteMapping("{id}")
    public R delete(@PathVariable Long id) {
        //判断是否有子菜单
        //查找是否有菜单的父id等于当前要删除的菜单的id
        long count = sysMenuService.count(new QueryWrapper<SysMenu>().eq("parent_id", id));
        if (count > 0) {
            return R.fail("请先删除子菜单");
        }
        //当没有子菜单之后
        //清除所有与该菜单相关的权限缓存
        sysUserService.clearUserAuthorityInfoByMenuId(id);
        //清除中间表信息
        this.sysMenuService.removeById(id);
        sysRoleMenuService.remove(new QueryWrapper<SysRoleMenu>().eq("menu_id", id));

        return R.ok();
    }
}

