// let date = new Date(2021,11,31);
//
// let data = {
//     assignmentName: "作业1",
//     deadline: date,
//     questions:[
//         {
//             questionId: 1,
//             description: "测试题目1 选择题",
//             type:"choice",
//             choiceA: "选项A",
//             choiceB: "选项B",
//             choiceC: "选项C",
//             choiceD: "选项D"
//         },
//         {
//             questionId: 2,
//             description: "测试题目2 填空题",
//             type:"completion"
//         },
//         {
//             questionId: 3,
//             description: "测试题目3 简答题",
//             type: "shortAnswer"
//         },
//         {
//             questionId: 4,
//             description: "测试题目4 上传题",
//             type: "file"
//         }
//     ]
// }

let data = [];

function showQuestionList(){
    let str = "<fieldset>";
    for(let i = 1;i<data.length;i++){
        str += "<p>" + i + "." + data[i].description + "</p>";
        if(data[i].type === "choice"){
            str += "<label for='" + data[i].questionId + "A" + "' class='pure-radio'>";
            str += "<input id='" + data[i].questionId + "A" + "' type='radio' name='choice" + data[i].questionId + "' value='A'>";
            str += data[i].choiceA + "</label>"
            str += "<label for='" + data[i].questionId + "B" + "' class='pure-radio'>";
            str += "<input id='" + data[i].questionId + "B" + "' type='radio' name='choice" + data[i].questionId + "' value='B'>";
            str += data[i].choiceB + "</label>"
            str += "<label for='" + data[i].questionId + "C" + "' class='pure-radio'>";
            str += "<input id='" + data[i].questionId + "C" + "' type='radio' name='choice" + data[i].questionId + "' value='C'>";
            str += data[i].choiceC + "</label>"
            str += "<label for='" + data[i].questionId + "D" + "' class='pure-radio'>";
            str += "<input id='" + data[i].questionId + "D" + "' type='radio' name='choice" + data[i].questionId + "' value='D'>";
            str += data[i].choiceD + "</label>"
        }
        else if(data[i].type === "completion"){
            str += "<input id='" + data[i].questionId + "' class='pure-input-1' type='text' name='completion" + data[i].questionId + "'>"
        }
        else if(data[i].type === "shortAnswer"){
            str += "<textarea id='" + data[i].questionId + "' name='shortAnswer" + data[i].questionId + "'></textarea>"
        }
        else{
            str += "<input id='" + data[i].questionId + "' type='file' name='file" + data[i].questionId + "'>"
        }
    }
    str += "</filedset>";
    document.getElementById("questionList").innerHTML = str;
}

function submit(){
    let startTime = new Date(data[0].startTime);
    let endTime = new Date(data[0].endTime);
    let now = new Date();
    if(now < startTime || now > endTime){
        window.alert("不在开放时间内，无法作答");
        return false;
    }
    let assignment = window.location.search.split("id=")[1];
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", getServer()+"/assignmentMark?assignment=" + assignment, true);
    xmlHttp.send();
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
            let mark = xmlHttp.responseText;
            //判断此学生此作业是否已有成绩
            if (mark >= 0) {
                window.alert("已完成过此作业，不可重复提交");
                return false;
            } else if (xmlHttp.readyState === 4) {
                for (let i = 1; i < data.length; i++) {
                    //依次获取单选题答案
                    let name = data[i].type + data[i].questionId;
                    if (data[i].type === "choice") {
                        let temp = document.getElementsByName(name);
                        data[i].answer = "";
                        for (let j = 0; j < temp.length; j++) {
                            if (temp[j].checked) {
                                data[i].answer = temp[j].value;
                                break;
                            }
                        }
                    } else {
                        //获取填空题答案
                        data[i].answer = document.getElementsByName(name)[0].value;
                    }
                }

                let xmlHttp2 = new XMLHttpRequest();
                xmlHttp2.open("PUT", getServer() + "/assignmentMark", true);
                xmlHttp2.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xmlHttp2.send("data=" + JSON.stringify(data));
                // window.alert(data);
                xmlHttp2.onreadystatechange = function () {
                    if (xmlHttp2.readyState === 4 && xmlHttp2.status === 200) {
                        moveToList();
                    }
                }
            }
        }
    }
}

function moveToList(){
    window.location.href = "assignmentList.html?id=" + data[0].course;
}

window.onload = function (){
    listenMouse();

    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", getServer()+"/assignment/get" + window.location.search,true);
    xmlHttp.send();
    xmlHttp.onreadystatechange = function (){
        if (this.readyState === 4 && this.status === 200) {
            let assignmentData = JSON.parse(xmlHttp.responseText);
            data.push(assignmentData);
            document.getElementById("title").innerText = assignmentData.assignmentName;
            document.getElementById("assignmentName").innerText = assignmentData.assignmentName;
            let startTime = new Date(assignmentData.startTime);
            let endTime = new Date(assignmentData.endTime);
            document.getElementById("startTime").innerText = "发布时间：" + startTime.toLocaleString();
            document.getElementById("endTime").innerText = "截止时间：" + endTime.toLocaleString();
        }
    }

    let xmlHttpChoice = new XMLHttpRequest();
    xmlHttpChoice.open("GET", getServer()+"/choiceQuestion" + window.location.search,true);
    xmlHttpChoice.send();
    xmlHttpChoice.onreadystatechange = function (){
        if (xmlHttpChoice.readyState === 4 && xmlHttpChoice.status === 200) {
            let questionData = JSON.parse(xmlHttpChoice.responseText);
            for(let i = 0;i<questionData.length;i++){
                questionData[i].type = "choice";
                data.push(questionData[i]);
            }
            showQuestionList();
            let xmlHttpCompletion = new XMLHttpRequest();
            xmlHttpCompletion.open("GET", getServer()+"/completionQuestion" + window.location.search,true);
            xmlHttpCompletion.send();
            xmlHttpCompletion.onreadystatechange = function (){
                if (xmlHttpCompletion.readyState === 4 && xmlHttpCompletion.status === 200) {
                    let questionData = JSON.parse(xmlHttpCompletion.responseText);
                    for(let i = 0;i<questionData.length;i++){
                        questionData[i].type = "completion";
                        data.push(questionData[i]);
                    }
                    showQuestionList();
                }
            }
        }
    }
}

window.onbeforeunload = function (){
    leavePage(data[0].course);
};


