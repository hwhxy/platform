package com.fulu.game.core.service;

import com.fulu.game.core.entity.SysRole;

import java.util.List;


/**
 * 角色表
 * 
 * @author shijiaoyun
 * @email ${email}
 * @date 2018-09-19 16:25:01
 */
public interface SysRoleService extends ICommonService<SysRole,Integer>{

    /**
     * 通过用户id获取角色列表
     * @param id
     * @return
     */
    List<SysRole> findByAdminId(Integer id);
}