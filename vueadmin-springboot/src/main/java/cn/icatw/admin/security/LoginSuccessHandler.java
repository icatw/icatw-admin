package cn.icatw.admin.security;

import cn.hutool.json.JSONUtil;
import cn.icatw.admin.common.R;
import cn.icatw.admin.domain.SysUser;
import cn.icatw.admin.service.SysUserService;
import cn.icatw.admin.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 登录成功处理程序
 *
 * @author icatw
 * @date 2022/5/5
 * @email 762188827@qq.com
 * @apiNote
 */
@Component
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    SysUserService sysUserService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        String currentUsername = authentication.getName();
        //更新最后登陆时间
        if (currentUsername != null) {
            SysUser user = sysUserService.getByUsername(currentUsername);
            user.setLastLogin(LocalDateTime.now());
            sysUserService.updateById(user);
        }

        // 生成jwt，并放置到请求头中
        String jwt = jwtUtil.generateToken(currentUsername);
        log.info("token为---------------------------------------" + jwt);
        response.setHeader(jwtUtil.getHeader(), jwt);

        R result = R.ok("");

        outputStream.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));

        outputStream.flush();
        outputStream.close();
    }
}
