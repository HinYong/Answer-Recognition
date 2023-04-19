let data;
let dates = [];
let lessonStudents = [];
let assignmentStudents = [];
let lessonData = [];
let lessonDataStudents = [];
let assignmentData = [];
let assignmentDataStudents = [];

function setChart(){
    let myChart = echarts.init(document.getElementById('chart'));

    // 指定图表的配置项和数据
    let option = {
        title: {
            text: '学生操作记录分析'
        },
        tooltip: {},
        legend: {
            data:['学习人数','作业人数']
        },
        xAxis: {
            data: dates
        },
        yAxis: {},
        series: [{
            name: '学习人数',
            type: 'bar',
            data: lessonStudents
        },{
            name: '作业人数',
            type: 'bar',
            data: assignmentStudents
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
            data:['学习人数']
        },
        xAxis: {
            data: lessonNames
        },
        yAxis: {},
        series: [{
            name: '学习人数',
            type: 'bar',
            data: lessonDataStudents
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
            data:['作业人数']
        },
        xAxis: {
            data: assignmentNames
        },
        yAxis: {},
        series: [{
            name: '作业人数',
            type: 'bar',
            data: assignmentDataStudents
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}

function readLogByDate(logData){
    let lessonSet = new Set();
    let assignmentSet = new Set();

    //设置查看七天内的学习情况
    console.log(logData);
    for(let i = 0;i<logData.length;i++){
        let log = JSON.parse(logData[i]);
        if(log.acts.length<2){
            continue;
        }

        //读取log中的日期
        let logDataDate = new Date(log.date).toLocaleDateString();
        let index = dates.indexOf(logDataDate);
        //若日期在七天内则计数
        if(index >= 0){
            //若此人当天的学习或作业已经过计数则不再重复计数，否则加入set中
            if(log.path.indexOf("/course/lesson.html") >= 0){
                if(lessonSet.has(logDataDate)){
                    continue;
                } else {
                    lessonSet.add(logDataDate);
                }
            } else {
                if(assignmentSet.has(logDataDate)){
                    continue;
                } else {
                    assignmentSet.add(logDataDate);
                }
            }

            if(log.path.indexOf("/course/lesson.html") >= 0){
                lessonStudents[index] += 1;
            } else {
                assignmentStudents[index] += 1;
            }
        }
    }
}

function readLogByLesson(logData){
    let lessonSet = new Set();
    for(let i = 0;i<logData.length;i++){
        let log = JSON.parse(logData[i]);
        if(log.acts.length<2){
            continue;
        }

        if(log.path.indexOf("/course/lesson.html") >= 0){
            //读取log第一次操作和最后一次操作的时间差，并转化单位为分钟
            let lessonId = log.path.split("id=")[1];

            if(lessonSet.has(lessonId)){
                continue;
            } else {
                lessonSet.add(lessonId);
            }

            for(let i = 0;i<lessonData.length;i++){
                if(lessonData[i].lessonId == lessonId){
                    lessonDataStudents[i] += 1;
                }
            }
        }
    }
}

function readLogByAssignment(logData){
    let assignmentSet = new Set();
    for(let i = 0;i<logData.length;i++){
        let log = JSON.parse(logData[i]);
        if(log.acts.length<2){
            continue;
        }
        if(log.path.indexOf("/course/assignment.html") >= 0){
            //读取log第一次操作和最后一次操作的时间差，并转化单位为分钟
            let assignmentId = log.path.split("id=")[1];

            if(assignmentSet.has(assignmentId)){
                continue;
            } else {
                assignmentSet.add(assignmentId);
            }

            for(let i = 0;i<assignmentData.length;i++){
                if(assignmentData[i].assignmentId == assignmentId){
                    assignmentDataStudents[i] += 1;
                }
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
    xmlHttp.open("GET", getServer() + "/log" + window.location.search + "&u=all");
    xmlHttp.send();
    xmlHttp.onreadystatechange = function (){
        if (this.readyState === 4 && this.status === 200) {
            data = JSON.parse(xmlHttp.responseText);
            if(data == null){
                document.getElementById("main").innerText = "暂无学生进行过学习";
            } else {
                for(let i = 6;i>=0;i--){
                    let time = new Date().getTime() - i*24*60*60*1000;
                    let date = new Date(time);
                    dates.push(date.toLocaleDateString());
                    lessonStudents.push(0);
                    assignmentStudents.push(0);
                }
                for(let i = 0;i<data.length;i++){
                    //读取单条log中的数据
                    readLogByDate(data[i]);
                }
                setChart();

                let xmlHttp2 = new XMLHttpRequest();
                xmlHttp2.open("get", getServer()+"/lesson/list"+window.location.search, true);
                xmlHttp2.send();
                xmlHttp2.onreadystatechange = function (){
                    if(xmlHttp2.readyState === 4 && xmlHttp2.status === 200){
                        let tempData = JSON.parse(xmlHttp2.responseText);
                        for(let i = 1;i<tempData.length;i++){
                            lessonData.push(tempData[i]);
                            lessonDataStudents.push(0);
                        }
                        for(let i = 0;i<data.length;i++){
                            //读取单条log中的数据
                            readLogByLesson(data[i]);
                        }
                    }
                    setLessonChart();
                }
                let xmlHttp3 = new XMLHttpRequest();
                xmlHttp3.open("GET", getServer()+"/assignment/list" + window.location.search,true);
                xmlHttp3.send();
                xmlHttp3.onreadystatechange = function (){
                    if (xmlHttp3.readyState === 4 && xmlHttp3.status === 200) {
                        assignmentData = JSON.parse(xmlHttp3.responseText);
                        for(let i = 0;i<assignmentData.length;i++){
                            assignmentDataStudents.push(0);
                        }
                        for(let i = 0;i<data.length;i++){
                            //读取单条log中的数据
                            readLogByAssignment(data[i]);
                        }
                    }
                    setAssignmentChart();
                }
                for(let i = 1;i<data.length;i++){
                    //读取单条log中的数据
                    // let logData = JSON.parse(data[i]);
                }
            }
        }
    }
}