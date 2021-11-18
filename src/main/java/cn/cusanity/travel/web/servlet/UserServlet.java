package cn.cusanity.travel.web.servlet;

import cn.cusanity.travel.domain.ResultInfo;
import cn.cusanity.travel.domain.User;
import cn.cusanity.travel.service.UserService;
import cn.cusanity.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private final UserServiceImpl service = new UserServiceImpl();

    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //verification code validation
        String check = request.getParameter("check");
        //get verification code in session
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//one-time verification code
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            //Wrong Verification Code
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("Wrong Verification Code");
            this.jsonResponse(info, response);
            return;
        }

        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        boolean flag = service.register(user);
        ResultInfo info = new ResultInfo();
        //
        if (flag) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
            info.setErrorMsg("Register Failed");
        }

        //Serialize the 'info' object
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);

        //set content-type to application.json
        this.jsonResponse(json, response);
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        User u = service.login(user);

        ResultInfo info = new ResultInfo();

        if (u == null) {
            //Wrong username/password
            info.setFlag(false);
            info.setErrorMsg("Wrong username/password");
        } else {
            //Check if the email is verified
            if ("N".equals(u.getStatus())) {
                info.setFlag(false);
                info.setErrorMsg("Your email has not been verified yet.");
            }
            //Login successfully
            else {
                request.getSession().setAttribute("user", u);
                info.setFlag(true);
            }
        }

        //Response
        this.jsonResponse(info, response);
    }

    public void findOneUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //Get user info from the session
        Object user = request.getSession().getAttribute("user");

        //Response
        this.jsonResponse(user, response);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //Destroy session
        request.getSession().invalidate();

        //Redirect to login page
        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    public void activate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = request.getParameter("code");
        if (code != null) {
            boolean flag = service.activate(code);
            String msg = flag ? "Activation Succeeded, <a href='login.html'>LOGIN</a>" : "Activation FAILED, <a href='register.html'>Register Again</a>";
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }

}
