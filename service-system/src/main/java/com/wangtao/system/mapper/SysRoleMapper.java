package com.wangtao.system.mapper;

import com.wangtao.model.system.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangtao.model.vo.SysRoleQueryVo;
import org.apache.ibatis.annotations.Param;

public interface SysRoleMapper extends BaseMapper<SysRole> {
    Page<SysRole> findPage(Page<SysRole> page,@Param("vo")  SysRoleQueryVo sysRoleQueryVo);
}
