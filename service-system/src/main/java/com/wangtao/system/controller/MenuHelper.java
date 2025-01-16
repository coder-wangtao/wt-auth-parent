package com.wangtao.system.controller;

import com.wangtao.model.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

public class MenuHelper {
    public static List<SysMenu> buildTreeList(List<SysMenu> sysMenusAllList) {
        List<SysMenu> treeList = new ArrayList<>();
        for (SysMenu sysMenu : sysMenusAllList) {
            if(sysMenu.getParentId() == 0){
                treeList.add(findChildNode(sysMenu,sysMenusAllList));
            }
        }
        return treeList;
    }

    //查找子节点
    private static SysMenu findChildNode(SysMenu sysMenu, List<SysMenu> sysMenusAllList) {
        sysMenu.setChildren(new ArrayList<>());

        for (SysMenu childNode : sysMenusAllList) {
            if(childNode.getParentId() == sysMenu.getId()){
                if(sysMenu.getChildren() == null){
                    sysMenu.setChildren(new ArrayList<>());
                }
                sysMenu.getChildren().add(findChildNode(childNode,sysMenusAllList));
            }
        }
        return sysMenu;
    }
}
