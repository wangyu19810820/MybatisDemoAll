package wangyu1981;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wangyu1981.dao.TbAdminDao;
import wangyu1981.model.TbAdmin;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class TbAdminTest {

    @Autowired
    private TbAdminDao tbAdminDao;

    @Test
    public void testSelect() {
        List<TbAdmin> list = tbAdminDao.select();
        list.forEach(System.out::println);
    }

    @Test
    public void testInsert() {
        TbAdmin tbAdmin = new TbAdmin();
        tbAdmin.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        tbAdmin.setUserName("dd");
        tbAdmin.setCreateTime(new Date());
        int r = tbAdminDao.insert(tbAdmin);
        System.out.println(r);
    }

    @Test
    public void testUpdate() {
        TbAdmin tbAdmin = new TbAdmin();
        tbAdmin.setId("11");
        tbAdmin.setUserName("aaa");
        int r = tbAdminDao.update(tbAdmin);
        System.out.println(r);
    }

}
