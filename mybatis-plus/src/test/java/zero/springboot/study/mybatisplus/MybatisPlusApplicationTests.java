package zero.springboot.study.mybatisplus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zero.springboot.study.mybatisplus.domain.VipAccount;
import zero.springboot.study.mybatisplus.mapper.master.VipAccountMapper;
import zero.springboot.study.mybatisplus.mapper.slave.VipAccountMapperSlave;
import zero.springboot.study.mybatisplus.service.vip.VipAccountServiceImpl;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = MybatisPlusApplication.class)
public class MybatisPlusApplicationTests {

    @Autowired
    private VipAccountServiceImpl vipAccountService;

    @Autowired
    private VipAccountMapper vipAccountMapper;

    @Autowired
    private VipAccountMapperSlave vipAccountMapperSlave;

    @Test
    public void testServiceCrud() {
        VipAccount vipAccount = new VipAccount();
        vipAccount.setUsername("青龙");
        vipAccountService.saveOrUpdate(vipAccount);
    }

    @Test
    public void testMapperCrud() {

        vipAccountMapper.deleteById(2);

        VipAccount vipAccount = new VipAccount();
        vipAccount.setUsername("青龙");
        vipAccountMapper.insert(vipAccount);
        VipAccount data = vipAccountMapperSlave.selectById(3);
        System.out.println(data.toString());
    }

    @Test
    public void testMapperCrud1() {
        VipAccount vipAccount = new VipAccount();
        vipAccount.setUsername("青龙");
        System.out.println(vipAccount.toString());
        vipAccount = null;
        vipAccount.setUsername("哈哈");
        System.out.println(vipAccount.toString());
    }

    public static void main(String[] args) {
        VipAccount vipAccount = new VipAccount();
        vipAccount.setUsername("青龙");
        System.out.println(vipAccount.toString());
        vipAccount = null;
        vipAccount.setUsername("哈哈");
        System.out.println(vipAccount.toString());
    }


}
