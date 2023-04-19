let data = [];

function showQuestionList(){
    let str = "<fieldset>";
    for(let i = 1;i<data.length;i++){
        str += "<p>" + i + "." + "</p>";
        str += "<div class='pure-control-group'><label for='" + i + "description'>题目描述：</label>"
        str += "<input id='" + i + "description' type='text' value='" + data[i].description + "'></div>"
        if(data[i].type === "choice"){
            //显示四个选项输入
            str += "<div class='pure-control-group'><label for='" + i + "A" + "'>选项A:</label>";
            str += "<input id='" + i + "A" + "' type='text' value='" + data[i].choiceA + "'></div>";
            str += "<div class='pure-control-group'><label for='" + i + "B" + "'>选项B:</label>";
            str += "<input id='" + i + "B" + "' type='text' value='" + data[i].choiceB + "'></div>";
            str += "<div class='pure-control-group'><label for='" + i + "C" + "'>选项C:</label>";
            str += "<input id='" + i + "C" + "' type='text' value='" + data[i].choiceC + "'></div>";
            str += "<div class='pure-control-group'><label for='" + i + "D" + "'>选项D:</label>";
            str += "<input id='" + i + "D" + "' type='text' value='" + data[i].choiceD + "'></div>";
            //显示答案和分数输入
            str += "<div class='pure-control-group'><label for='" + i + "answer" + "'>答案：</label>";
            if(data[i].answer === "A"){
                str += "<select id='" + i + "answer" + "'><option>A</option> <option>B</option> <option>C</option> <option>D</option></select></div>";
            } else if(data[i].answer === "B") {
                str += "<select id='" + i + "answer" + "'><option>A</option> <option selected=\"selected\">B</option> <option>C</option> <option>D</option></select></div>";
            } else if(data[i].answer === "C") {
                str += "<select id='" + i + "answer" + "'><option>A</option> <option>B</option> <option selected=\"selected\">C</option> <option>D</option></select></div>";
            } else {
                str += "<select id='" + i + "answer" + "'><option>A</option> <option>B</option> <option>C</option> <option selected=\"selected\">D</option></select></div>";
            }

            str += "<div class='pure-control-group'><label for='" + i + "mark" + "'>分数：</label>";
            str += "<input id='" + i + "mark" + "' type='number' value='" + data[i].mark + "'></div>";
            //显示删除按钮
            str += "<div class='pure-controls'><button type='button' class='pure-button redButton' onclick='deleteCheck(" + i + ");return false'>删除本题</button></div>";
        }
        if(data[i].type === "completion"){
            //显示答案和分数输入
            str += "<div class='pure-control-group'><label for='" + i + "answer" + "'>答案：</label>";
            str += "<input id='" + i + "answer" + "' value='" + data[i].answer + "'></div>";
            str += "<div class='pure-control-group'><label for='" + i + "mark" + "'>分数：</label>";
            str += "<input id='" + i + "mark" + "' type='number' value='" + data[i].mark + "'></div>";
            //显示删除按钮
            str += "<div class='pure-controls' ><button type='button' class='pure-button redButton' onclick='deleteCheck(" + i + ");return false'>删除本题</button></div>";
        }
        if(data[i].type === "shortAnswer"){
            //显示分数输入
            str += "<div class='pure-control-group'><label for='" + i + "mark" + "'>分数：</label>";
            str += "<input id='" + i + "mark" + "' type='number' value='" + data[i].mark + "'></div>";
            //显示删除按钮
            str += "<div class='pure-controls' ><button type='button' class='pure-button redButton' onclick='deleteCheck(" + i + ");return false'>删除本题</button></div>";
        }
        if(data[i].type === "file"){
            //显示分数输入
            str += "<div class='pure-control-group'><label for='" + i + "mark" + "'>分数：</label>";
            str += "<input id='" + i + "mark" + "' type='number' value='" + data[i].mark + "'></div>";
            //显示删除按钮
            str += "<div class='pure-controls' ><button type='button' class='pure-button redButton' onclick='deleteCheck(" + i + ");return false'>删除本题</button></div>";
        }
    }
    str += "</filedset>";
    document.getElementById("questionList").innerHTML = str;
}

function deleteCheck(questionNumber){
    let check = confirm("确定要删除本题吗？");
    if(check === true){
        if(data[questionNumber].questionId === 0){
            data.splice(questionNumber,1);
            showQuestionList();
            return true;
        }
        else{
            let xmlHttp = new XMLHttpRequest();
            if(data[questionNumber].type === "choice"){
                xmlHttp.open("DELETE", getServer()+"/choiceQuestion",true);
                xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xmlHttp.send("questionId=" + data[questionNumber].questionId);
                xmlHttp.onreadystatechange = function () {
                    if (this.readyState === 4 && this.status === 200) {
                        data.splice(questionNumber, 1);
                        showQuestionList();
                    }
                }
            } else if(data[questionNumber].type === "completion"){
                xmlHttp.open("DELETE", getServer()+"/completionQuestion",true);
                xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xmlHttp.send("questionId=" + data[questionNumber].questionId);
                xmlHttp.onreadystatechange = function () {
                    if (this.readyState === 4 && this.status === 200) {
                        data.splice(questionNumber, 1);
                        showQuestionList();
                    }
                }
            } else if(data[questionNumber].type === "shortAnswer"){
                xmlHttp.open("DELETE", getServer()+"/shortAnswerQuestion",true);
                xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xmlHttp.send("questionId=" + data[questionNumber].questionId);
                xmlHttp.onreadystatechange = function () {
                    if (this.readyState === 4 && this.status === 200) {
                        data.splice(questionNumber, 1);
                        showQuestionList();
                    }
                }
            } else if(data[questionNumber].type === "file"){
                xmlHttp.open("DELETE", getServer()+"/fileQuestion",true);
                xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xmlHttp.send("questionId=" + data[questionNumber].questionId);
                xmlHttp.onreadystatechange = function () {
                    if (this.readyState === 4 && this.status === 200) {
                        data.splice(questionNumber, 1);
                        showQuestionList();
                    }
                }
            }
        }
    }
}

function newChoiceQuestion(){
    data.push({
        questionId: 0,
        description: "选择题",
        type:"choice",
        choiceA: "选项A",
        choiceB: "选项B",
        choiceC: "选项C",
        choiceD: "选项D",
        answer: "A",
        mark: 1
    });
    showQuestionList();
}

function newCompletionQuestion(){
    data.push({
        questionId: 0,
        description: "填空题",
        type:"completion",
        answer: "",
        mark: 1
    })
    showQuestionList();
}

function newShortAnswerQuestion(){
    data.push({
        questionId: 0,
        description: "简答题",
        type: "shortAnswer",
        mark: 1
    })
    showQuestionList();
}

function newFileQuestion(){
    data.push({
        questionId: 0,
        description: "上传题",
        type: "file",
        mark: 1
    })
    showQuestionList();
}

function update(){
    let xmlHttp = [];
    xmlHttp[0] = new XMLHttpRequest();
    xmlHttp[0].open("POST", getServer()+"/assignment",true);
    xmlHttp[0].setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlHttp[0].send("assignmentId=" + data[0].assignmentId +
        "&assignmentName=" + document.getElementById("assignmentName").value +
        "&weight=" + document.getElementById("weight").value +
        "&startTime=" + document.getElementById("startTime").value +
        "&endTime=" + document.getElementById("endTime").value);
    xmlHttp[0].onreadystatechange = function (){
        if (this.readyState === 4 && this.status === 200) {
            let res = JSON.parse(xmlHttp[0].responseText);
            if(res.success){
            } else {
                alert(res.text);
                return false;
            }
        }
    }
    for(let i = 1;i<data.length;i++){
        //questionId为0时，创建新的question，否则对question信息进行更新
        if(data[i].questionId === 0){
            xmlHttp[i] = new XMLHttpRequest();
            if(data[i].type === "choice") {
                xmlHttp[i].open("PUT", getServer() + "/choiceQuestion", true);
                xmlHttp[i].setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xmlHttp[i].send("assignment=" + data[0].assignmentId +
                    "&description=" + document.getElementById(i + "description").value +
                    "&choiceA=" + document.getElementById(i + "A").value +
                    "&choiceB=" + document.getElementById(i + "B").value +
                    "&choiceC=" + document.getElementById(i + "C").value +
                    "&choiceD=" + document.getElementById(i + "D").value +
                    "&answer=" + document.getElementById(i + "answer").value +
                    "&mark=" + document.getElementById(i + "mark").value);
            } else if(data[i].type === "completion"){
                xmlHttp[i].open("PUT", getServer() + "/completionQuestion", true);
                xmlHttp[i].setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xmlHttp[i].send("assignment=" + data[0].assignmentId +
                    "&description=" + document.getElementById(i + "description").value +
                    "&answer=" + document.getElementById(i + "answer").value +
                    "&mark=" + document.getElementById(i + "mark").value);
            } else if(data[i].type === "shortAnswer"){
                xmlHttp[i].open("PUT", getServer() + "/shortAnswerQuestion", true);
                xmlHttp[i].setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xmlHttp[i].send("assignment=" + data[0].assignmentId +
                    "&description=" + document.getElementById(i + "description").value +
                    "&mark=" + document.getElementById(i + "mark").value);
            } else if(data[i].type === "file"){
                xmlHttp[i].open("PUT", getServer() + "/fileQuestion", true);
                xmlHttp[i].setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xmlHttp[i].send("assignment=" + data[0].assignmentId +
                    "&description=" + document.getElementById(i + "description").value +
                    "&mark=" + document.getElementById(i + "mark").value);
            }
        } else {
            xmlHttp[i] = new XMLHttpRequest();
            if(data[i].type === "choice") {
                xmlHttp[i].open("POST", getServer() + "/choiceQuestion", true);
                xmlHttp[i].setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xmlHttp[i].send("questionId=" + data[i].questionId +
                    "&description=" + document.getElementById(i + "description").value +
                    "&choiceA=" + document.getElementById(i + "A").value +
                    "&choiceB=" + document.getElementById(i + "B").value +
                    "&choiceC=" + document.getElementById(i + "C").value +
                    "&choiceD=" + document.getElementById(i + "D").value +
                    "&answer=" + document.getElementById(i + "answer").value +
                    "&mark=" + document.getElementById(i + "mark").value);
            } else if(data[i].type === "completion"){
                xmlHttp[i].open("POST", getServer() + "/completionQuestion", true);
                xmlHttp[i].setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xmlHttp[i].send("questionId=" + data[i].questionId +
                    "&description=" + document.getElementById(i + "description").value +
                    "&answer=" + document.getElementById(i + "answer").value +
                    "&mark=" + document.getElementById(i + "mark").value);
            } else if(data[i].type === "shortAnswer"){
                xmlHttp[i].open("POST", getServer() + "/shortAnswerQuestion", true);
                xmlHttp[i].setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xmlHttp[i].send("questionId=" + data[i].questionId +
                    "&description=" + document.getElementById(i + "description").value +
                    "&mark=" + document.getElementById(i + "mark").value);
            } else if(data[i].type === "file"){
                xmlHttp[i].open("POST", getServer() + "/fileQuestion", true);
                xmlHttp[i].setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xmlHttp[i].send("questionId=" + data[i].questionId +
                    "&description=" + document.getElementById(i + "description").value +
                    "&mark=" + document.getElementById(i + "mark").value);
            }
        }
    }
    location.reload();
}

function moveToList(){
    window.location.href = "assignmentListManage.html?id=" + data[0].course;
}

window.onload = function (){
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", getServer()+"/assignment/get" + window.location.search,true);
    xmlHttp.send();
    xmlHttp.onreadystatechange = function (){
        if (this.readyState === 4 && this.status === 200) {
            let assignmentData = JSON.parse(xmlHttp.responseText);
            data.push(assignmentData);
            document.getElementById("title").innerText = assignmentData.assignmentName;
            document.getElementById("assignmentName").value = assignmentData.assignmentName;
            document.getElementById("weight").value = assignmentData.weight;
            document.getElementById("startTime").value = formatTime(assignmentData.startTime);
            document.getElementById("endTime").value = formatTime(assignmentData.endTime);
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
                    let xmlHttpShortAnswer = new XMLHttpRequest();
                    xmlHttpShortAnswer.open("GET", getServer()+"/shortAnswerQuestion" + window.location.search,true);
                    xmlHttpShortAnswer.send();
                    xmlHttpShortAnswer.onreadystatechange = function (){
                        if (xmlHttpShortAnswer.readyState === 4 && xmlHttpShortAnswer.status === 200) {
                            let questionData = JSON.parse(xmlHttpShortAnswer.responseText);
                            for(let i = 0;i<questionData.length;i++){
                                questionData[i].type = "shortAnswer";
                                data.push(questionData[i]);
                            }
                            showQuestionList();
                            let xmlHttpFile = new XMLHttpRequest();
                            xmlHttpFile.open("GET", getServer()+"/fileQuestion" + window.location.search,true);
                            xmlHttpFile.send();
                            xmlHttpFile.onreadystatechange = function (){
                                if (xmlHttpFile.readyState === 4 && xmlHttpFile.status === 200) {
                                    let questionData = JSON.parse(xmlHttpFile.responseText);
                                    for(let i = 0;i<questionData.length;i++){
                                        questionData[i].type = "file";
                                        data.push(questionData[i]);
                                    }
                                    showQuestionList();
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

function formatTime(string){
    let time = string.split(":");
    return time[0] + ":" + time[1];
}