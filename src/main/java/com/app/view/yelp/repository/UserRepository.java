package com.app.view.yelp.repository;

import com.app.view.yelp.model.User;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CassandraRepository<User,String> {

    @AllowFiltering
    public User findByUsername(String username);
}
