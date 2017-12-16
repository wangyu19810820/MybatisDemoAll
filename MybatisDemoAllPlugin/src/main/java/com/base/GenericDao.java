package com.base;

import java.util.List;

/**
 * Dao的接口
 * @param <M>
 * @param <Q>
 * @param <PK>
 */
public interface GenericDao<M, Q, PK> {

    List<M> selectByExample(Q example);
    int insert(M record);
    M selectByPrimaryKey(PK pk);
    int updateByPrimaryKeySelective(M record);
    int deleteByPrimaryKey(PK id);
}
