package cn.icatw.admin.security;

import cn.hutool.json.JSONUtil;
import cn.icatw.admin.common.R;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * jwt拒绝访问处理程序
 *
 * @author icatw
 * @date 2022/5/5
 * @email 762188827@qq.com
 * @apiNote 访问权限不足时将在此处理，抛出异常
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ServletOutputStream outputStream = response.getOutputStream();
        R r = R.fail("无权访问，请联系管理员vx：iCat0297");
        outputStream.write(JSONUtil.toJsonStr(r).getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
    }
}
