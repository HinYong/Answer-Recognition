function courseInformation(){
    window.location.href = "courseInformation.html" + window.location.search;
}

function addLesson(){
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("PUT", getServer()+"/lesson"+window.location.search, true);
    xmlHttp.send();
    xmlHttp.onreadystatechange = function (){
        if(xmlHttp.readyState === 4 && xmlHttp.status === 200){
            location.reload();
        }
    }
}

function deleteCheck(lessonId){
    let check = confirm("确定要删除本节内容吗？");
    if(check === true){
        let xmlHttp = new XMLHttpRequest();
        xmlHttp.open("DELETE", getServer()+"/lesson", true);
        xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlHttp.send("lessonId=" + lessonId);
        xmlHttp.onreadystatechange = function (){
            if(xmlHttp.readyState === 4 && xmlHttp.status === 200){
                location.reload();
            }
        }
    }
    else {}
}

function writeCourseInfo (courseData){
    document.getElementById("title").innerText = courseData.courseName;
    document.getElementById("courseName").innerText = courseData.courseName;
	document.getElementById("courseId").innerText = "课程号：" + window.location.search.split("id=")[1];
    let courseTime = "开课时间：" + new Date(courseData.startDate).toLocaleDateString() + '\n';
    courseTime += "结课时间：" + new Date(courseData.endDate).toLocaleDateString();
    document.getElementById("courseTime").innerText = courseTime;
}

function writeLessonList (lessonData){
    let lessonList = "";
    for(let i = 0;i<lessonData.length;i++){
        lessonList += "<li class='pure-menu-item'><a href='lessonManage.html?id=" + lessonData[i].lessonId + "' class='pure-menu-link titleLink'>" +
            lessonData[i].lessonName + "</a><a onclick='deleteCheck(" + lessonData[i].lessonId + ")' class='pure-menu-link deleteLink'>删除</a></li>";
    }
    document.getElementById("lessonList").innerHTML = lessonList;
}

window.onload = function (){
    document.getElementById("topMenu").innerHTML = getTopMenuForManage(window.location.search.split("id=")[1]);

    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("get", getServer()+"/lesson/list"+window.location.search, true);
    xmlHttp.send();
    xmlHttp.onreadystatechange = function (){
        if(xmlHttp.readyState === 4 && xmlHttp.status === 200){
            let data = JSON.parse(xmlHttp.responseText);
            let courseData = data[0];
            let lessonData = [];
            for(let i = 1;i<data.length;i++){
                lessonData.push(data[i]);
            }

            writeCourseInfo(courseData);
            writeLessonList(lessonData);
        }
    }
}

function getDate(para){
    let str = "";
    str += " " + para.getFullYear() + "/";
    str += para.getMonth()<9 ? "0"+(para.getMonth()+1) : para.getMonth()+1;
    str += "/";
    str += para.getDate()<10 ? "0"+para.getDate() : para.getDate();
    return str;
}