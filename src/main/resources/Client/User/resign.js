function generateRandomMaTV() {
  return Math.floor(Math.random() * 90000000) + 10000000;
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

    const maTV = generateRandomMaTV();
    console.log(maTV);

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
        alert("Registration successful.");
        window.location.href = "./login.html";
      })
      .catch((error) => {
        console.error("Error:", error);
        alert("Fail, Please Try Again.");
      });
  });
});
