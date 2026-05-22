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