package com.wangtao.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangtao.model.system.SysMenu;
import com.wangtao.model.system.SysRoleMenu;
import com.wangtao.model.vo.AssginMenuVo;
import com.wangtao.system.controller.MenuHelper;
import com.wangtao.system.exception.WtException;
import com.wangtao.system.mapper.SysMenuMapper;
import com.wangtao.system.mapper.SysRoleMenuMapper;
import com.wangtao.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    SysMenuMapper sysMenuMapper;

    @Autowired
    SysRoleMenuMapper sysRoleMenuMapper;

    public SysMenuServiceImpl(SysMenuMapper sysMenuMapper) {
        this.sysMenuMapper = sysMenuMapper;
    }

    @Override
    public List<SysMenu> findMenuNodes() {
        List<SysMenu> sysMenusAllList = sysMenuMapper.selectList(null);

        List<SysMenu> treeList = MenuHelper.buildTreeList(sysMenusAllList);

        return treeList;
    }

    @Override
    public void delete(Long id) {
        Integer count = sysMenuMapper.getCountByParentId(id);
        if(count > 0){
            throw new WtException(201,"这是父节点不能删除");
        }else{
            sysMenuMapper.deleteById(id);
        }
    }

    @Override
    public List<SysMenu> getRoleMenuList(Long roleId) {
        List<SysMenu> sysMenuListAll = sysMenuMapper.selectList(new QueryWrapper<SysMenu>().eq("status", 1));
        List<SysRoleMenu> roleMenuIds = sysRoleMenuMapper.selectList(new QueryWrapper<SysRoleMenu>().eq("role_id", roleId));
        ArrayList<Object> menuIdList = new ArrayList<>();
        for (SysRoleMenu roleMenu : roleMenuIds) {
            menuIdList.add(roleMenu.getMenuId());
        }
        for (SysMenu sysMenu : sysMenuListAll) {
            if(menuIdList.contains(sysMenu.getId())){
                sysMenu.setSelect(true);
            }
        }
        //树
        List<SysMenu> treeMenuList = MenuHelper.buildTreeList(sysMenuListAll);
        return treeMenuList;
    }

    @Override
    public void assignMenu(AssginMenuVo assginMenuVo) {
        Long roleId = assginMenuVo.getRoleId();
        List<Long> menuIdList = assginMenuVo.getMenuIdList();
        sysRoleMenuMapper.delete(new QueryWrapper<SysRoleMenu>().eq("role_id", roleId));
        for (Long menuId : menuIdList) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(roleId);
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenuMapper.insert(sysRoleMenu);

        }
    }
}
