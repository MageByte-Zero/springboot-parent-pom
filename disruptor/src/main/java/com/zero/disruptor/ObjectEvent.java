package com.zero.disruptor;

import lombok.Data;

import java.io.Serializable;

/**
 * 事件对象，包装传递的数据
 * @param <T>
 */
@Data
public class ObjectEvent<T> implements Serializable {

    private T obj;
}
