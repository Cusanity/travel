package cn.cusanity.travel.dao;

import cn.cusanity.travel.domain.User;

public interface UserDao {

    User findByUsername(String username);

    void save(User user);

    User findByCode(String code);

    void updateStatus(User user);

    User findByUsernameAndPassword(String username, String password);
}
