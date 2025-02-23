const BASE_URL = "http://localhost:8080/login"

function loginValidation(){
    document.getElementById("loginform").addEventListener("submit",async function (event) {
        event.preventDefault();

        const uname = document.getElementById("username").value;
        const pword = document.getElementById("password").value;

        const response = await fetch(BASE_URL,{
            method:"POST",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify({username:uname,password:pword})
        });

        let logMessage = document.getElementById("loginMessage");
        if(response.ok){
            window.location.href = "home.html"; 
        }else{
            logMessage.style.color = "red";
            logMessage.textContent = "Invalid email or password!";
        }  
    })
}

loginValidation();