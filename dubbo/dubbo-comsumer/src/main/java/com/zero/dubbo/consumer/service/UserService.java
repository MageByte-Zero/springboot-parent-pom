/**
 * Project: springboot-parent-pom
 * File created at 2019/4/23 20:18
 */
package com.zero.dubbo.consumer.service;

import com.zero.api.model.User;
import com.zero.api.provider.UserProvider;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lijianqing
 * @version 1.0
 * @ClassName UserService
 * @date 2019/4/23 20:18
 */
@Service
public class UserService {

    @Reference
    private UserProvider userProvider;

    public List<User> listUser() {
        return userProvider.listUser();
    }
}
