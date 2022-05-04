package cn.icatw.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.icatw.admin.domain.SysRoleMenu;
import cn.icatw.admin.service.SysRoleMenuService;
import cn.icatw.admin.common.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (SysRoleMenu)表控制层
 *
 * @author icatw
 * @since 2022-05-04 19:28:34
 */
@RestController
@RequestMapping("sysRoleMenu")
public class SysRoleMenuController {

    /**
     * 服务对象
     */
    @Resource
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 分页查询所有数据
     */
    @GetMapping
    public R page(@RequestParam int current, @RequestParam int size) {
        Page<SysRoleMenu> page = new Page<>(current, size);
        return R.ok(this.sysRoleMenuService.page(page));
    }


    /**
     * 通过主键查询单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return R.ok(this.sysRoleMenuService.getById(id));
    }

    /**
     * 新增数据
     */
    @PostMapping
    public R save(@RequestBody SysRoleMenu sysRoleMenu) {
        return R.ok(this.sysRoleMenuService.save(sysRoleMenu));
    }

    /**
     * 修改数据
     */
    @PutMapping
    public R updateById(@RequestBody SysRoleMenu sysRoleMenu) {
        return R.ok(this.sysRoleMenuService.updateById(sysRoleMenu));
    }

    /**
     * 单条/批量删除数据
     */
    @DeleteMapping
    public R delete(@RequestParam List<Long> id) {
        return R.ok(this.sysRoleMenuService.removeByIds(id));
    }
}

