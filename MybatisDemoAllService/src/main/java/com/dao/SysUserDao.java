package com.dao;

import com.base.GenericDao;
import com.model.SysUser;
import com.model.SysUserCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysUserDao extends GenericDao<SysUser, SysUserCriteria, String> {

    List<SysUser> selectAll();
}