/**
 * Project: springboot-parent-pom
 * File created at 2019/4/22 17:04
 */
package zero.springboot.study.mybatisplus.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lijianqing
 * @version 1.0
 * @ClassName VipAccount
 * @date 2019/4/22 17:04
 */
@Data
public class VipAccount implements Serializable {
    private Long id;
    private String username;
}
