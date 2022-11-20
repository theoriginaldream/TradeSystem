package com.example.service;

import com.example.pojo.User;

import java.util.List;

public interface UserService {
    int addUser(User user);

    int deleteUser(String userid);

    int updateUser(User user);

    User queryUserByName(String userid);

    User queryUserByPhone(String phone);

    List<User> queryAllUser();
}
