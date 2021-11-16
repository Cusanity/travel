package cn.cusanity.travel.service.impl;

import cn.cusanity.travel.dao.UserDao;
import cn.cusanity.travel.dao.impl.UserDaoImpl;
import cn.cusanity.travel.domain.User;
import cn.cusanity.travel.service.UserService;
import cn.cusanity.travel.util.MailUtils;
import cn.cusanity.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {

    private final UserDao userDao = new UserDaoImpl();

    @Override
    public boolean register(User user) {
        User u = userDao.findByUsername(user.getUsername());
        if(u != null){
            //same username exists, registration failed
            return false;
        }
        //set verification code
        user.setCode(UuidUtil.getUuid());
        //Set activation status to 'N'
        user.setStatus("N");
        userDao.save(user);

        //Send activation Email

        String content="<a href='http://localhost/travel/activeUserServlet?code="+user.getCode()+"'>Activate your account.</a>";

        MailUtils.sendMail(user.getEmail(),content,"Activate your account");

        return true;
    }

    @Override
    public boolean activate(String code) {
        User user = userDao.findByCode(code);
        if(user != null){
            userDao.updateStatus(user);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public User login(User user) {
        return userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }
}
