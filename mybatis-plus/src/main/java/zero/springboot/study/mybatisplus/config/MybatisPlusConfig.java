/**
 * Project: springboot-parent-pom
 * File created at 2019/4/22 17:11
 */
package zero.springboot.study.mybatisplus.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author lijianqing
 * @version 1.0
 * @ClassName MybatisPlusConfig
 * @date 2019/4/22 17:11
 */
@Configuration
@MapperScan("zero.springboot.study.mybatisplus.mapper")
public class MybatisPlusConfig {
}
