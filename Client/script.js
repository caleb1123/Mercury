// script.js
document.addEventListener('DOMContentLoaded', (event) => {
    var popup = document.getElementById("popup");
    var openPopupBtn = document.getElementById("openPopup");
    var closePopupSpan = document.getElementsByClassName("close")[0];

    // Khi người dùng nhấn vào nút, mở popup
    openPopupBtn.onclick = function() {
        popup.style.display = "block";
    }

    // Khi người dùng nhấn vào (x), đóng popup
    closePopupSpan.onclick = function() {
        popup.style.display = "none";
    }

    // Khi người dùng nhấn bất kỳ đâu ngoài popup, đóng popup
    window.onclick = function(event) {
        if (event.target == popup) {
            popup.style.display = "none";
        }
    }
});
