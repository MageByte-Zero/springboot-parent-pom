/**
 * Project: springboot-parent-pom
 * File created at 2019/4/23 20:17
 */
package com.zero.dubbo.consumer.controller;

import com.zero.api.model.User;
import com.zero.dubbo.consumer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lijianqing
 * @version 1.0
 * @ClassName UserController
 * @date 2019/4/23 20:17
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public Object listUser() {
        List<User> list = userService.listUser();
        return list;
    }
}
