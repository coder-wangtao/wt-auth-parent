package com.wangtao.system.filter;

import com.wangtao.common.helper.JwtHelper;
import com.wangtao.common.result.Result;
import com.wangtao.common.util.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.jws.Oneway;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private RedisTemplate redisTemplate;

    public TokenAuthenticationFilter(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //所有请求的拦截器
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if("/admin/system/index/login".equals(requestURI)){
            filterChain.doFilter(request, response);
        }
        String token = request.getHeader("token");
        if(!StringUtils.isEmpty(token)){
            String username = JwtHelper.getUsername(token);
            if(!StringUtils.isEmpty(username)){
                //redis获取权限
                Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)redisTemplate.boundValueOps(username).get();

                //把认证成功的用户对象放到上下文对象中
                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            }else{
                ResponseUtil.out(response, Result.fail("您不是一个有效的用户"));
            }
        }else{
            ResponseUtil.out(response, Result.fail("您不是一个有效的用户"));
        }

    }
}
