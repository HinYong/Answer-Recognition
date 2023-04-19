let data;
let dates = [];
let lessonTime = [];
let assignmentTime = [];
let lessonData = [];
let lessonDataTime = [];
let assignmentData = [];
let assignmentDataTime = [];

function setChart(){
    let myChart = echarts.init(document.getElementById('chart'));

    // 指定图表的配置项和数据
    let option = {
        title: {
            text: '学生操作记录分析'
        },
        tooltip: {},
        legend: {
            data:['学习时间（分钟）','作业时间（分钟）']
        },
        xAxis: {
            data: dates
        },
        yAxis: {},
        series: [{
            name: '学习时间（分钟）',
            type: 'bar',
            data: lessonTime
        },{
            name: '作业时间（分钟）',
            type: 'bar',
            data: assignmentTime
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}

function setLessonChart(){
    let myChart = echarts.init(document.getElementById('lessonChart'));
    let lessonNames = [];
    for(let i = 0;i<lessonData.length;i++){
        lessonNames[i] = lessonData[i].lessonName;
    }

    // 指定图表的配置项和数据
    let option = {
        title: {
            text: '学生学习记录分析'
        },
        tooltip: {},
        legend: {
            data:['学习时间（分钟）']
        },
        xAxis: {
            data: lessonNames
        },
        yAxis: {},
        series: [{
            name: '学习时间（分钟）',
            type: 'bar',
            data: lessonDataTime
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}

function setAssignmentChart(){
    let myChart = echarts.init(document.getElementById('assignmentChart'));
    let assignmentNames = [];
    for(let i = 0;i<assignmentData.length;i++){
        assignmentNames[i] = assignmentData[i].assignmentName;
    }

    // 指定图表的配置项和数据
    let option = {
        title: {
            text: '学生作业记录分析'
        },
        tooltip: {},
        legend: {
            data:['作业时间（分钟）']
        },
        xAxis: {
            data: assignmentNames
        },
        yAxis: {},
        series: [{
            name: '作业时间（分钟）',
            type: 'bar',
            data: assignmentDataTime
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}

function readLogByDate(logData){
    if(logData.acts.length<2){
        return null;
    }

    //读取log中的日期
    let logDataDate = new Date(logData.date);

    let logDataLessonTime;
    let logDataAssignmentTime;
    if(logData.path.indexOf("/course/lesson.html") >= 0){
        //读取log第一次操作和最后一次操作的时间差，并转化单位为分钟
        logDataLessonTime = new Date(logData.acts[logData.acts.length - 1].Time) - new Date(logData.acts[0].Time);
        logDataLessonTime = logDataLessonTime/1000/60;
        logDataAssignmentTime = 0;
    } else {
        //读取log第一次操作和最后一次操作的时间差，并转化单位为分钟
        logDataAssignmentTime = new Date(logData.acts[logData.acts.length - 1].Time) - new Date(logData.acts[0].Time);
        logDataAssignmentTime = logDataAssignmentTime/1000/60;
        logDataLessonTime = 0;
    }

    //若dates中已有此日期则后续数据加入此日期中，否则将此日期插入dates
    let index = dates.indexOf(logDataDate.toLocaleDateString());
    if(index >= 0){
        lessonTime[index] += logDataLessonTime;
        assignmentTime[index] += logDataAssignmentTime;
    } else {
        dates.push(logDataDate.toLocaleDateString());
        lessonTime.push(logDataLessonTime);
        assignmentTime.push(logDataAssignmentTime);
    }
}

function readLogByLesson(logData){
    if(logData.acts.length<2){
        return null;
    }

    let logDataLessonTime;
    if(logData.path.indexOf("/course/lesson.html") >= 0){
        //读取log第一次操作和最后一次操作的时间差，并转化单位为分钟
        logDataLessonTime = new Date(logData.acts[logData.acts.length - 1].Time) - new Date(logData.acts[0].Time);
        logDataLessonTime = logDataLessonTime/1000/60; let lessonId = logData.path.split("id=")[1];
        for(let i = 0;i<lessonData.length;i++){
            if(lessonData[i].lessonId == lessonId){
                lessonDataTime[i] += logDataLessonTime;
            }
        }
    }
}

function readLogByAssignment(logData){
    if(logData.acts.length<2){
        return null;
    }

    let logDataAssignmentTime;
    if(logData.path.indexOf("/course/assignment.html") >= 0){
        //读取log第一次操作和最后一次操作的时间差，并转化单位为分钟
        logDataAssignmentTime = new Date(logData.acts[logData.acts.length - 1].Time) - new Date(logData.acts[0].Time);
        logDataAssignmentTime = logDataAssignmentTime/1000/60;
        let assignmentId = logData.path.split("id=")[1];
        for(let i = 0;i<assignmentData.length;i++){
            if(assignmentData[i].assignmentId == assignmentId){
                assignmentDataTime[i] += logDataAssignmentTime;
            }
        }
    }
}

window.onload = function (){
    let courseId = window.location.search.split("id=");
    courseId = courseId[1].split("&");
    courseId = courseId[0];
    document.getElementById("topMenu").innerHTML = getTopMenuForManage(courseId);
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", getServer() + "/log" + window.location.search);
    xmlHttp.send();
    xmlHttp.onreadystatechange = function (){
        if (this.readyState === 4 && this.status === 200) {
            data = JSON.parse(xmlHttp.responseText);
            console.log(data);
            if(data[0] == null){
                document.getElementById("main").innerText = "此学生暂未进行过学习";
            } else {
                document.getElementById("logUrl").href = "../log/" + data[0];
                document.getElementById("logUrl").download = data[0];
                let xmlHttp2 = new XMLHttpRequest();
                xmlHttp2.open("get", getServer()+"/lesson/list"+window.location.search, true);
                xmlHttp2.send();
                xmlHttp2.onreadystatechange = function (){
                    if(xmlHttp2.readyState === 4 && xmlHttp2.status === 200){
                        let tempData = JSON.parse(xmlHttp2.responseText);
                        for(let i = 1;i<tempData.length;i++){
                            lessonData.push(tempData[i]);
                            lessonDataTime.push(0);
                        }
                        for(let i = 1;i<data.length;i++){
                            //读取单条log中的数据
                            let logData = JSON.parse(data[i]);
                            readLogByLesson(logData);
                        }
                        setLessonChart();
                    }
                }
                let xmlHttp3 = new XMLHttpRequest();
                xmlHttp3.open("GET", getServer()+"/assignment/list" + window.location.search,true);
                xmlHttp3.send();
                xmlHttp3.onreadystatechange = function (){
                    if (xmlHttp3.readyState === 4 && xmlHttp3.status === 200) {
                        assignmentData = JSON.parse(xmlHttp3.responseText);
                        for(let i = 0;i<assignmentData.length;i++){
                            assignmentDataTime.push(0);
                        }
                        for(let i = 1;i<data.length;i++){
                            //读取单条log中的数据
                            let logData = JSON.parse(data[i]);
                            readLogByAssignment(logData);
                        }
                        setAssignmentChart();
                    }
                }
                for(let i = 1;i<data.length;i++){
                    //读取单条log中的数据
                    let logData = JSON.parse(data[i]);
                    readLogByDate(logData);
                }
                setChart();
            }
        }
    }
}