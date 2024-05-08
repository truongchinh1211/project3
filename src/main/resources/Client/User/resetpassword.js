document.addEventListener("DOMContentLoaded", function () {
  //const resetPasswordForm = document.getElementById("resetPasswordForm");

  document.addEventListener("submit", function (event) {
    event.preventDefault();
    const urlParams = window.location.href;
    let url = new URL(urlParams);
    console.log(urlParams);
    var search = url.searchParams.get("email");
    console.log(search);

    const newPassword = document.getElementById("newPassword").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    // Kiểm tra xem new password và confirm password có trùng nhau không
    if (newPassword !== confirmPassword) {
      alert("New password and confirm password don't match.");
      return;
    }

    fetch(`http://localhost:8080/api/v1/user/reset-password/${search}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        newPass: newPassword,
        confirm: confirmPassword,
      }),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Failed to reset password");
        }
        return response.json();
      })
      .then((data) => {
        console.log(data);
        alert("Password reset successfully");
        window.location.href = "./login.html";
      })
      .catch((error) => {
        console.error("Error:", error);
        alert("Failed to reset password");
      });
  });
});


