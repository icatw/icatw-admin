package cn.icatw.admin.controller;

import cn.hutool.core.map.MapUtil;
import cn.icatw.admin.common.R;
import cn.icatw.admin.common.vo.SysMenuVo;
import cn.icatw.admin.domain.SysMenu;
import cn.icatw.admin.domain.SysUser;
import cn.icatw.admin.service.SysMenuService;
import cn.icatw.admin.service.SysUserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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

    @Autowired
    SysUserService sysUserService;

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
     * 通过主键查询单条数据
     */
    @ApiOperation(value = "通过主键查询单条数据 菜单表")
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return R.ok(this.sysMenuService.getById(id));
    }

    /**
     * 新增数据
     */
    @ApiOperation(value = "新增数据 菜单表")
    @PostMapping
    public R save(@RequestBody SysMenu sysMenu) {
        return R.ok(this.sysMenuService.save(sysMenu));
    }

    /**
     * 修改数据
     */
    @ApiOperation(value = "修改数据 菜单表")
    @PutMapping
    public R updateById(@RequestBody SysMenu sysMenu) {
        return R.ok(this.sysMenuService.updateById(sysMenu));
    }

    /**
     * 单条/批量删除数据
     */
    @ApiOperation(value = "单条/批量删除数据 菜单表")
    @DeleteMapping
    public R delete(@RequestParam List<Long> id) {
        return R.ok(this.sysMenuService.removeByIds(id));
    }
}

