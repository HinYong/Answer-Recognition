function addCourse(){
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("get",getServer()+"/session",true);
    xmlHttp.send();
    xmlHttp.onreadystatechange = function (){
        if (this.readyState === 4 && this.status === 200) {
            let res = JSON.parse(xmlHttp.responseText);
            if(res.role === "student"){//登录身份为学生则输入课程号添加已有课程
                let courseId = prompt("请输入课程号");
                let xmlHttp2 = new XMLHttpRequest();
                xmlHttp2.open("put", getServer()+"/course/select", true);
                xmlHttp2.setRequestHeader("Content-type", "application/x-www-form-urlencoded")
                xmlHttp2.send("courseId=" + courseId);
                xmlHttp2.onreadystatechange = function (){
                    if(xmlHttp2.readyState === 4 && xmlHttp2.status === 200){
                        let message = JSON.parse(xmlHttp2.responseText);
                        window.alert(message.text);
                    }
                }
            }
            else {//登录身份为教师则跳转至创建新课程页面
                window.location.href = "../courseManage/courseAdd.html";
            }
        }
    }
}

function writeList(Data, isStudent){
    let obj = JSON.parse(Data);
    if(obj.length>0){
        // document.getElementById("courseList").innerHTML = "<tr><td>有课程</td></tr>";
        let listHtml= "";
        if(isStudent){
            for(let i = 0;i<obj.length;i++){
                listHtml = listHtml + "<div class='pure-u-1-3' onclick='location.href=\"course.html?id=" + obj[i].courseId + "\"'>";
                listHtml = listHtml + "<img class='pure-img' src='../img/2.png' alt='course'>";
                listHtml = listHtml + "<p class='courseName'>" + obj[i].courseName + "</p>";
                listHtml = listHtml + "<p class='teacher'>" + obj[i].teacher + "</p>";
                listHtml = listHtml + "</div>"
            }
        } else {
            for(let i = 0;i<obj.length;i++) {
                listHtml = listHtml + "<div class='pure-u-1-3' onclick='location.href=\"/courseManage/courseManage.html?id=" + obj[i].courseId + "\"'>";
                listHtml = listHtml + "<img class='pure-img' src='../img/2.png' alt='course'>";
                listHtml = listHtml + "<p class='courseName'>" + obj[i].courseName + "</p>";
                listHtml = listHtml + "<p class='teacher'>" + obj[i].teacher + "</p>";
                listHtml = listHtml + "</div>"
            }
        }
        // let listHtml = "<thead><tr><th>课程</th><th>教师</th><th>&nbsp;</th></tr></thead><tbody>";
        // for(let i = 0;i<obj.length;i++){
        //     listHtml = listHtml + "<tr><td class='courseName'>" + obj[i].courseName;
        //     if(isStudent){
        //         listHtml = listHtml + "</td><td class='teacher'>" + obj[i].teacher + "</td>" +
        //             "<td class='button'><button class='pure-button' onclick='location.href=\"course.html?id=" +
        //             obj[i].courseId + "\"'>进入课程</button></td>"+ "</tr>";
        //     }else{
        //         listHtml = listHtml + "</td><td class='teacher'>" + obj[i].teacher + "</td>" +
        //             "<td class='button'><button class='pure-button' onclick='location.href=\"/courseManage/courseManage.html?id=" +
        //             obj[i].courseId + "\"'>进入课程</button></td>"+ "</tr>";
        //     }
        //
        // }
        // listHtml = listHtml + "</tbody>";
        document.getElementById("courseList").innerHTML = listHtml;
    }else{
        document.getElementById("courseList").innerHTML = "<p style='letter-spacing: 0em'>暂无课程</p>";
    }
}

window.onload = function (){
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("get",getServer()+"/session",true);
    xmlHttp.send();
    xmlHttp.onreadystatechange = function (){
        if (this.readyState === 4 && this.status === 200) {
            let res = JSON.parse(xmlHttp.responseText);
            let xmlHttp2 = new XMLHttpRequest();
            xmlHttp2.open("get",getServer()+"/course/list",true);
            xmlHttp2.send();
            if(res.role === "student"){//登录身份为学生则跳转至course页面
                xmlHttp2.onreadystatechange = function (){
                    if (xmlHttp2.readyState === 4 && xmlHttp2.status === 200) {
                        writeList(xmlHttp2.responseText, true);
                    }
                }
            }
            else {//登录身份为教师则跳转至courseManage页面
                xmlHttp2.onreadystatechange = function (){
                    if (xmlHttp2.readyState === 4 && xmlHttp2.status === 200) {
                        writeList(xmlHttp2.responseText, false);
                    }
                }
            }
        }
    }
}
