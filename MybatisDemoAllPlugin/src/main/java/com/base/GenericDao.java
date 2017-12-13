package com.base;

import java.util.List;

public interface GenericDao<M, Q, PK> {

    List<M> selectByExample(Q example);
    int insert(M record);
    M selectByPrimaryKey(PK pk);
    int updateByPrimaryKeySelective(M record);
    int deleteByPrimaryKey(PK id);
}
