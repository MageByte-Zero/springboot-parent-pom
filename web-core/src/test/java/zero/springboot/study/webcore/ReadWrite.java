package zero.springboot.study.webcore;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWrite {

    private ReadWrite() {
    }

    private static class singleFactory {
        private static final ReadWrite INSTANCE = new ReadWrite();
    }

    public static ReadWrite getInstance() {
        return singleFactory.INSTANCE;
    }

    /* 共享数据，只能一个线程写数据，可以多个线程读数据 */
    private Object data = null;
    /* 创建一个读写锁 */
    ReadWriteLock rwlock = new ReentrantReadWriteLock();

    /**
     * 读数据，可以多个线程同时读， 所以上读锁即可
     */
    public void get() {
        /* 上读锁 */
        rwlock.readLock().lock();

        try {
            System.out.println(Thread.currentThread().getName() + " 准备读数据!");
            /* 休眠 */
            Thread.sleep((long) (Math.random() * 1000));
            System.out.println(Thread.currentThread().getName() + "读出的数据为 :" + data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rwlock.readLock().unlock();
        }

    }

    /**
     * 写数据，多个线程不能同时 写 所以必须上写锁
     *
     * @param data
     */
    public void put(Object data) {

        /* 上写锁 */
        rwlock.writeLock().lock();

        try {
            System.out.println(Thread.currentThread().getName() + " 准备写数据!");
            /* 休眠 */
            Thread.sleep((long) (Math.random() * 1000));
            this.data = data;
            System.out.println(Thread.currentThread().getName() + " 写入的数据: " + data);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwlock.writeLock().unlock();
        }
    }
}
