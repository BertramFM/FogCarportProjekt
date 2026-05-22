function showForm(type){


    document.getElementById("loginForm").classList.remove("active");
    document.getElementById("registerForm").classList.remove("active");
    document.getElementById("employeeForm").classList.remove("active");


    document.getElementById("loginTab").classList.remove("active");
    document.getElementById("registerTab").classList.remove("active");
    document.getElementById("employeeTab").classList.remove("active");


    if(type === "login"){
        document.getElementById("loginForm")
            .classList.add("active");

        document.getElementById("loginTab")
            .classList.add("active");
    }


    if(type === "register"){
        document.getElementById("registerForm")
            .classList.add("active");

        document.getElementById("registerTab")
            .classList.add("active");
    }


    if(type === "employee"){
        document.getElementById("employeeForm")
            .classList.add("active");

        document.getElementById("employeeTab")
            .classList.add("active");
    }
}