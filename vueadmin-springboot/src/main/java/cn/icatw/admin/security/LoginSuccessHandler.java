package cn.icatw.admin.security;

import cn.hutool.json.JSONUtil;
import cn.icatw.admin.common.R;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
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
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        //生成jwt，并放置到请求头中
        R r = R.ok();
        outputStream.write(JSONUtil.toJsonStr(r).getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
    }
}
