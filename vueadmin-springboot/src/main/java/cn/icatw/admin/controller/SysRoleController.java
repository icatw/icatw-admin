package cn.icatw.admin.controller;

import cn.icatw.admin.common.R;
import cn.icatw.admin.common.exception.CustomException;
import cn.icatw.admin.domain.SysRole;
import cn.icatw.admin.service.SysRoleService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * (SysRole)表控制层
 *
 * @author icatw
 * @since 2022-05-05 08:46:26
 */
@Api(tags = "角色模块")
@RestController
@RequestMapping("sys/role")
public class SysRoleController {

    /**
     * 服务对象
     */
    @Resource
    private SysRoleService sysRoleService;


    /**
     * 分页查询所有数据
     */
    @ApiOperation(value = "分页查询所有数据 ")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:role:list')")
    public R page(@RequestParam(required = false) String name,
                  @RequestParam(defaultValue = "1") int current,
                  @RequestParam(defaultValue = "10") int size) {
        Page<SysRole> listPage = sysRoleService.pageList(name, current, size);
        return R.ok(listPage);
    }


    /**
     * 通过主键查询单条数据
     */
    @ApiOperation(value = "通过主键查询单条数据 ")
    @PreAuthorize("hasAuthority('sys:role:list')")
    @GetMapping("info/{id}")
    public R selectOne(@PathVariable Serializable id) {
        SysRole sysRole = sysRoleService.selectById(id);
        return R.ok(sysRole);
    }

    /**
     * 新增数据
     */
    @ApiOperation(value = "新增数据 ")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:role:save')")
    public R save(@Validated @RequestBody SysRole sysRole) {
        sysRole.setCreated(LocalDateTime.now());
        try {
            this.sysRoleService.save(sysRole);
            return R.ok();
        } catch (Exception e) {
            throw new CustomException(400, "请检查名称和唯一编码");
        }
    }

    /**
     * 修改数据
     */
    @ApiOperation(value = "修改数据 ")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:role:update')")
    public R updateById(@Validated @RequestBody SysRole sysRole) {
        sysRoleService.updateRole(sysRole);
        return R.ok();
    }

    /**
     * 单条/批量删除数据
     */
    @ApiOperation(value = "单条/批量删除数据 ")
    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:role:delete')")
    @Transactional(rollbackFor = Exception.class)
    public R delete(@RequestBody List<Long> ids) {
        sysRoleService.delete(ids);
        return R.ok();
    }

    /**
     * 分配权限
     *
     * @return {@link R}
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/perm/{roleId}")
    @PreAuthorize("hasAuthority('sys:role:perm')")
    public R perm(@PathVariable("roleId") Long roleId, @RequestBody Long[] menuIds) {
        sysRoleService.assignPermissions(roleId,menuIds);
        return R.ok();
    }
}

