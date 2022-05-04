package cn.icatw.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.icatw.admin.domain.SysUser;
import cn.icatw.admin.service.SysUserService;
import cn.icatw.admin.common.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (SysUser)表控制层
 *
 * @author icatw
 * @since 2022-05-04 19:28:34
 */
@RestController
@RequestMapping("sysUser")
public class SysUserController {

    /**
     * 服务对象
     */
    @Resource
    private SysUserService sysUserService;

    /**
     * 分页查询所有数据
     */
    @GetMapping
    public R page(@RequestParam int current, @RequestParam int size) {
        Page<SysUser> page = new Page<>(current, size);
        return R.ok(this.sysUserService.page(page));
    }


    /**
     * 通过主键查询单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return R.ok(this.sysUserService.getById(id));
    }

    /**
     * 新增数据
     */
    @PostMapping
    public R save(@RequestBody SysUser sysUser) {
        return R.ok(this.sysUserService.save(sysUser));
    }

    /**
     * 修改数据
     */
    @PutMapping
    public R updateById(@RequestBody SysUser sysUser) {
        return R.ok(this.sysUserService.updateById(sysUser));
    }

    /**
     * 单条/批量删除数据
     */
    @DeleteMapping
    public R delete(@RequestParam List<Long> id) {
        return R.ok(this.sysUserService.removeByIds(id));
    }
}

