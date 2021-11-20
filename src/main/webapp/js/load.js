function getParameter(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = location.search.substr(1).match(reg);
    if (r != null) return (r[2]);
    return null;
}

function load(cid, currentPage, rname) {
    $.get("category/getRoutes", {cid: cid, currentPage: currentPage, rname: rname}, function (pageBean) {
        //Pagination
        $("#totalPage").html(pageBean.totalPage);
        $("#totalCount").html(pageBean.totalCount);
        let page_list = "";
        let homePage = '<li class="threeword" onclick="load(' + cid + ', 1, \'' + rname + '\')"><a>' + ' First ' + '</a></li>';
        let prev_num = Math.max(1, pageBean.currentPage - 1);
        let prePage = '<li class="threeword" onclick="load(' + cid + ', ' + prev_num + ', \'' + rname + '\')"><a>' + ' First ' + '</a></li>';
        page_list += homePage;
        page_list += prePage;

        let begin_page = pageBean.totalPage < 10 ? 1 : pageBean.currentPage - 5;
        let end_page = pageBean.totalPage < 10 ? pageBean.totalPage : pageBean.currentPage + 4;
        begin_page = Math.max(1, begin_page);
        end_page = Math.min(pageBean.totalPage, end_page);
        for (let i = begin_page; i <= end_page; i++) {
            let li = (pageBean.currentPage === i) ?
                '<li class="curPage" onclick="load(' + cid + ',' + i + ',\'' + rname + '\')"><a href="javascript:void(0);">' + i + '</a></li>' :
                '<li onclick="load(' + cid + ',' + i + ',\'' + rname + '\')"><a href="javascript:void(0);">' + i + '</a></li>';
            page_list += li;
        }
        let next_num = Math.min(pageBean.totalPage, pageBean.currentPage + 1);
        let nextPage = '<li onclick="load(' + cid + ',' + next_num + ',\'' + rname + '\')" class="threeword"><a href="#">' + ' Next ' + '</a></li>';
        let lastPage = '<li class="threeword" onclick="load(' + cid + ',' + pageBean.totalPage + ',\'' + rname + '\')" "><a href="javascript:void(0);">' + ' Last ' + '</a></li>';
        page_list += nextPage;
        page_list += lastPage;

        $("#pageNum").html(page_list);

        //Route List
        //route data format itemList[{rid: rname: price: routeIntroduce:}]
        let route_list = "";
        for (let i = 0; i < pageBean.itemList.length; i++) {
            let route = pageBean.itemList[i];
            let li = '<li>\n' +
                '<div class="img"><img src="' + route.rimage + '" style="width: 299px;"></div>\n' +
                '<div class="text1">\n' +
                '    <p>' + route.rname + '</p>\n' +
                '    <br/>\n' +
                '    <p>' + route.routeIntroduce + '</p>\n' +
                '</div>\n' +
                '<div class="price">\n' +
                '    <p class="price_num">\n' +
                '        <span>&yen;</span>\n' +
                '        <span>' + route.price + '</span>\n' +
                '    </p>\n' +
                '    <p><a href="route_detail.html?rid=' + route.rid + '">Detail</a></p>\n' +
                '</div>\n' +
                '                    </li>';
            route_list += li;
        }
        $("#route_list").html(route_list);
        window.scrollTo(0, 0);
    })
}

$(function () {
    let cid = getParameter("cid");
    let rname = getParameter("rname");
    if (rname) {
        rname = window.decodeURIComponent(rname);
    }
    load(cid, 1, rname);
})