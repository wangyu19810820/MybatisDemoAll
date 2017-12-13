package wangyu1981;

import com.model.SysRole;
import com.model.SysUser;
import com.service.SysRoleService;
import com.service.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class SysRoleTest {

    @Autowired
    private SysRoleService sysRoleService;

    @Test
    public void testInsert() {
        SysRole sysRole = new SysRole();
        sysRole.setId(UUID.randomUUID().toString());
        sysRole.setcName("aa");
        sysRole.setStatus((byte)1);
        sysRoleService.insert(sysRole);
    }

}
