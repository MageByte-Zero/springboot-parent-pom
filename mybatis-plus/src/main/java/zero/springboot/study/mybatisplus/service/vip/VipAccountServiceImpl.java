/**
 * Project: springboot-parent-pom
 * File created at 2019/4/22 17:16
 */
package zero.springboot.study.mybatisplus.service.vip;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import zero.springboot.study.mybatisplus.domain.VipAccount;
import zero.springboot.study.mybatisplus.mapper.master.VipAccountMapper;

/**
 * @author lijianqing
 * @version 1.0
 * @ClassName VipAccountService
 * @date 2019/4/22 17:16
 */
@Service
public class VipAccountServiceImpl extends ServiceImpl<VipAccountMapper, VipAccount> {
}
