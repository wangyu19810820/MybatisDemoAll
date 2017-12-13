package com.dao;

import com.base.GenericDao;
import com.model.SysRole;
import com.model.SysRoleCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysRoleDao extends GenericDao<SysRole, SysRoleCriteria, String> {
}