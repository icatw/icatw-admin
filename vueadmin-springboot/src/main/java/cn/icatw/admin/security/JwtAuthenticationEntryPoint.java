package cn.icatw.admin.security;

import cn.hutool.json.JSONUtil;
import cn.icatw.admin.common.R;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * jwt认证入口点
 *
 * @author icatw
 * @date 2022/5/5
 * @email 762188827@qq.com
 * @apiNote
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ServletOutputStream outputStream = response.getOutputStream();
        R r = R.fail("请先登陆！");
        outputStream.write(JSONUtil.toJsonStr(r).getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
    }
}
