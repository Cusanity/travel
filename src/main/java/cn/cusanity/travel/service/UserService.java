package cn.cusanity.travel.service;

import cn.cusanity.travel.domain.User;

public interface UserService {
    boolean register(User user);
    boolean activate(String code);
    User login(User user);
}
