const BASE_URL = "http://localhost:8080/register"

function registerUser(){
    document.getElementById("registerForm").addEventListener("submit",async function(event){
            console.log("entered");
            event.preventDefault();
            const firstname = document.getElementById("firstname").value;
            const lastname = document.getElementById("lastname").value;
            const userEmail = document.getElementById("userEmail").value;
            const password = document.getElementById("password").value;
            const username = document.getElementById("username").value;
            const repassword = document.getElementById("repassword").value;
            let logMessage = document.getElementById("registerMessage");
            
            const response = await fetch(BASE_URL,{
                method:"POST",
                headers:{"Content-Type":"application/json"},
                body:JSON.stringify({username:username,password:password,email:userEmail,firstName:firstname,lastName:lastname})
            })

            if(response.ok){
                logMessage.style.color="green"
                logMessage.textContent="Registered Successfully..Redirecting to login page .."
                setTimeout(() => {
                    window.location.href = "login.html";
                }, 2000);
                
                
            }
            else{
                const errorData = await response.json();
                let errorMessage = "Unable to register<br>";
                if(errorData.status == "error" && errorData.errors){
                    logMessage.style.color = "red";
                    for (const field in errorData.errors) {
                        if (Array.isArray(errorData.errors[field])) {
                            errorData.errors[field].forEach(errorMessageText => {
                                errorMessage += `<br>* ${errorMessageText}`;  // Add each error on a new line
                            });
                        } else {
                            // If it's not an array (just one error), display it as a single message
                            errorMessage += `<br>${field}: ${errorData.errors[field]}`;
                        }
                    }
                    logMessage.innerHTML = errorMessage;
                }
               
               
            } 

    }
)

}
registerUser();