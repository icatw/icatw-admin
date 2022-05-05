package cn.icatw.admin.controller;

import cn.icatw.admin.common.R;
import cn.icatw.admin.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author icatw
 * @date 2022/5/5
 * @email 762188827@qq.com
 * @apiNote
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    SysUserService sysUserService;

    @PreAuthorize("hasRole('admin')")
    @GetMapping
    public R test() {
        return R.ok(sysUserService.list());
    }

    //普通用户、超级管理员
    @PreAuthorize("hasAuthority('sys:user:list')")
    @GetMapping("/pass")
    public R pass() {
        return R.ok("普通用户");
    }
}
