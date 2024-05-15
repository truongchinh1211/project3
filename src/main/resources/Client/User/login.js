document.addEventListener("submit", function (event) {
  event.preventDefault();

  const username = document.getElementById("username").value;
  const password = document.getElementById("password").value;

  fetch("http://localhost:8080/api/v1/user/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ email: username, password: password }),
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.success) {
        window.location.href = "home.html";
      } else {
        alert("Tài khoản hoặc mật khẩu bạn sai");
        document.getElementById("password").value = "";
      }
    })
    .catch((error) => {
      console.error("Error:", error);
      alert("Có lỗi xảy ra, vui lòng thử lại sau.");
    });
});
