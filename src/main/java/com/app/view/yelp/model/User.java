package com.app.view.yelp.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Table
public class User {
    @PrimaryKey
    private int id;
    @Column
    private String username;
    @Column
    private String password;

    public User() {
    }

    public User(int iId, String iUsername, String iPassword) {
        id = iId;
        username = iUsername;
        password = iPassword;
    }

    public int getId() {
        return id;
    }

    public void setId(int iId) {
        id = iId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String iUsername) {
        username = iUsername;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String iPassword) {
        password = iPassword;
    }

    @Override
    public String toString() {
        return new org.apache.commons.lang3.builder.ToStringBuilder(this)
                .append("id", id)
                .append("username", username)
                .append("password", password)
                .toString();
    }
}
