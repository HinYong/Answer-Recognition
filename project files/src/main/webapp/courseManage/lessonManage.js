function update(){
    let xmlHttp = new XMLHttpRequest();
    let formData = new FormData();
    formData.append("lessonName", document.getElementById("lessonName").value);
    let lessonId = window.location.search;
    lessonId = lessonId.split("id=")[1];
    formData.append("lessonId", lessonId);
    formData.append("introduction", document.getElementById("introduction").value);
    formData.append("video", document.getElementById("video").files[0]);
    xmlHttp.open("POST", getServer()+"/lesson", true);
    xmlHttp.send(formData);
    document.getElementById("message").innerText = "上传视频中";
    xmlHttp.onreadystatechange = function (){
        if (this.readyState === 4 && this.status === 200) {
            let res = JSON.parse(xmlHttp.responseText);
            if (res.success === true) {
                window.alert(res.text);
                location.reload();
            } else {
                document.getElementById("message").innerText = res.text;
            }
        }
    }
}

window.onload = function (){
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("get",getServer()+"/lesson/get" + window.location.search,true);
    xmlHttp.send();
    xmlHttp.onreadystatechange = function (){
        if (this.readyState === 4 && this.status === 200) {
            let data = JSON.parse(xmlHttp.responseText);
            document.getElementById("topMenu").innerHTML = getTopMenuForManage(data.course);
            window.document.getElementById("title").innerText = data.lessonName;
            window.document.getElementById("lessonName").value = data.lessonName;
            window.document.getElementById("introduction").innerText = data.introduction;
            window.document.getElementById("oldVideo").src = data.video;
        }
    }
}