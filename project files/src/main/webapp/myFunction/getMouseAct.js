let log = {
    path: window.location.pathname + window.location.search,//当前进行操作的路径
    date: new Date().toDateString(),//当前进行操作的日期
    acts:[]//保存具体操作内容的数组
};

function listenMouse(){
	//监听html标签内的鼠标操作
    window.document.getElementsByTagName("html")[0].addEventListener("mousedown",function (e){
        log.acts.push({
            X: e.clientX,
            Y: e.clientY,
            button: e.button,
            Time: new Date().toTimeString()//当前操作进行的时间
        });
    })
}

function leavePage(courseId){
    //向后端发送获取到的操作记录
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", getServer()+"/log", true);
    xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlHttp.send("course=" + courseId + "&log=" + JSON.stringify(log));
}