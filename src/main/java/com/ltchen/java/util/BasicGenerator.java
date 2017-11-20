package com.ltchen.java.util;

/**
 * @author : ltchen
 * @date : 2017/11/21
 * @desc : 传递的类型必须有默认构造方法; 传递类型必须能被 BasicGenerator<T> 访问到;
 */
public class BasicGenerator<T> implements Generator<T> {

    private Class<T> type;

    public BasicGenerator(Class<T> type) {
        this.type = type;
    }

    @Override
    public T next() {
        try {
            return type.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据传递的类型返回默认的生成器
     * @param type 类型
     * @param <T> 泛型
     * @return Generator<T>
     */
    public static <T> Generator<T> create(Class<T> type) {
        return new BasicGenerator<T>(type);
    }
}
