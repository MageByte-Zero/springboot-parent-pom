/**
 * Project: springboot-parent-pom
 * File created at 2019/4/23 17:33
 */
package com.zero.api.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lijianqing
 * @version 1.0
 * @ClassName User
 * @date 2019/4/23 17:33
 */
@Data
public class User implements Serializable {
    private Long id;
    private String username;
}
