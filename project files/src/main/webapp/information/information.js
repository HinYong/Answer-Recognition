function logout(){
    window.location.href = "../index.html";
}

function update(){
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", getServer()+"/information/information", true);
    xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlHttp.send("realname=" + document.getElementById("realname").value +
        "&phone=" + document.getElementById("phone").value +
        "&email=" + document.getElementById("email").value);
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
            if (data.username != null) {
                document.getElementById("username").value = data.username;
                document.getElementById("realname").value = data.realname;
                document.getElementById("phone").value = data.phone;
                document.getElementById("email").value = data.email;
            } else {
                window.alert("请重新登录");
                window.location.href = "../index.html";
            }
        }
    }
}