package com.wangtao.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangtao.model.system.SysUser;
import com.wangtao.model.vo.SysUserQueryVo;
import org.apache.ibatis.annotations.Param;

public interface SysUserMapper extends BaseMapper<SysUser> {
    Page<SysUser> findPage(Page page,@Param("vo") SysUserQueryVo sysUserQueryVo);
}
