let data;

function addAssignment(){
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("PUT", getServer()+"/assignment"+window.location.search,true);
    xmlHttp.send();
    xmlHttp.onreadystatechange = function (){
        if (this.readyState === 4 && this.status === 200) {
            location.reload();
        }
    }
}

function deleteCheck(assignmentId){
    let check = confirm("确定要删除本次作业吗？");
    if(check === true){
        let xmlHttp = new XMLHttpRequest();
        xmlHttp.open("DELETE", getServer()+"/assignment",true);
        xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlHttp.send("assignmentId=" + assignmentId);
        xmlHttp.onreadystatechange = function (){
            if (this.readyState === 4 && this.status === 200) {
                location.reload();
            }
        }
    }
    else{}
}

function writeAssignmentList(data){
    let listHtml= "";
    for(let i = 0;i<data.length;i++){
        let startTime = new Date(data[i].startTime);
        let endTime = new Date(data[i].endTime);
        listHtml = listHtml + "<div class='pure-u-1-3' style='border: 1px solid #DCDCDC;box-sizing: border-box'>";
        listHtml = listHtml + "<p class='assignmentTitle'>" + data[i].assignmentName + "<p>";
        listHtml = listHtml + "<p class='assignmentInformation'>开始时间：" + startTime.toLocaleDateString() + startTime.toLocaleTimeString() + "</p>";
        listHtml = listHtml + "<p class='assignmentInformation'>截止时间：" + endTime.toLocaleDateString() + endTime.toLocaleTimeString() + "</p>";
        listHtml = listHtml + "<button class='pure-button pure-button-primary setSpread' onclick='location.href=\"assignmentManage.html?id=" + data[i].assignmentId + "\"'>进入</button>";
        listHtml = listHtml + "<button class='redButton pure-button setSpread' onclick='deleteCheck(" + data[i].assignmentId + ");return false;'>删除</button>";
        listHtml = listHtml + "</div>"
    }
    document.getElementById("assignmentList").innerHTML = listHtml;

    // let str = "<tr><th>任务</th><th>截止时间</th><th>操作</th></tr>";
    // for(let i = 0;i<data.length;i++){
    //     let time = new Date(data[i].endTime);
    //     str += "<tr><td class='assignmentTitle'><a class='assignmentLink' href='assignmentManage.html?id=" + data[i].assignmentId + "'>" + data[i].assignmentName + "</a></td>";
    //     str += "<td class='assignmentInformation'>" + time.toLocaleDateString() + time.toLocaleTimeString() + "</td>";
    //     str += "<td class='assignmentInformation'>"+
    //         "<button class='pure-button' onclick='deleteCheck(" + data[i].assignmentId + ")'>删除</button></td></tr>"
    // }
    // document.getElementById("assignmentList").innerHTML = str;
}

window.onload = function() {
    let xmlHttp = new XMLHttpRequest();
    let courseId = window.location.search.split("id=");
    courseId = courseId[1];
    document.getElementById("topMenu").innerHTML = getTopMenuForManage(courseId);
    xmlHttp.open("GET", getServer()+"/assignment/list" + window.location.search,true);
    xmlHttp.send();
    xmlHttp.onreadystatechange = function (){
        if (this.readyState === 4 && this.status === 200) {
            data = JSON.parse(xmlHttp.responseText);
            writeAssignmentList(data);
        }
    }
}