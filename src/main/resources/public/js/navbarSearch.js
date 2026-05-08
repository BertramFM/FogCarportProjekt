document.addEventListener("DOMContentLoaded", function () {
    const input = document.querySelector(".navbar-search-input");
    const clearButton = document.querySelector(".navbar-search-delete-button");

    input.addEventListener("input", function () {
        if (input.value.length > 0) {
            clearButton.style.display = "grid";
        } else {
            clearButton.style.display = "none";
        }
    });

    clearButton.addEventListener("click", function () {
        input.value = "";
        clearButton.style.display = "none";
        input.focus();
    });
});