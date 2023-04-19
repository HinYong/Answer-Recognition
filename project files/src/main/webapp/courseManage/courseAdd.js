function addCourse(){
    if(document.getElementById("courseName").value.length <= 0){
        document.getElementById("message").innerText = "请填写课程名称";
        return false;
    }
    if(document.getElementById("startDate").value.length <= 0){
        document.getElementById("message").innerText = "请填写开始日期";
        return false;
    }
    if(document.getElementById("endDate").value.length <= 0){
        document.getElementById("message").innerText = "请填写结束日期";
        return false;
    }

    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("PUT", getServer()+"/course/add", true);
    xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded")
    let joinMode = 0;
    if(document.getElementById("joinMode").value === "仅允许主讲教师导入学生名单"){
        joinMode = 0;
    }
    else if(document.getElementById("joinMode").value === "仅允许本校学生添加"){
        joinMode = 1;
    }
    else if(document.getElementById("joinMode").value === "允许任何人通过课程号添加"){
        joinMode = 2;
    }
    xmlHttp.send("courseName=" + document.getElementById("courseName").value
        + "&startDate=" + document.getElementById("startDate").value
        + "&endDate=" + document.getElementById("endDate").value
        + "&joinMode=" + joinMode);
    xmlHttp.onreadystatechange = function (){
        if (this.readyState === 4 && this.status === 200) {
            let res = JSON.parse(xmlHttp.responseText);
            if (res.success === true) {
                window.location.href = "../course/courseList.html";
                window.alert(res.text);
                return true;
            } else {
                document.getElementById("message").innerText = res.text;
                return false;
            }
        }
    }
}