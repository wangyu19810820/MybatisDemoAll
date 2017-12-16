package com.base;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Service的基类实现类
 * @param <M>
 * @param <Q>
 * @param <PK>
 */
public class GenericServiceImpl<M, Q, PK>
        implements GenericService<M, Q, PK> {

    @Autowired
    protected GenericDao<M, Q, PK> dao;

    public int insert(M m) {
        return dao.insert(m);
    }

    public int update(M m) {
        return dao.updateByPrimaryKeySelective(m);
    }

    public int deleteByPrimaryKey(PK pk) {
        return dao.deleteByPrimaryKey(pk);
    }

    public M selectByPrimaryKey(PK pk) {
        return dao.selectByPrimaryKey(pk);
    }

    public List<M> selectByExample(Q q) {
        return dao.selectByExample(q);
    }

    public GenericDao<M, Q, PK> getDao() {
        return dao;
    }

}
