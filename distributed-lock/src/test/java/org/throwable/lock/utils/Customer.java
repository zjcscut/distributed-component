package org.throwable.lock.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author throwable
 * @version v1.0
 * @function
 * @since 2017/5/18 17:38
 */
public class Customer {

    @JsonProperty
    private String name;
    @JsonProperty
    private Integer age;

    public Customer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
