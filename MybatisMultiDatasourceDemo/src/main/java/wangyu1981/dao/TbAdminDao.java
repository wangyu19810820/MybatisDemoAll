package wangyu1981.dao;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;
import wangyu1981.model.TbAdmin;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class TbAdminDao extends SqlSessionDaoSupport {

    public int insert(TbAdmin tbAdmin) {
        return this.getSqlSession().insert("insert", tbAdmin);
    }

    public int update(TbAdmin tbAdmin) {
        return this.getSqlSession().update("update", tbAdmin);
    }

    public List<TbAdmin> select() {
        return this.getSqlSession().selectList("select");
    }

    @Resource
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
        super.setSqlSessionFactory(sqlSessionFactory);
    }
}
