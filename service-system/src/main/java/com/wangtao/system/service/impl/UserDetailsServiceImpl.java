package com.wangtao.system.service.impl;

import com.wangtao.model.system.SysUser;
import com.wangtao.system.custom.CustomerUser;
import com.wangtao.system.mapper.SysUserMapper;
import com.wangtao.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

//3.自定义UserDetailsServiceImpl重写loadUserByUsername返回2步的CustomerUser
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getUserByUserName(username);
        if(sysUser==null){
            throw new UsernameNotFoundException("账号不存在");
        }
        if(sysUser.getStatus()==0){
            throw new RuntimeException("账号已停用");
        }

        List<String> userPermsBtnsList = sysUserService.getUserPermsBtnsByUserId(sysUser.getId());

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (String perms : userPermsBtnsList) {
            authorities.add(new SimpleGrantedAuthority(perms));
        }
        return new CustomerUser(sysUser, authorities);
    }
}
