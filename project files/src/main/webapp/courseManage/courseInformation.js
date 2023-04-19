function updateCourseInformation(){
    let joinMode;
    if(document.getElementById("joinMode").value === "仅允许主讲教师导入学生名单"){
        joinMode = 0;
    }
    else if(document.getElementById("joinMode").value === "仅允许本校学生添加"){
        joinMode = 1;
    }
    else if(document.getElementById("joinMode").value === "允许任何人通过课程号添加"){
        joinMode = 2;
    }
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", getServer()+"/course", true);
    xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    let courseId = window.location.search.split("id=")[1];
    xmlHttp.send("courseName=" + document.getElementById("courseName").value +
        "&startDate=" + document.getElementById("startDate").value +
        "&endDate=" + document.getElementById("endDate").value +
        "&joinMode=" + joinMode +
        "&courseId=" + courseId);
    xmlHttp.onreadystatechange = function (){
        if (this.readyState === 4 && this.status === 200) {
            let res = JSON.parse(xmlHttp.responseText);
            if (res.success === true) {
                window.location.href = "/courseManage/courseManage.html" + window.location.search;
                window.alert(res.text);
                return true;
            } else {
                document.getElementById("message").innerText = res.text;
                return false;
            }
        }
    }
}

function moveToManage(){
    window.location.href = "courseManage.html" + window.location.search;
}

window.onload = function (){
    // document.getElementById("topMenu").innerHTML = getTopMenuForManage();
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("get", getServer()+"/course/get"+window.location.search, true);
    xmlHttp.send();
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
            let courseData = JSON.parse(xmlHttp.responseText);
			console.log(courseData);
            window.document.getElementById("courseName").value = courseData.courseName;
            window.document.getElementById("startDate").value = formatDate(courseData.startDate);
            window.document.getElementById("endDate").value = formatDate(courseData.endDate);
            if (courseData.joinMode === 0) {
                window.document.getElementById("joinMode").value = "仅允许主讲教师导入学生名单";
            } else if (courseData.joinMode === 1) {
                window.document.getElementById("joinMode").value = "仅允许本校学生添加";
            } else if (courseData.joinMode === 2) {
                window.document.getElementById("joinMode").value = "允许任何人通过课程号添加";
            }
        }
    }
}

function formatDate(parament){
    return parament.split("T")[0];
}