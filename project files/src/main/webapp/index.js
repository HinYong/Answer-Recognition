function login(){
    if(document.getElementById("username").value.length <= 0){
        document.getElementById("message").innerText = "请填写用户名";
        return false;
    }
    if(document.getElementById("password").value.length <= 0){
        document.getElementById("message").innerText = "请填写密码";
        return false;
    }

    let xmlHttp = new XMLHttpRequest();
    if(document.getElementById("role").value === "学生"){
        xmlHttp.open("POST", getServer()+"/index/student", true);
    }
    else{
        xmlHttp.open("POST", getServer()+"/index/teacher", true);
    }
    xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlHttp.send("username=" + document.getElementById("username").value + "&password=" + document.getElementById("password").value);
    xmlHttp.onreadystatechange = function (){
        if (this.readyState === 4 && this.status === 200) {
            let res = JSON.parse(xmlHttp.responseText);
            if (res.success === true) {
                window.location.href = "./course/courseList.html";
                window.alert(res.text);
                return true;
            } else {
                document.getElementById("message").innerText = res.text;
                return false;
            }
        }
    }
}

function moveToRegister(){
    window.location.href = "information/register.html";
}