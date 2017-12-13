package com.base;

import java.util.List;

public interface GenericService<M, Q, PK> {

    int insert(M m);
    int update(M m);
    int deleteByPrimaryKey(PK pk);
    M selectByPrimaryKey(PK pk);
    List<M> selectByExample(Q q);
    GenericDao<M, Q, PK> getDao();
}
