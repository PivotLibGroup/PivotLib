package com.pivotgroup.pivotlib.sample.solid.domain;

/**
 * @author xinliu
 * @email 3536370493@qq.com
 * @date 2026/11/18 12:03
 */

public class User {

    private final String name;
    private final String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
