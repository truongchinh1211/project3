function checkInput() {
  return true;
}

$("#form-change-password").submit(function (e) {
    e.preventDefault();
    let formData = {
      oldPass: $("#old-pass").val(),
      newPass: $("#new-pass").val(),
      confirmNewPass: $("#confirm-new-pass").val()
    };
  
    if (checkInput()) {
      $.ajax({
        url: "http://localhost:8080/api/v1/user/change-pass",
        type: "post",
        contentType: "application/json",
        data: JSON.stringify(formData), // Chuyển đổi object thành chuỗi JSON
        headers: {
          "Authorization": "Bearer eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MTUwNzYzMTQsInN1YiI6ImFAZ21haWwuY29tIn0.olbucHHXnocYO0ACPHxOVTASGe9VWyb_v3AQIocXEJo"
        },
        success: function (data) {
          console.log(data);
        },
        error: function(jqXHR, textStatus, errorThrown) {
          console.log("Request failed:", textStatus, errorThrown);
        }
      });
    }
  });
