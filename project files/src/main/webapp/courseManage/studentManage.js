// let data = {
//     students:[
//         {
//             realName: "张三",
//             username: "testName1",
//             school: "大连理工大学",
//             studentNumber: "0123456789",
//             department: "软件学院",
//             class: "1701班",
//             mark: 80
//         },
//         {
//             realName: "李四",
//             username: "testName2",
//             school: "大连理工大学",
//             studentNumber: "0123456788",
//             department: "软件学院",
//             class: "1701班",
//             mark: 80
//         },
//         {
//             realName: "王五",
//             username: "testName3",
//             school: "大连理工大学",
//             studentNumber: "0123456787",
//             department: "软件学院",
//             class: "1702班",
//             mark: 90
//         }
//     ]
// }

let data;

function writeList(data){
    let str = "";
    for(let i = 0;i<data[0].length;i++){
        str += "<tr><td>" + data[0][i].realname + "</td>";
        str += "<td>" + data[0][i].username + "</td>";
        str += "<td>" + data[0][i].school + "</td>";
        str += "<td>" + data[0][i].studentNumber + "</td>";
        str += "<td>" + data[0][i].department + "</td>";
        str += "<td>" + data[0][i].schoolClass + "</td>";
        str += "<td>" + data[1][i] + "</td>";
        str += "<td><a href='logAnalysis.html" + window.location.search + "&u=" + data[0][i].username + "'>操作记录</a></td></tr>";
    }
    document.getElementById("studentList").innerHTML = str;
}

window.onload = function (){
    let courseId = window.location.search.split("id=");
    courseId = courseId[1];
    document.getElementById("topMenu").innerHTML = getTopMenuForManage(courseId);
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", getServer()+"/studentManage" + window.location.search, true);
    xmlHttp.send();
    xmlHttp.onreadystatechange = function (){
        if (this.readyState === 4 && this.status === 200) {
            data = JSON.parse(xmlHttp.responseText);
            console.log(data);
            writeList(data);
        }
    }
}