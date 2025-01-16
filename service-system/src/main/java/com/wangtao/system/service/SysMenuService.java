package com.wangtao.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangtao.model.system.SysMenu;
import com.wangtao.model.vo.AssginMenuVo;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {
    List<SysMenu> findMenuNodes();

    void delete(Long id);

    List<SysMenu> getRoleMenuList(Long roleId);

    void assignMenu(AssginMenuVo assginMenuVo);
}
