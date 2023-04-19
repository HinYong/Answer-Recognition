window.onload = function (){
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

            document.getElementById("topMenu").innerHTML = getTopMenu(courseData.courseId);
            document.getElementById("title").innerText = courseData.courseName;
            document.getElementById("courseName").innerText = courseData.courseName;
            let courseTime = "开课时间：" + new Date(courseData.startDate).toLocaleDateString() + '\n';
            courseTime += "结课时间：" + new Date(courseData.endDate).toLocaleDateString();
            document.getElementById("courseTime").innerText = courseTime;

            let lessonList = "";
            for(let i = 0;i<lessonData.length;i++){
                lessonList += "<li class='pure-menu-item'><a href='lesson.html?id=" + lessonData[i].lessonId + "' class='pure-menu-link titleLink'>" +
                    lessonData[i].lessonName + "</a></li>";
            }
            document.getElementById("lessonList").innerHTML = lessonList;
        }
    }
}
