package com.bol.interview.kalahaapi.abstraction;

/**
 * This is the generic class for the implementation of the id
 * @param <T> - return identity of specific type like String
 */
public interface Identity <T>{

    T getId();

    void setId(T id);
}
