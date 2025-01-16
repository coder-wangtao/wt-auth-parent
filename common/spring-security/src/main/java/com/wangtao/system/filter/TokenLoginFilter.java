package com.wangtao.system.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.wangtao.common.helper.JwtHelper;
import com.wangtao.common.result.Result;
import com.wangtao.common.util.ResponseUtil;
import com.wangtao.model.system.SysLoginLog;
import com.wangtao.model.system.SysUser;
import com.wangtao.model.vo.LoginVo;
import com.wangtao.system.custom.CustomerUser;
import com.wangtao.system.service.LoginLogService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//4 TokenLoginFilter 重写 attemptAuthentication(只有登录才走这里)
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {
    private RedisTemplate redisTemplate;
    private LoginLogService loginLogService;

    public TokenLoginFilter(AuthenticationManager authenticationManager,RedisTemplate redisTemplate,LoginLogService loginLogService) {
        this.setAuthenticationManager(authenticationManager);
        //设置过滤器要过滤的请求地址
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/system/index/login", "POST"));
        this.redisTemplate = redisTemplate;
        this.loginLogService = loginLogService;
    }

    //认证的方法
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            //读取客户端传过来的用户信息
            LoginVo loginVo = new ObjectMapper().readValue(request.getInputStream(), LoginVo.class);
            String username = loginVo.getUsername();
            String password = loginVo.getPassword();
            if(username == null){
                username = "";
            }
            if(password == null){
                password = "";
            }

            username = username.trim();
            //未认证的用户对象
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //认证成功之后的方法
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //认证成功向客户端写入token
        CustomerUser customerUser = (CustomerUser)authResult.getPrincipal();
        SysUser sysUser = customerUser.getSysUser();

        Collection<GrantedAuthority> authorities = customerUser.getAuthorities();
        //redis
        redisTemplate.boundValueOps(sysUser.getUsername()).set(authorities);

        //保存当前登录用户的日志信息
        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setUsername(sysUser.getUsername());
        sysLoginLog.setStatus(1);
        sysLoginLog.setMsg("登陆成功");
        sysLoginLog.setIpaddr(request.getRemoteAddr());
        sysLoginLog.setAccessTime(new Date());
        loginLogService.saveLoginLog(sysLoginLog);
        String token = JwtHelper.createToken(sysUser.getId(), sysUser.getUsername());
        Map map = new HashMap();
        map.put("token", token);
        ResponseUtil.out(response, Result.ok(map));
    }

    //认证失败之后执行的方法


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ResponseUtil.out(response,Result.fail(failed.getMessage()));
    }
}
