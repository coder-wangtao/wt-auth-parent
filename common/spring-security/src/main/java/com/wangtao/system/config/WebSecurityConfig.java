package com.wangtao.system.config;

import com.wangtao.system.filter.TokenAuthenticationFilter;
import com.wangtao.system.filter.TokenLoginFilter;
import com.wangtao.system.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SpringBootConfiguration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //使得PreAuthorize生效
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    LoginLogService loginLogService;

    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors();
        //登录接口放行，其他接口认证
        http.authorizeRequests().antMatchers("/admin/system/index/login","/admin/system/index/info","/admin/system/index/logout")
        //其他接口只要登录成功（上下文对象有这个用户）就放行
        .permitAll().anyRequest().authenticated();
        //注册过滤器
        http.addFilter(new TokenLoginFilter(authenticationManager(),redisTemplate,loginLogService));
        http.addFilterBefore(new TokenAuthenticationFilter(redisTemplate), UsernamePasswordAuthenticationFilter.class);
        //禁用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    //配置(配置匿名访问的资源)
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/favicon.ico","/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/doc.html");
    }
}
