package cn.cusanity.travel.web.servlet;

import cn.cusanity.travel.domain.Category;
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
}
