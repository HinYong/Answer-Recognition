// let date = new Date(2021,11,31);
//
// let data={
//     courseName: "软件工程",
//     assignments: [
//         {
//             assignmentName: "作业1",
//             assignmentId: 1,
//             deadline: date,
//             mark: 80
//         },
//         {
//             assignmentName: "作业2",
//             assignmentId: 2,
//             deadline: date,
//             mark: 90
//         },
//         {
//             assignmentName: "考试",
//             assignmentId: 3,
//             deadline: date,
//             mark: null
//         }
//     ]
// }

let data = [];

function writeAssignmentList(data) {
    let listHtml= "";
    for(let i = 0;i<data.length;i++){
        let startTime = new Date(data[i].startTime);
        let endTime = new Date(data[i].endTime);
        listHtml = listHtml + "<div class='pure-u-1-3' style='border: 1px solid #DCDCDC;box-sizing: border-box' onclick='location.href=\"assignment.html?id=" + data[i].assignmentId + "\"'>";
        listHtml = listHtml + "<p class='assignmentTitle'>" + data[i].assignmentName + "<p>";
        listHtml = listHtml + "<p class='assignmentInformation'>开始时间：" + startTime.toLocaleDateString() + startTime.toLocaleTimeString() + "</p>";
        listHtml = listHtml + "<p class='assignmentInformation'>截止时间：" + endTime.toLocaleDateString() + endTime.toLocaleTimeString() + "</p>";
        if(data[i].mark >= 0){
            listHtml = listHtml + "<p class='assignmentInformation'>得分：" + data[i].mark + "</p>";
        }
        else {
            listHtml = listHtml + "<p class='assignmentInformation'>暂无成绩</p>";
        }
        listHtml = listHtml + "</div>"
    }

    document.getElementById("assignmentList").innerHTML = listHtml;
    // let str = "<thead><tr><th>任务</th><th>截止时间</th><th>分数</th></tr></thead><tbody>";
    // for(let i = 0;i<data.length;i++){
    //     let time = new Date(data[i].endTime);
    //     str += "<tr><td class='assignmentTitle'><a class='assignmentLink' href='assignment.html?id=" + data[i].assignmentId + "'>" + data[i].assignmentName + "</a></td>";
    //     str += "<td class='assignmentInformation'>" + time.toLocaleDateString() + time.toLocaleTimeString() + "</td>";
    //     if(data[i].mark){
    //         str += "<td class='assignmentInformation'>" + data[i].mark + "</td></tr>"
    //     }
    //     else {
    //         str += "<td class='assignmentInformation'>暂无成绩</td></tr>"
    //     }
    // }
    // str = str + "</tbody>";
    // document.getElementById("assignmentList").innerHTML = str;
}

window.onload = function() {
    let xmlHttp = new XMLHttpRequest();
    let courseId = window.location.search.split("id=");
    courseId = courseId[1];
    document.getElementById("topMenu").innerHTML = getTopMenu(courseId);
    xmlHttp.open("GET", getServer()+"/assignment/list" + window.location.search,true);
    xmlHttp.send();
    xmlHttp.onreadystatechange = function (){
        if (this.readyState === 4 && this.status === 200) {
            data = JSON.parse(xmlHttp.responseText);
            let xmlHttps = [];
            for(let i = 0;i<data.length;i++){
                xmlHttps[i] = new XMLHttpRequest();
                xmlHttps[i].open("GET", getServer()+"/assignmentMark?assignment=" + data[i].assignmentId,true);
                xmlHttps[i].send();
                xmlHttps[i].onreadystatechange = function (){
                    if (xmlHttps[i].readyState === 4 && xmlHttps[i].status === 200) {
                        let mark = JSON.parse(xmlHttps[i].responseText);
                        if(mark >= 0){
                            data[i].mark = mark;
                        }
                        writeAssignmentList(data);
                    }
                }
            }
        }
    }
}

// function getDate(para){
//     let str = "";
//     //str += para.getHours() + ":" + para.getMinutes() + " ";
//     str += para.getHours()<10 ? "0"+para.getHours() : para.getHours();
//     str += ":";
//     str += para.getMinutes()<10 ? "0"+para.getMinutes() : para.getMinutes();
//     str += " " + para.getFullYear() + "/";
//     str += para.getMonth()<9 ? "0"+(para.getMonth()+1) : para.getMonth()+1;
//     str += "/";
//     str += para.getDate()<10 ? "0"+para.getDate() : para.getDate();
//     return str;
// }