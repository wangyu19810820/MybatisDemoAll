package com.service;

import com.base.GenericServiceImpl;
import com.dao.SysUserDao;
import com.model.SysUser;
import com.model.SysUserCriteria;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends GenericServiceImpl<SysUser, SysUserCriteria, String> implements SysUserService {
}