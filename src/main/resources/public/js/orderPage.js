function toggleShed(select) {
    const shedFields = document.getElementById('shedFields');
    const shedWidth = document.querySelector('select[name="shedWidth"]');
    const shedLength = document.querySelector('select[name="shedLength"]');


    if (select.value === 'true') {
        shedFields.style.display = 'block'
        shedWidth.required = true
        shedLength.required = true
    } else {
        shedFields.style.display = 'none'
        shedWidth.required = false
        shedLength.required = false
    }
}

function togglePasswordField() {

    const passwordContainer =
        document.getElementById("passwordContainer");

    const passwordInput =
        document.getElementById("password");

    if (passwordContainer.style.display === "none") {

        passwordContainer.style.display = "block";

        passwordInput.required = true;

    } else {

        passwordContainer.style.display = "none";

        passwordInput.required = false;

        passwordInput.value = "";

    }
}