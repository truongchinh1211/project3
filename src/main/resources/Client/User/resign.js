function generateRandomMaTV() {
  return Math.floor(Math.random() * 9000000000) + 1000000000;
}
document.addEventListener("DOMContentLoaded", function () {
  document.addEventListener("submit", function (event) {
    event.preventDefault();

    const hoten = document.getElementById("fullName").value;
    const khoa = document.getElementById("faculty").value;
    const nganh = document.getElementById("major").value;
    const sdt = document.getElementById("phoneNumber").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    // Tạo mã TV ngẫu nhiên
    const maTV = generateRandomMaTV();
    console.log(maTV);

    // Gửi yêu cầu đăng ký đến backend
    fetch("http://localhost:8080/api/v1/user/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        maTV: maTV,
        hoTen: hoten,
        khoa: khoa,
        nganh: nganh,
        sdt: sdt,
        password: password,
        email: email,
      }),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Failed to resign");
        }
        return response.json();
      })
      .then((data) => {
        if (data.success) {
          alert("Registration successful.");
          window.location.href = "./login.html";
        } else {
          alert("Registration failed. " + data.message);
        }
      })
      .catch((error) => {
        console.error("Error:", error);
        alert("Fail, Please Try Again.");
      });
  });
});
