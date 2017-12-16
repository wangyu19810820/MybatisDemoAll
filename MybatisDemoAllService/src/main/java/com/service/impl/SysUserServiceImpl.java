package com.service.impl;

import com.base.GenericServiceImpl;
import com.dao.SysUserDao;
import com.model.SysUser;
import com.model.SysUserCriteria;
import com.service.SysUserService;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends GenericServiceImpl<SysUser, SysUserCriteria, String> implements SysUserService {
}