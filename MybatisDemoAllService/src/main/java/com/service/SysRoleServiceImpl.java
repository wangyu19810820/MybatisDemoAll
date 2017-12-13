package com.service;

import com.base.GenericServiceImpl;
import com.dao.SysRoleDao;
import com.model.SysRole;
import com.model.SysRoleCriteria;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl extends GenericServiceImpl<SysRole, SysRoleCriteria, String> implements SysRoleService {
}