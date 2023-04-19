let data;

window.onload = function(){
    //进入页面后生成log记录
    listenMouse();
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("get",getServer()+"/lesson/get" + window.location.search,true);
    xmlHttp.send();
    xmlHttp.onreadystatechange = function (){
        if (this.readyState === 4 && this.status === 200) {
            data = JSON.parse(xmlHttp.responseText);
            document.getElementById("topMenu").innerHTML = getTopMenu(data.course);
            window.document.getElementById("title").innerText = data.lessonName;
            window.document.getElementById("lessonName").innerText = data.lessonName;
            window.document.getElementById("introduction").innerText = data.introduction;
            //为视频元素设置视频链接
            window.document.getElementById("video").src = data.video;
        }
    }
}

window.onbeforeunload = function (){
    //退出页面前发送log记录
    leavePage(data.course);
};