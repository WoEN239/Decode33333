package org.woen.core.utils;


public interface InitializableWith<T> {
    void initialize(T with);
    boolean isInitialized();
}
