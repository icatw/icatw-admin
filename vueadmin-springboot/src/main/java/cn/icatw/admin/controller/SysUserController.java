package cn.icatw.admin.controller;

import cn.icatw.admin.common.R;
import cn.icatw.admin.common.vo.PassVo;
import cn.icatw.admin.common.vo.UserVo;
import cn.icatw.admin.domain.SysUser;
import cn.icatw.admin.service.SysUserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.security.Principal;
import java.util.List;

/**
 * (SysUser)表控制层
 *
 * @author icatw
 * @since 2022-05-05 08:46:27
 */
@Api(tags = "(用户模块)")
@RestController
@RequestMapping("sys/user")
@Slf4j
public class SysUserController {

    /**
     * 服务对象
     */
    @Resource
    private SysUserService sysUserService;


    /**
     * 分页查询所有数据
     */
    @ApiOperation(value = "分页查询所有数据 ")
    @PreAuthorize("hasAuthority('sys:user:list')")
    @GetMapping("/list")
    public R page(@RequestParam(required = false) String name, @RequestParam int current, @RequestParam int size) {
        Page<SysUser> pageList = sysUserService.listPage(name, current, size);
        return R.ok(pageList);
    }

    /**
     * 获取当前用户
     *
     * @param principal 主要
     * @return {@link R}
     */
    @ApiOperation(value = "得到当前登陆用户信息")
    @GetMapping("/getCurrentUser")
    public R getCurrentUser(Principal principal) {
        UserVo userVo = sysUserService.getCurrentUser(principal);
        return R.ok(userVo);
    }

    /**
     * 通过主键查询单条数据
     */
    @ApiOperation(value = "通过主键查询单条数据 ")
    @GetMapping("info/{id}")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public R selectOne(@PathVariable Serializable id) {
        return R.ok(sysUserService.getUserAndRolesByUserId(id));
    }

    /**
     * 新增数据
     */
    @ApiOperation(value = "新增数据 ")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:user:save')")
    public R save(@Validated @RequestBody SysUser sysUser) {
        return R.ok(sysUserService.saveUser(sysUser));
    }

    /**
     * 修改数据
     */
    @ApiOperation(value = "修改数据 ")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:user:update')")
    public R updateById(@RequestBody SysUser sysUser) {
        return R.ok(sysUserService.updateByUser(sysUser));
    }

    /**
     * 单条/批量删除数据
     */
    @ApiOperation(value = "单条/批量删除数据 ")
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public R delete(@RequestParam List<Long> ids) {
        sysUserService.delete(ids);
        return R.ok();
    }

    /**
     * 分配权限
     *
     * @param userId  用户id
     * @param roleIds 角色id
     * @return {@link R}
     */
    @ApiOperation(value = "分配权限 ")
    @PostMapping("/role/{userId}")
    @PreAuthorize("hasAuthority('sys:user:role')")
    public R rolePerm(@PathVariable("userId") Long userId,
                      @RequestBody List<Long> roleIds) {
        sysUserService.assignPermissions(userId, roleIds);
        return R.ok();
    }

    /**
     * 重置密码：888888
     *
     * @param userId 用户id
     * @return {@link R}
     */
    @ApiOperation("重置密码：默认为888888")
    @PostMapping("/rePass")
    @PreAuthorize("hasAuthority('sys:user:repass')")
    public R rePass(@RequestBody Long userId) {
        boolean b = sysUserService.resetPassword(userId);
        if (!b) {
            R.fail("系统异常，请稍后重试");
        }
        return R.ok();
    }

    /**
     * 修改密码
     *
     * @param passVo    通过签证官
     * @param principal 主要
     * @return {@link R}
     */
    @ApiOperation("修改密码")
    @PostMapping("/updatePass")
    public R updatePass(@Validated @RequestBody PassVo passVo, Principal principal) {
        sysUserService.updatePassword(passVo, principal);
        return R.ok();
    }
}

