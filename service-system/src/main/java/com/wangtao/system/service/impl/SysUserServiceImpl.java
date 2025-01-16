package com.wangtao.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangtao.common.helper.RouterHelper;
import com.wangtao.model.system.SysMenu;
import com.wangtao.model.system.SysUser;
import com.wangtao.model.vo.RouterVo;
import com.wangtao.model.vo.SysUserQueryVo;
import com.wangtao.system.controller.MenuHelper;
import com.wangtao.system.mapper.SysMenuMapper;
import com.wangtao.system.mapper.SysUserMapper;
import com.wangtao.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysMenuMapper sysMenuMapper;

    @Override
    public Page<SysUser> findPage(Page page, SysUserQueryVo sysUserQueryVo) {
        return sysUserMapper.findPage(page,sysUserQueryVo);
    }

    @Override
    public void updateStatus(Long userId, Integer status) {
        SysUser sysUser = new SysUser();
        sysUser.setId(userId);
        sysUser.setStatus(status);
        sysUserMapper.updateById(sysUser);
    }

    @Override
    public SysUser getUserByUserName(String username) {
        return sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("username", username));
    }

    @Override
    public Map getUserByUserId(Long userId) {
        SysUser sysUser = sysUserMapper.selectById(userId);
        List<SysMenu> sysMenuList = getMenuByUserId(userId);
        List<SysMenu> treeMenuList = MenuHelper.buildTreeList(sysMenuList);
        List<RouterVo> routers = RouterHelper.buildRouters(treeMenuList);
        List<String> buttons = getUserPermsBtnsByUserId(userId);
        Map map = new HashMap();
        map.put("name",sysUser.getName());
        map.put("avatar",sysUser.getHeadUrl());
        map.put("buttons",buttons);
        map.put("routers",routers);
        return map;
    }

    @Override
    public List<String> getUserPermsBtnsByUserId(Long userId) {
        List<String> btnsList = new ArrayList<>();
        List<SysMenu> menuList = getMenuByUserId(userId);
        for (SysMenu sysMenu : menuList) {
            if(sysMenu.getType() == 2){
                btnsList.add(sysMenu.getPerms());
            }
        }
        return btnsList;
    }

    private List<SysMenu> getMenuByUserId(Long userId) {
        List<SysMenu> sysMenuList = null;
        if(userId == 1l){
            sysMenuList = sysMenuMapper.selectList(new QueryWrapper<SysMenu>().eq("status",1));
        }else{
            sysMenuList = sysMenuMapper.getMenuListByUserId(userId);
        }
        return sysMenuList;
    }
}
