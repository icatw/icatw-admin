package cn.icatw.admin.config;

import cn.icatw.admin.security.CaptchaFilter;
import cn.icatw.admin.security.LoginFailureHandler;
import cn.icatw.admin.security.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author icatw
 * @date 2022/5/5
 * @email 762188827@qq.com
 * @apiNote
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private LoginFailureHandler failureHandler;
    @Autowired
    private LoginSuccessHandler successHandler;
    @Autowired
    CaptchaFilter captchaFilter;

    private static final String[] URL_WHITELIST = {
            "/login",
            "/logout",
            "/captcha",
            "/favicon.ico",
            "/swagger-ui.html/**",
            "/webjars/**",
            "/v2/**",
            "/swagger-resources/**",
            "/doc.html",
    };

    //@Override
    //public void configure(WebSecurity web) throws Exception {
    //    web.ignoring().antMatchers("/swagger-ui.html/**")
    //            .antMatchers("/webjars/**")
    //            .antMatchers("/v2/**")
    //            .antMatchers("/swagger-resources/**");
    //}

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                //登陆配置
                .formLogin()
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .and()
                //禁用session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //配置拦截规则
                .authorizeRequests()
                .antMatchers(URL_WHITELIST).permitAll()
                .anyRequest().authenticated()
                //异常处理器
                //配置自定义的过滤器
                .and()
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)
        ;
    }
}
