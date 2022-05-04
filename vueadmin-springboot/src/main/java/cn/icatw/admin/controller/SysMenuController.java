package cn.icatw.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.icatw.admin.domain.SysMenu;
import cn.icatw.admin.service.SysMenuService;
import cn.icatw.admin.common.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (SysMenu)表控制层
 *
 * @author icatw
 * @since 2022-05-04 19:28:34
 */
@RestController
@RequestMapping("sysMenu")
public class SysMenuController {

    /**
     * 服务对象
     */
    @Resource
    private SysMenuService sysMenuService;

    @GetMapping("/test")
    public R test(){
        List<SysMenu> list = sysMenuService.list();
        return R.ok(list);
    }
    /**
     * 分页查询所有数据
     */
    @GetMapping
    public R page(@RequestParam int current, @RequestParam int size) {
        Page<SysMenu> page = new Page<>(current, size);
        return R.ok(this.sysMenuService.page(page));
    }


    /**
     * 通过主键查询单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return R.ok(this.sysMenuService.getById(id));
    }

    /**
     * 新增数据
     */
    @PostMapping
    public R save(@RequestBody SysMenu sysMenu) {
        return R.ok(this.sysMenuService.save(sysMenu));
    }

    /**
     * 修改数据
     */
    @PutMapping
    public R updateById(@RequestBody SysMenu sysMenu) {
        return R.ok(this.sysMenuService.updateById(sysMenu));
    }

    /**
     * 单条/批量删除数据
     */
    @DeleteMapping
    public R delete(@RequestParam List<Long> id) {
        return R.ok(this.sysMenuService.removeByIds(id));
    }
}

