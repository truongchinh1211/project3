function postData(url, data, callback) {
  var xhr = new XMLHttpRequest();
  xhr.open("POST", url, true);
  xhr.setRequestHeader("Content-Type", "application/json");
  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4 && xhr.status === 200) {
      var response = JSON.parse(xhr.responseText);
      callback(response);
    } else if (xhr.readyState === 4 && xhr.status === 400) {
      alert("Sai email hoặc mật khẩu");
    }
  };
  var jsonData = JSON.stringify(data);
  xhr.send(jsonData);
}

document.addEventListener("DOMContentLoaded", function () {
  document.addEventListener("submit", function (event) {
    event.preventDefault();
    var apiUrl = "http://localhost:8080/api/v1/user/login";
    var dataToSend = {
      email: document.getElementById("username").value,
      password: document.getElementById("password").value,
    };
    postData(apiUrl, dataToSend, function (response) {
      console.log("Object:", response);
      var token = response.token;
      localStorage.setItem("token", token);
      console.log("Token in local storage:", localStorage.getItem("token"));
      console.log("Catch token:", token);
      window.location.href = "../homepage/datcho.html";
    });
  });
});
