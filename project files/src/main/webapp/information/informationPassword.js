function logout(){
    window.location.href = "../index.html";
}

function update(){
    if(document.getElementById("password").value.length <= 0){
        document.getElementById("message").innerText = "请输入密码";
        return false;
    }
    if(document.getElementById("newPassword").value.length <= 0){
        document.getElementById("message").innerText = "请填写新密码";
        return false;
    }
    if(document.getElementById("newPassword").value === document.getElementById("rePassword").value){
        let xmlHttp = new XMLHttpRequest();
        xmlHttp.open("POST", getServer()+"/information/password", true);
        xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlHttp.send("oldPassword=" + document.getElementById("password").value +
            "&newPassword=" + document.getElementById("newPassword").value);
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
    else{
        document.getElementById("message").innerText = "两次输入密码不一致，请重新输入";
    }
}