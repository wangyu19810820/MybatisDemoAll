package com.base;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Dao的接口
 * @param <M>
 * @param <Q>
 * @param <PK>
 */
public interface GenericDao<M, Q, PK> {

    long countByExample(Q example);
    int deleteByExample(Q example);
    int deleteByPrimaryKey(PK id);
    int insert(M record);
    int insertSelective(M record);
    List<M> selectByExample(Q example);
    M selectByPrimaryKey(PK pk);
    int updateByExampleSelective(@Param("record") M record, @Param("example") Q example);
    int updateByExample(@Param("record") M record, @Param("example") Q example);
    int updateByPrimaryKeySelective(M record);

}
