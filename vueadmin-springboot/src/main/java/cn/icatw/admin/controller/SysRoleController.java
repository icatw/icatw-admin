package cn.icatw.admin.controller;

import cn.icatw.admin.common.R;
import cn.icatw.admin.common.exception.CustomException;
import cn.icatw.admin.domain.SysRole;
import cn.icatw.admin.domain.SysRoleMenu;
import cn.icatw.admin.domain.SysUserRole;
import cn.icatw.admin.service.SysRoleMenuService;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (SysRole)表控制层
 *
 * @author icatw
 * @since 2022-05-05 08:46:26
 */
@Api(tags = "(SysRole)")
@RestController
@RequestMapping("sys/role")
public class SysRoleController {

    /**
     * 服务对象
     */
    @Resource
    private SysRoleService sysRoleService;
    @Autowired
    SysRoleMenuService sysRoleMenuService;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysUserRoleService sysUserRoleService;

    /**
     * 分页查询所有数据
     */
    @ApiOperation(value = "分页查询所有数据 ")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:role:list')")
    public R page(@RequestParam(required = false) String name,
                  @RequestParam(defaultValue = "1") int current,
                  @RequestParam(defaultValue = "10") int size) {
        QueryWrapper<SysRole> wrapper = new QueryWrapper<SysRole>()
                .like(StringUtils.isNotBlank(name), "name", name);
        //if (name != null && !"".equals(name)) {
        //    wrapper = new QueryWrapper<SysRole>().like("name", name);
        //}
        Page<SysRole> page = new Page<>(current, size);
        return R.ok(this.sysRoleService.page(page, wrapper));
    }


    /**
     * 通过主键查询单条数据
     */
    @ApiOperation(value = "通过主键查询单条数据 ")
    @PreAuthorize("hasAuthority('sys:role:list')")
    @GetMapping("info/{id}")
    public R selectOne(@PathVariable Serializable id) {
        SysRole sysRole = this.sysRoleService.getById(id);
        //获取角色相关联的menuIds
        List<SysRoleMenu> roleMenus = sysRoleMenuService.list(new QueryWrapper<SysRoleMenu>().eq("role_id", sysRole.getId()));
        List<Long> menuIds = roleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
        sysRole.setMenuIds(menuIds);
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
        sysRole.setUpdated(LocalDateTime.now());
        //同步删除缓存权限信息
        sysUserService.clearUserAuthorityInfoByRoleId(sysRole.getId());
        return R.ok(this.sysRoleService.updateById(sysRole));
    }

    /**
     * 单条/批量删除数据
     */
    @ApiOperation(value = "单条/批量删除数据 ")
    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:role:delete')")
    @Transactional(rollbackFor = Exception.class)
    public R delete(@RequestBody List<Long> ids) {
        this.sysRoleService.removeByIds(ids);
        //同步删除中间表记录
        sysUserRoleService.remove(new QueryWrapper<SysUserRole>()
                .in("role_id", ids));
        sysRoleMenuService.remove(new QueryWrapper<SysRoleMenu>()
                .in("role_id", ids));
        //同步删除缓存权限信息
        for (Long id : ids) {
            sysUserService.clearUserAuthorityInfoByRoleId(id);
        }
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
        ArrayList<SysRoleMenu> roleMenus = new ArrayList<>();
        Arrays.stream(menuIds).forEach(menuId -> {
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setMenuId(menuId);
            roleMenu.setRoleId(roleId);
            roleMenus.add(roleMenu);
        });
        //先删除原来的记录再保存新记录
        sysRoleMenuService.remove(new QueryWrapper<SysRoleMenu>().eq("role_id", roleId));
        sysRoleMenuService.saveBatch(roleMenus);
        //同步删除缓存
        sysUserService.clearUserAuthorityInfoByRoleId(roleId);
        return R.ok();
    }
}

