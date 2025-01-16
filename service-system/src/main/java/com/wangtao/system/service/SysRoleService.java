package com.wangtao.system.service;

import com.wangtao.model.system.SysRole;
import com.wangtao.model.vo.AssginRoleVo;
import com.wangtao.model.vo.SysRoleQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface SysRoleService extends IService<SysRole> {
    Page<SysRole> findPage(Page<SysRole> page, SysRoleQueryVo sysRoleQueryVo);

    Map getRolesByUserId(Long userId);

    void doAssignRole(AssginRoleVo assginRoleVo);
}
