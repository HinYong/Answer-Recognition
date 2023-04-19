function getServer(){
    return "http://localhost:8080";
}

function getTopMenu(id){
    return "<div class=\"pure-menu pure-menu-horizontal topMenu\">\n" +
        "    <a href=\"courseList.html\" class=\"pure-menu-heading pure-menu-link topLink\">课程列表</a>\n" +
        "    <ul class=\"pure-menu-list\">\n" +
        "        <li class=\"pure-menu-item\"><a href=\"course.html?id="+ id +"\" class=\"pure-menu-link topLink\">学习</a></li>\n" +
        "        <li class=\"pure-menu-item\"><a href=\"assignmentList.html?id="+ id +"\" class=\"pure-menu-link topLink\">作业</a></li>\n" +
        "        <li class=\"pure-menu-item\"><a href=\"../information/information.html\" class=\"pure-menu-link topLink\">个人信息</a></li>\n" +
        "    </ul>\n" +
        "    </div>";
}

function getTopMenuForManage(id){
    return "<div class=\"pure-menu pure-menu-horizontal topMenu\">\n" +
        "        <a href=\"../course/courseList.html\" class=\"pure-menu-heading pure-menu-link topLink\">课程列表</a>\n" +
        "        <ul class=\"pure-menu-list\">\n" +
        "            <li class=\"pure-menu-item\"><a href=\"courseManage.html?id="+ id +"\" class=\"pure-menu-link topLink\">课程管理</a></li>\n" +
        "            <li class=\"pure-menu-item\"><a href=\"assignmentListManage.html?id="+ id +"\" class=\"pure-menu-link topLink\">作业管理</a></li>\n" +
        "            <li class=\"pure-menu-item\"><a href=\"studentManage.html?id="+ id +"\" class=\"pure-menu-link topLink\">学生管理</a></li>\n" +
        "            <li class=\"pure-menu-item\"><a href=\"courseLogAnalysis.html?id="+ id +"\" class=\"pure-menu-link topLink\">数据分析</a></li>\n" +
        "            <li class=\"pure-menu-item\"><a href=\"wordRecognition.html?id="+ id +"\" class=\"pure-menu-link topLink\">文字识别</a></li>\n" +
        "            <li class=\"pure-menu-item\"><a href=\"exam.html?id="+ id +"\" class=\"pure-menu-link topLink\">考试信息</a></li>\n" +
        "            <li class=\"pure-menu-item\"><a href=\"../information/information.html\" class=\"pure-menu-link topLink\">个人信息</a></li>\n" +
        "        </ul>\n" +
        "    </div>";
}