package com.wangtao.system.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangtao.model.system.SysRole;
import com.wangtao.model.system.SysUserRole;
import com.wangtao.model.vo.AssginRoleVo;
import com.wangtao.model.vo.SysRoleQueryVo;
import com.wangtao.system.mapper.SysRoleMapper;
import com.wangtao.system.mapper.SysUserMapper;
import com.wangtao.system.mapper.SysUserRoleMapper;
import com.wangtao.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    SysRoleMapper sysRoleMapper;

    @Autowired
    SysUserRoleMapper sysUserRoleMapper;
    @Override
    public Page<SysRole> findPage(Page<SysRole> page, SysRoleQueryVo sysRoleQueryVo) {
        return sysRoleMapper.findPage(page,sysRoleQueryVo);
    }

    @Override
    public Map getRolesByUserId(Long userId) {
        Map map = new HashMap();
        List<SysRole> allRoles = sysRoleMapper.selectList(null);
        List<Long> userRoleIdList = sysUserRoleMapper.getRoleIdsByUserId(userId);
        map.put("allRoles",allRoles);
        map.put("userRoleIds",userRoleIdList);
        return map;
    }

    @Override
    public void doAssignRole(AssginRoleVo assginRoleVo) {
        List<Long> roleIdList = assginRoleVo.getRoleIdList();
        Long userId = assginRoleVo.getUserId();
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        sysUserRoleMapper.delete(queryWrapper);
        sysUserRoleMapper.batchInsert(userId,roleIdList);
    }
}
