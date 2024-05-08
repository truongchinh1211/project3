document.addEventListener("DOMContentLoaded", function () {
  document.addEventListener("submit", function (event) {
    event.preventDefault(); // Prevent default form submission

    var email = document.getElementById("email").value;

    // Check if email is valid
    var emailRegex = /^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]{2,7}$/;
    if (emailRegex.test(email)) {
      // Send email to backend
      fetch("http://localhost:8080/api/v1/user/forget-password", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ email: email }),
      })
        .then((response) => response.json())
        .then((data) => {
          alert("Please check your email for further instructions.");
          window.location.href = "./login.html";
        })
        .catch((error) => {
          console.error("Error:", error);
          alert("Email is not valid");
        });
    } else {
      alert("Please enter a valid email address.");
    }
  });
});
