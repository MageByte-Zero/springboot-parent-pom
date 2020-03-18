/**
 * Project: springboot-parent-pom
 * File created at 2019/4/22 17:00
 */
package zero.springboot.study.mybatisplus.mapper.master;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import zero.springboot.study.mybatisplus.domain.VipAccount;

/**
 * @author lijianqing
 * @version 1.0
 * @ClassName VipAccountMapp
 * @date 2019/4/22 17:00
 */
@DS("master")
public interface VipAccountMapper extends BaseMapper<VipAccount> {

}
