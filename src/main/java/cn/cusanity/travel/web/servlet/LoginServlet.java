package cn.cusanity.travel.web.servlet;

import cn.cusanity.travel.domain.ResultInfo;
import cn.cusanity.travel.domain.User;
import cn.cusanity.travel.service.UserService;
import cn.cusanity.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        //Lookup using UserService
        UserService service = new UserServiceImpl();
        User u = service.login(user);

        ResultInfo info = new ResultInfo();

        if (u == null) {
            //Wrong username/password
            info.setFlag(false);
            info.setErrorMsg("Wrong username/password");
        }
        else{
            //Check if the email is verified
            if("N".equals(u.getStatus())){
                info.setFlag(false);
                info.setErrorMsg("Your email has not been verified yet.");
            }
            //Login successfully
            else{
                request.getSession().setAttribute("user", u);
                info.setFlag(true);
            }
        }

        //Response
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), info);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doPost(request, response);
    }
}
