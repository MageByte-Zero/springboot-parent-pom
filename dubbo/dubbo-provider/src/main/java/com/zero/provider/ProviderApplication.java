/**
 * Project: springboot-parent-pom
 * File created at 2019/4/23 20:14
 */
package com.zero.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lijianqing
 * @version 1.0
 * @ClassName ConsumerApplication
 * @date 2019/4/23 20:14
 */
@SpringBootApplication
public class ProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}
