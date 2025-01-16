package com.wangtao.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wangtao.model.system.SysUser;
import com.wangtao.model.vo.SysUserQueryVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public interface SysUserService extends IService<SysUser> {

    Page<SysUser> findPage(Page page, SysUserQueryVo sysUserQueryVo);

    void updateStatus(Long userId, Integer status);

    SysUser getUserByUserName(String username);

    Map getUserByUserId(Long userId);

    List<String> getUserPermsBtnsByUserId(Long userId);
}
