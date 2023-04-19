let studentData;
let studentIndex = 0;
let excelData = "";

let data = {
    questionNumber:["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十"],
    questionMark:[0],
    totalMark:0
};

function showStudentData(){
    let str;
    if(studentIndex < studentData[0].length){
        str = "当前学生为：" + studentData[0][studentIndex].realname + "(" + studentData[0][studentIndex].studentNumber + ")，请上传对应图片";
    } else {
        str = "本课程学生已完成识别";
    }
    window.document.getElementById("studentInfo").innerText = str;
}

function nextStudent(){
    studentIndex++;
    showStudentData(studentIndex);
}

function showMarks(){
    let str = "<fieldset>";
    for(let i = 0;i<data.questionMark.length;i++){
        str += "<div class='pure-control-group'><label for='" + i + "questionNumber" + "'>题号：</label>";
        str += "<input id='" + i + "questionNumber" + "' type='text' value='" + data.questionNumber[i] + "' onchange='updateMark()'>";
        str += "<label for='" + i + "questionMark" + "'>分数：</label>";
        str += "<input id='" + i + "questionMark" + "' type='number' value='" + data.questionMark[i] + "' onchange='updateMark()'>"
    }
    str += "<div class='pure-controls'>总分：" + data.totalMark + "</div></fieldset>";
    window.document.getElementById("questionList").innerHTML = str;
}

function addQuestion(){
    // data.questionNumber.push("");
    data.questionMark.push(0);
    let sum = Number(0);
    for(let i = 0;i<data.questionMark.length;i++){
        sum += Number(data.questionMark[i]);
    }
    data.totalMark = sum;
    showMarks();
}

function deleteQuestion(){
    // data.questionNumber.pop();
    data.questionMark.pop();
    let sum = Number(0);
    for(let i = 0;i<data.questionMark.length;i++){
        sum += Number(data.questionMark[i]);
    }
    data.totalMark = sum;
    console.log(data);
    showMarks();
}

function updateMark(){
    for(let i = 0;i<data.questionMark.length;i++){
        data.questionNumber[i] = document.getElementById(i + "questionNumber").value;
        data.questionMark[i] = document.getElementById(i + "questionMark").value;
    }
    let sum = Number(0);
    for(let i = 0;i<data.questionMark.length;i++){
        sum += Number(data.questionMark[i]);
    }
    data.totalMark = sum;
    showMarks();
}

function uploadImage(){
    if(data.questionMark.length !== data.questionNumber.length){
        for(let i = 0;i<data.questionMark.length;i++){
            data.questionMark[i] = document.getElementById(i + "questionMark").value;
        }

        for(let i = data.questionNumber.length;i>data.questionMark.length;i--){
            data.questionNumber.pop();
        }
        data.questionNumber.push("总分");
        data.questionMark.push(data.totalMark);
    }

    let xmlHttp = new XMLHttpRequest();
    let formData = new FormData();
    formData.append("questionNumber", data.questionNumber);
    formData.append("questionMark", data.questionMark);
    formData.append("image", document.getElementById("image").files[0]);
    console.log(getServer());
    xmlHttp.open("POST", getServer()+"/wordRecognition", true);
    xmlHttp.send(formData);
    document.getElementById("uploadStatus").innerText = "上传识别中";
    xmlHttp.onreadystatechange = function (){
        if (this.readyState === 4 && this.status === 200) {
            let result = JSON.parse(xmlHttp.responseText);
            document.getElementById("uploadStatus").innerText = "识别结果：" + result;
            downLoadExcel(result);
        }
    }
    studentIndex++;
}

function showImage(){
    if(window.FileReader){
        let reader = new FileReader();
        reader.onload = function (){
            let image = document.getElementById("uploadedImage");
            image.hidden = false;
            image.src = this.result;
        }
        reader.readAsDataURL(document.getElementById('image').files[0]);
    }
}

function downLoadExcel(result){
    if(excelData.length === 0){
        excelData = ",姓名,学校,学号,院系,班级,线上学习成绩," + data.questionNumber + "\n";
        excelData += "标准分,,,,,,," + data.questionMark + "\n";
    }
    excelData += "得分情况," + studentData[0][studentIndex].realname + "," +
        studentData[0][studentIndex].school + "," +
        studentData[0][studentIndex].studentNumber + "," +
        studentData[0][studentIndex].department + "," +
        studentData[0][studentIndex].schoolClass + "," +
        studentData[1][studentIndex] + "," +
        result + "\n";
    showStudentData();

    let uri = 'data:text/csv;charset=utf-8,\ufeff' + encodeURIComponent(excelData);
    let link = document.getElementById("downLoadLink");
    link.href = uri;
    link.hidden = false;
    link.download = document.getElementById("image").files[0].name + '.csv';
}

window.onload = function (){
    let courseId = window.location.search.split("id=");
    courseId = courseId[1];
    document.getElementById("topMenu").innerHTML = getTopMenuForManage(courseId);
    showMarks();

    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", getServer()+"/studentManage" + window.location.search, true);
    xmlHttp.send();
    xmlHttp.onreadystatechange = function (){
        if (this.readyState === 4 && this.status === 200) {
            studentData = JSON.parse(xmlHttp.responseText);
            showStudentData(studentIndex);
        }
    }
}
