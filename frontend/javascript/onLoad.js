
function getGreeting() {
    let now = new Date();
    let hour = now.getHours(); // Get current hour (0-23 format)
    let greeting;

    if (hour >= 5 && hour < 12) {
        greeting = "Good Morning! ☀️.";
    } else if (hour >= 12 && hour < 17) {
        greeting = "Good Afternoon! 🌞";
    } else {
        greeting = "Good Evening! 🌙";
    }

    return greeting;
}

// Display the greeting when the page loads
window.onload = function () {
    document.getElementById("greeting-message").innerText = getGreeting();
};





