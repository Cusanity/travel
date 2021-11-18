package cn.cusanity.travel.web.servlet;


import cn.cusanity.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeUserServlet")
public class ActivateUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        if (code != null) {
            UserServiceImpl userService = new UserServiceImpl();
            boolean flag = userService.activate(code);
            String msg = flag ? "Activation Succeeded, <a href='login.html'>LOGIN</a>" : "Activation FAILED, <a href='register.html'>Register Again</a>";
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().write(msg);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
