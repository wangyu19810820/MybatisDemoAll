package wangyu1981;

import com.dao.SysUserMapper;
import com.model.SysUser;
import com.model.SysUserCriteria;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class SysUserTest {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Test
    public void testInsert() {
        SysUser sysUser = new SysUser();
        sysUser.setId(UUID.randomUUID().toString());
        sysUser.setStatus((byte)1);
        sysUserMapper.insert(sysUser);
    }

    @Test
    public void testSelectByExample() {
        SysUserCriteria sysUserExample = new SysUserCriteria();
        sysUserExample.createCriteria().andStatusEqualTo((byte)1);
        List<SysUser> userList = sysUserMapper.selectByExample(sysUserExample);
        System.out.println(userList);
    }
}
