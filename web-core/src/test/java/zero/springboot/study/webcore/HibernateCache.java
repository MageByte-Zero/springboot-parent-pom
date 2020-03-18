package zero.springboot.study.webcore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class HibernateCache {

    /* 定义一个Map来模拟缓存 */
    private Map<String, Object> cache = new HashMap<String, Object>();

    /* 创建一个读写锁 */
    private ReadWriteLock rwLock = new ReentrantReadWriteLock();

    /**
     * 模拟Hibernate缓存,优先缓存，若缓存不存在写锁更新
     *
     * @param key
     * @return
     */
    public Object getData(String key) {

        /* 上读锁 */
        rwLock.readLock().lock();
        /* 定义从缓存中读取的对象 */
        Object value = null;

        try {
            /* 从缓存中读取数据 */
            value = cache.get(key);

            if (value == null) {
                /* 如果缓存中没有数据，我们就把读锁关闭，直接上写锁【让一个线程去数据库中取数据】 */
                rwLock.readLock().unlock();
                /* 上写锁 */
                rwLock.writeLock().lock();

                try {
                    /* 上了写锁之后再判断一次【我们只让一个线程去数据库中取值即可，当第二个线程过来的时候，发现value不为空了就去缓存中取值】 */
                    if (value == null) {
                        /* 模拟去数据库中取值 */
                        value = "hello";
                        System.out.println("修改换缓存");
                        cache.put(key, value);
                    }
                } finally {
                    /* 写完之后把写锁关闭 */
                    rwLock.writeLock().unlock();
                }
                /* 缓存中已经有了数据，我们再把已经 关闭的读锁打开 */
                rwLock.readLock().lock();
            }
            return value;

        } finally {
            /* 最后把读锁也关闭 */
            rwLock.readLock().unlock();
        }

    }

    public Map<String, Object> getCache() {
        return cache;
    }

    public void setCache(Map<String, Object> cache) {
        this.cache = cache;
    }
}