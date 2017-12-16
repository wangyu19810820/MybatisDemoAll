package com.service.impl;

import com.base.GenericServiceImpl;
import com.dao.SysRoleDao;
import com.model.SysRole;
import com.model.SysRoleCriteria;
import com.service.SysRoleService;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl extends GenericServiceImpl<SysRole, SysRoleCriteria, String> implements SysRoleService {
}