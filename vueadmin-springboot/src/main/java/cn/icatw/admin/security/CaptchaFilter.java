package cn.icatw.admin.security;

import cn.icatw.admin.common.Const;
import cn.icatw.admin.common.exception.CaptchaException;
import cn.icatw.admin.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码过滤
 *
 * @author icatw
 * @date 2022/5/5
 * @email 762188827@qq.com
 * @apiNote
 */
@Component
public class CaptchaFilter extends OncePerRequestFilter {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    LoginFailureHandler failureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String url = request.getRequestURI();
        if ("/login".equals(url) && "POST".equals(request.getMethod())) {
            //校验验证码
            try {
                validate(request);
            } catch (CaptchaException e) {
                //如果不正确，就跳转到认证失败处理器
                failureHandler.onAuthenticationFailure(request, response, e);
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 验证验证码逻辑
     *
     * @param request 请求
     */
    private void validate(HttpServletRequest request) {
        String code = request.getParameter("code");
        String key = request.getParameter("token");
        if (StringUtils.isBlank(code) || StringUtils.isBlank(key)) {
            throw new CaptchaException("验证码错误");
        }
        if (!code.equals(redisUtil.hget(Const.CAPTCHA_KEY, key))) {
            throw new CaptchaException("验证码错误");
        }
        //一次性使用
        redisUtil.hdel(Const.CAPTCHA_KEY, key);
    }
}
