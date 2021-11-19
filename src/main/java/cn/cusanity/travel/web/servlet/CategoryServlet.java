package cn.cusanity.travel.web.servlet;

import cn.cusanity.travel.domain.Category;
import cn.cusanity.travel.domain.PageBean;
import cn.cusanity.travel.domain.Route;
import cn.cusanity.travel.service.CategoryService;
import cn.cusanity.travel.service.impl.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    private final CategoryService categoryService = new CategoryServiceImpl();

    public void getCategoryList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Category> cl = categoryService.getCategoryList();
        //json serialization and response
        this.jsonResponse(cl, response);
    }

    public void getRoutes(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // client ajax request args:
        // 1. currentPage
        // 2. itemPerPage
        // 3. cid
        String currentPageStr = request.getParameter("currentPage");
        String itemPerPageStr = request.getParameter("itemPerPage");
        String cidStr = request.getParameter("cid");
        //Default 'currentPage' value is 1
        int currentPage = (currentPageStr != null && currentPageStr.length() > 0) ? Integer.parseInt(currentPageStr) : 1;
        //Default 'itemPerPage' value is 10
        int itemPerPage = (itemPerPageStr != null && itemPerPageStr.length() > 0) ? Integer.parseInt(itemPerPageStr) : 10;
        int cid = (cidStr != null && cidStr.length() > 0) ? Integer.parseInt(cidStr) : 1;

        PageBean<Route> routes = categoryService.getRoutes(currentPage, itemPerPage, cid);
        this.jsonResponse(routes, response);
    }
}
