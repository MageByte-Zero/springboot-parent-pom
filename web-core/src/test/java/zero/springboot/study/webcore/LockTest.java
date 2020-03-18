/**
 * Project: springboot-parent-pom
 * File created at 2019/4/25 19:05
 */
package zero.springboot.study.webcore;

import java.util.Random;

/**
 * @author lijianqing
 * @version 1.0
 * @ClassName LockTest
 * @date 2019/4/25 19:05
 */
public class LockTest {
    public static void main(String[] args) {
        ReadWrite readWrite = ReadWrite.getInstance();


        for (int i = 0; i < 8; i++) {
            /* 创建并启动8个读线程 */
            new Thread(() -> readWrite.get()).start();

            /*创建8个写线程*/
            new Thread(() -> readWrite.put(new Random().nextInt(8))).start();
        }
    }


}
