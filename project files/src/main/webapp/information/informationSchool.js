function logout(){
    window.location.href = "../index.html";
}

function update(){
    let xmlHttp = new XMLHttpRequest();
    if(document.getElementById("class").hidden){
        xmlHttp.open("POST", getServer()+"/information/teacherInformation", true);
        xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlHttp.send("school=" + document.getElementById("school").value +
            "&teacherNumber=" + document.getElementById("schoolNumber").value +
            "&department=" + document.getElementById("department").value);
    } else {
        xmlHttp.open("POST", getServer()+"/information/schoolInformation", true);
        xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlHttp.send("school=" + document.getElementById("school").value +
            "&studentNumber=" + document.getElementById("schoolNumber").value +
            "&department=" + document.getElementById("department").value +
            "&schoolClass=" + document.getElementById("class").value);
    }
    xmlHttp.onreadystatechange = function (){
        if (this.readyState === 4 && this.status === 200) {
            let data = JSON.parse(xmlHttp.responseText);
            if (data.success === true) {
                window.alert(data.text);
                location.reload();
            } else {
                window.alert(data.text);
                window.location.href = "../index.html";
            }
        }
    }
}

window.onload = function (){
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", getServer()+"/information", true);
    xmlHttp.send();
    xmlHttp.onreadystatechange = function (){
        if (this.readyState === 4 && this.status === 200) {
            let data = JSON.parse(xmlHttp.responseText);
            console.log(data);
            if (data.username != null && "studentNumber" in data) {
                document.getElementById("school").value = data.school;
                document.getElementById("schoolNumber").value = data.studentNumber;
                document.getElementById("department").value = data.department;
                document.getElementById("class").value = data.schoolClass;
            } else if(data.username != null && "teacherNumber" in data) {
                document.getElementById("school").value = data.school;
                document.getElementById("schoolNumberLaber").innerText = "教工号";
                document.getElementById("schoolNumber").value = data.teacherNumber;
                document.getElementById("department").value = data.department;
                document.getElementById("classLaber").hidden = true;
                document.getElementById("class").hidden = true;
            } else {
                window.alert("请重新登录");
                window.location.href = "../index.html";
            }
        }
    }
}