package wangyu1981;

import com.dao.SysRoleDao;
import com.model.SysRole;
import com.model.SysUser;
import com.service.SysRoleService;
import com.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class SysRoleTest {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysRoleDao sysRoleDao;

    @Test
    public void testInsert() {
        Subject curUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
        curUser.login(token);

        SysRole sysRole = new SysRole();
        sysRole.setId(UUID.randomUUID().toString());
        sysRole.setcName("aa");
        sysRole.setStatus((byte)1);
        sysRoleService.insert(sysRole);
    }

    @Test
    public void testDemo() {
        List<SysRole> list = sysRoleDao.demo();
        list.forEach(System.out::println);
    }
}
