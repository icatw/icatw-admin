package cn.icatw.admin.controller;

import cn.icatw.admin.common.Const;
import cn.icatw.admin.common.R;
import cn.icatw.admin.domain.SysRole;
import cn.icatw.admin.domain.SysUser;
import cn.icatw.admin.domain.SysUserRole;
import cn.icatw.admin.service.SysRoleService;
import cn.icatw.admin.service.SysUserRoleService;
import cn.icatw.admin.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * (SysUser)表控制层
 *
 * @author icatw
 * @since 2022-05-05 08:46:27
 */
@Api(tags = "(SysUser)")
@RestController
@RequestMapping("sys/user")
public class SysUserController {

    /**
     * 服务对象
     */
    @Resource
    private SysUserService sysUserService;

    @Autowired
    SysRoleService sysRoleService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    SysUserRoleService sysUserRoleService;

    /**
     * 分页查询所有数据
     */
    @ApiOperation(value = "分页查询所有数据 ")
    @PreAuthorize("hasAuthority('sys:user:list')")
    @GetMapping("/list")
    public R page(@RequestParam(required = false) String name, @RequestParam int current, @RequestParam int size) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<SysUser>()
                .like(StringUtils.isNotBlank(name), "username", name);
        Page<SysUser> page = new Page<>(current, size);
        Page<SysUser> userPage = this.sysUserService.page(page, wrapper);
        //查出分页数据之后遍历设置角色信息
        userPage.getRecords().forEach(u -> {
            u.setRoles(sysRoleService.listRolesByUserId(u.getId()));
        });
        return R.ok(userPage);
    }


    /**
     * 通过主键查询单条数据
     */
    @ApiOperation(value = "通过主键查询单条数据 ")
    @GetMapping("info/{id}")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public R selectOne(@PathVariable Serializable id) {
        //一个用户有多个角色
        SysUser user = this.sysUserService.getById(id);
        List<SysRole> roles = sysRoleService.listRolesByUserId(user.getId());
        user.setRoles(roles);
        return R.ok(user);
    }

    /**
     * 新增数据
     */
    @ApiOperation(value = "新增数据 ")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:user:save')")
    public R save(@Validated @RequestBody SysUser sysUser) {
        sysUser.setCreated(LocalDateTime.now());
        //初始密码
        String initialPassword = bCryptPasswordEncoder.encode(Const.INITIAL_PASSWORD);
        sysUser.setPassword(initialPassword);
        //默认头像
        sysUser.setAvatar(Const.INITIAL_AVATAR);
        return R.ok(this.sysUserService.save(sysUser));
    }

    /**
     * 修改数据
     */
    @ApiOperation(value = "修改数据 ")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:user:update')")
    public R updateById(@RequestBody SysUser sysUser) {
        sysUser.setUpdated(LocalDateTime.now());
        return R.ok(this.sysUserService.updateById(sysUser));
    }

    /**
     * 单条/批量删除数据
     */
    @ApiOperation(value = "单条/批量删除数据 ")
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public R delete(@RequestParam List<Long> ids) {
        this.sysUserService.removeByIds(ids);
        //同步删除中间表数据
        sysUserRoleService.remove(new QueryWrapper<SysUserRole>()
                .in("user_id", ids));

        return R.ok();
    }

    @ApiOperation(value = "分配权限 ")
    @PostMapping("/role/{userId}")
    @PreAuthorize("hasAuthority('sys:user:role')")
    public R rolePerm(@PathVariable("userId") Long userId, @RequestBody List<Long> roleIds) {
        //更新中间表信息
        ArrayList<SysUserRole> sysUserRoles = new ArrayList<>();
        for (Long roleId : roleIds) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(roleId);
            sysUserRole.setUserId(userId);
            sysUserRoles.add(sysUserRole);
        }
        sysUserRoleService.remove(new QueryWrapper<SysUserRole>()
                .eq("user_id", userId));
        sysUserRoleService.saveBatch(sysUserRoles);
        //同时删除缓存
        SysUser user = sysUserService.getById(userId);
        sysUserService.clearUserAuthorityInfo(user.getUsername());
        return R.ok();
    }

    @PostMapping("/rePass")
    @PreAuthorize("hasAuthority('sys:user:repass')")
    public R rePass(@RequestBody Long userId) {
        SysUser user = sysUserService.getById(userId);
        user.setPassword(bCryptPasswordEncoder.encode(Const.INITIAL_PASSWORD));
        user.setUpdated(LocalDateTime.now());
        sysUserService.updateById(user);
        return R.ok();
    }
}

