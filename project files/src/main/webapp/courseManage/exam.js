let data;

window.onload = function() {
    let xmlHttp = new XMLHttpRequest();
    let courseId = window.location.search.split("id=");
    courseId = courseId[1];
    document.getElementById("topMenu").innerHTML = getTopMenuForManage(courseId);
}

function changefile(x){
    var change = document.getElementById("file");
    change.innerText = x;
}

function uploadfile() {
    if(document.getElementById("answer1").value==""){
        alert("请输入选择题答案！");
    }else if(document.getElementById("answer2").value==""){
        alert("请输入判断题答案！");
    }else if(document.getElementById("addfile").value==""){
        alert("请选择压缩文件！");
    }else{
        refresh()
        document.getElementById("uploadStatus").innerText =  "上传中...";
        setTimeout(function (){
            let xmlHttp = new XMLHttpRequest();
            let formData = new FormData();
            formData.append("zipfile", document.getElementById("addfile").files[0]);
            formData.append("answer1", document.getElementById("answer1").value);
            formData.append("answer2", document.getElementById("answer2").value);
            xmlHttp.open("POST", getServer()+"/answersRecognition"+window.location.search, true);
            xmlHttp.send(formData);
            xmlHttp.onreadystatechange = function (){
                if (this.readyState === 4 && this.status === 200) {
                    document.getElementById("uploadStatus").innerText =  "上传成功";
                    downloadfile();
                }
            }},0);

    }
}

function downloadfile(){
    let uri = "../downloadfile/score.csv";
    let link = document.getElementById("downLoadLink");
    link.href = uri;
    link.hidden = false;
    link.download = "score.csv";
}
function refresh(){
	document.getElementById("downLoadLink").hidden=true;
    document.getElementById("uploadStatus").innerText ="";
}