/**
 * Project: springboot-parent-pom
 * File created at 2019/4/23 17:35
 */
package com.zero.api.provider;

import com.zero.api.model.User;

import java.util.List;

/**
 * @author lijianqing
 * @version 1.0
 * @ClassName UserProvider
 * @date 2019/4/23 17:35
 */
public interface UserProvider {
    List<User> listUser();
}
