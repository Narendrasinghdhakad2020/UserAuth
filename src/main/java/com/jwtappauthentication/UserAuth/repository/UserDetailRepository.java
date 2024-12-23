package com.jwtappauthentication.UserAuth.repository;

import com.jwtappauthentication.UserAuth.model.UserDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.User;

public interface UserDetailRepository extends MongoRepository<UserDetails,String> {
    public UserDetails findUserByUsername(String username); //find user by username
    public boolean existsUserByUsername(String username);//check if user already exist

    User findByUsername(String username);
}
