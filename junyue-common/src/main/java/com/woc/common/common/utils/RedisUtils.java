package com.woc.common.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {


    @Autowired
    private  RedisTemplate redisTemplate;



    public  void set(String key,Object value){
        redisTemplate.opsForValue().set(key,value);
    }
    public void set(String key,Object value,long time){
        redisTemplate.opsForValue().set(key,value,time);
    }
    public void set(String key, Object value, long time,TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key,value,time,timeUnit);
    }
    public long setIncr(String key){
        return setIncr(key,1);
    }
    public long setIncr(String key, long incr,long time){
        return setIncr(key,incr,time,TimeUnit.SECONDS);
    }
    public long setIncr(String key, long incr,long time,TimeUnit timeUnit){
        return setIncr(key,incr,time,timeUnit,false);
    }
    public long setIncr(String key, long incr,long time,TimeUnit timeUnit,boolean reExpireTime){
        long count = redisTemplate.opsForValue().increment(key, incr);

        if(reExpireTime){
            redisTemplate.expire(key,time,timeUnit);
        }else{
            Long expire = redisTemplate.getExpire(key);
            if(expire == -1){
                redisTemplate.expire(key,time,timeUnit);
            }

        }
        return count;

    }

    public long setIncr(String key, long incr){
        return redisTemplate.opsForValue().increment(key,incr);
    }



    public String getStr(String key){
        Object o = redisTemplate.opsForValue().get(key);
        if(o ==null){
            return null;
        }
        return (String)o;
    }

    public Integer getInt(String key){
        Object o = redisTemplate.opsForValue().get(key);
        if( o == null){
            return null;
        }
        return (Integer) o;
    }

    public long getLong(String key){
        return (Long) redisTemplate.opsForValue().get(key);
    }

    public long getIncr(String key){
        Object o = redisTemplate.opsForValue().get(key);
        if(o != null){

            return Long.parseLong(redisTemplate.opsForValue().get(key).toString());
        }
        return 0;
    }

    public long getExpire(String key){
        return redisTemplate.getExpire(key);
    }

    public <T> T getObj(String key,Class T) {

        Object o = redisTemplate.opsForValue().get(key);
        if (o == null) {
            return null;
        }
        return (T)o;
    }

    public boolean delete(String key){
        return redisTemplate.delete(key);
    }

    /**
     *  最终加强分布式锁
     *
     * @param key key值
     * @return 是否获取到
     */
    public boolean lock(String key,long time,TimeUnit timeUnit){
        String lock = key;
        // 利用lambda表达式
        return (Boolean) redisTemplate.execute((RedisCallback) connection -> {
            long LOCK_EXPIRE = timeUnit.toMillis(time);
            long expireAt = System.currentTimeMillis() + LOCK_EXPIRE + 1;
            Boolean acquire = connection.setNX(lock.getBytes(), String.valueOf(expireAt).getBytes());


            if (acquire) {
                return true;
            } else {

                byte[] value = connection.get(lock.getBytes());

                if (Objects.nonNull(value) && value.length > 0) {

                    long expireTime = Long.parseLong(new String(value));
                    // 如果锁已经过期
                    if (expireTime < System.currentTimeMillis()) {
                        // 重新加锁，防止死锁
                        byte[] oldValue = connection.getSet(lock.getBytes(), String.valueOf(System.currentTimeMillis() + LOCK_EXPIRE + 1).getBytes());
                        return Long.parseLong(new String(oldValue)) < System.currentTimeMillis();
                    }
                }
            }
            return false;
        });
    }


}
