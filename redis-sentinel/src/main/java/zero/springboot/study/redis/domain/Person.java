/**
 * Project: springboot-parent-pom
 * File created at 2019/4/17 11:08
 */
package zero.springboot.study.redis.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lijianqing
 * @version 1.0
 * @ClassName Person
 * @date 2019/4/17 11:08
 */
@Data
public class Person implements Serializable {
    private Long id;
    private String username;
}
