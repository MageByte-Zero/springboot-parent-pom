/**
 * Project: springboot-parent-pom
 * File created at 2019/4/23 19:46
 */
package com.zero.provider.impl;

import com.google.common.collect.Lists;
import com.zero.api.model.User;
import com.zero.api.provider.UserProvider;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;

/**
 * @author lijianqing
 * @version 1.0
 * @ClassName UserProviderImpl
 * @date 2019/4/23 19:46
 */
@Service(interfaceClass = UserProvider.class)
public class UserProviderImpl implements UserProvider {
    @Override
    public List<User> listUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("青龙");
        return Lists.newArrayList(user);
    }
}
