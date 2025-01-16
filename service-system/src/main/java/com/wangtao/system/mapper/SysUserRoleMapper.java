package com.wangtao.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangtao.model.system.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    List<Long> getRoleIdsByUserId(Long userId);

    void batchInsert(@Param("userId") Long userId,@Param("roleIdList") List<Long> roleIdList);
}
