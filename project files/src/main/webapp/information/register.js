function register(){
    if(document.getElementById("password").value !== document.getElementById("rePassword").value){
        document.getElementById("message").innerText = "两次输入的密码不一致，请确认密码";
        return false;
    }
    else if(document.getElementById("password").value === '' || document.getElementById("username").value === ''){
        document.getElementById("message").innerText = "请输入用户信息以进行注册";
        return false;
    }
    else{
        let xmlHttp = new XMLHttpRequest();
        if(document.getElementById("role").value === "学生"){
            xmlHttp.open("PUT", getServer()+"/register/student", true);
        }
        else{
            xmlHttp.open("PUT", getServer()+"/register/teacher", true);
        }
        xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlHttp.send("username=" + document.getElementById("username").value + "&password=" + document.getElementById("password").value);
        xmlHttp.onreadystatechange = function (){
            if (this.readyState === 4 && this.status === 200) {
                let res = JSON.parse(xmlHttp.responseText);
                if (res.success === true) {
                    window.location.href = "../index.html";
                    window.alert(res.text);
                    return true;
                } else {
                    document.getElementById("message").innerText = res.text;
                    return false;
                }
            }
        }
    }
}