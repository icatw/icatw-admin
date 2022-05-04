package cn.icatw.admin.security;

import cn.hutool.json.JSONUtil;
import cn.icatw.admin.common.R;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author icatw
 * @date 2022/5/5
 * @email 762188827@qq.com
 * @apiNote
 */
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        R r = R.fail(400, exception.getMessage());
        outputStream.write(JSONUtil.toJsonStr(r).getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
    }
}
