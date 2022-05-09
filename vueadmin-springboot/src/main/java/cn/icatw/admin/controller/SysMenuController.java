package cn.icatw.admin.controller;

import cn.icatw.admin.common.R;
import cn.icatw.admin.domain.SysMenu;
import cn.icatw.admin.service.SysMenuService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.security.Principal;
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


    @ApiOperation("获取导航菜单")
    @GetMapping("/nav")
    public R nav(Principal principal) {
        return R.ok(sysMenuService.getNavMenus(principal));
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
        sysMenuService.insert(sysMenu);
        return R.ok();

    }

    /**
     * 修改数据
     */
    @ApiOperation(value = "修改数据 菜单表")
    @PreAuthorize("hasAuthority('sys:menu:update')")
    @PostMapping("/update")
    public R updateById(@Validated @RequestBody SysMenu sysMenu) {
        return R.ok(sysMenuService.updateMenu(sysMenu));
    }

    /**
     * 单条/批量删除数据
     */
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    @ApiOperation(value = "单条/批量删除数据 菜单表")
    @DeleteMapping("{id}")
    public R delete(@PathVariable Long id) {
        sysMenuService.deleteMenu(id);
        return R.ok();
    }
}

