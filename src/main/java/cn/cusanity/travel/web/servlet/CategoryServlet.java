package cn.cusanity.travel.web.servlet;

import cn.cusanity.travel.domain.Category;
import cn.cusanity.travel.domain.PageBean;
import cn.cusanity.travel.domain.Route;
import cn.cusanity.travel.domain.User;
import cn.cusanity.travel.service.CategoryService;
import cn.cusanity.travel.service.impl.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
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
        String rname = request.getParameter("rname");
        //Default 'currentPage' value is 1
        int currentPage = (currentPageStr != null && currentPageStr.length() > 0) ? Integer.parseInt(currentPageStr) : 1;
        //Default 'itemPerPage' value is 10
        int itemPerPage = (itemPerPageStr != null && itemPerPageStr.length() > 0) ? Integer.parseInt(itemPerPageStr) : 10;
        int cid = (cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)) ? Integer.parseInt(cidStr) : 5;
        rname = (rname == null || rname.length() == 0) ? null : new String(rname.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        PageBean<Route> routes = categoryService.getRoutes(currentPage, itemPerPage, cid, rname);
        this.jsonResponse(routes, response);
    }

    /**
     * Query a Route according to 'rid' in request
     */
    public void findARoute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String rid = request.getParameter("rid");
        Route route = categoryService.findARoute(Integer.parseInt(rid));
        jsonResponse(route, response);
    }

    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String rid = request.getParameter("rid");
        //Check rid
        if (rid == null || rid.length() == 0) return;
        User user = (User) request.getSession().getAttribute("user");
        int uid = (user == null) ? 0 : user.getUid();
        jsonResponse(categoryService.findFavByRidCid(Integer.parseInt(rid), uid), response);
    }

    public void updateFav(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user == null) {
            //Not logged-in => NO modification
            return;
        } else {
            uid = user.getUid();
        }
        int fav_num;
        if (categoryService.findFavByRidCid(Integer.parseInt(rid), uid)) {
            //Already bookmarked => Un-bookmark
            fav_num = categoryService.updateFav(Integer.parseInt(rid), uid, false);
        } else {
            //Not bookmarked yet => Bookmark
            fav_num = categoryService.updateFav(Integer.parseInt(rid), uid, true);
        }
        jsonResponse(fav_num, response);
    }

    public void getFavsByUid(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user == null) {
            //Not logged-in => NO modification
            return;
        } else {
            uid = user.getUid();
        }
        PageBean<Route> favsByUid = categoryService.getFavsByUid(uid);
        this.jsonResponse(favsByUid, response);
    }

    public void getTopFavs(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String top_num_str = request.getParameter("top_num");
        //Default: Get 10 most favorited routes
        int top_num = (top_num_str == null || top_num_str.length() == 0) ? 10 : Integer.parseInt(top_num_str);
        PageBean<Route> topFavs = categoryService.getTopFavs(top_num);
        this.jsonResponse(topFavs, response);
    }
}