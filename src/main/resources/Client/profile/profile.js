
 var token = `Bearer ${localStorage.getItem("token")}`;
//var token = `Bearer eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MTU2Nzg2MjIsInN1YiI6ImFAZ21haWwuY29tIn0.GYCuROwNPcGiA8O1lEVy6P9mkZJgzscK2ok6c2J3JUw`;

function liveToast(message, isSuccess) {
  if (isSuccess) {
    $("#liveToast").removeClass("bg-danger");
    $("#liveToast").addClass("bg-success");
  } else {
    $("#liveToast").removeClass("bg-success");
    $("#liveToast").addClass("bg-danger");
  }

  $(".toast-header .me-auto").text(isSuccess ? "Thành công" : "Thất bại");
  $(".toast-body").text(message);
  $("#liveToast").removeClass("hide").addClass("show");
}

function checkInput(oldPass, newPass, confirmNewPass) {
  if (oldPass == "") {
    liveToast("Chưa nhập mật khẩu hiện tại!", false);
    return false;
  }
  if (newPass == "") {
    liveToast("Chưa nhập mật khẩu mới!", false);
    return false;
  }
  if (confirmNewPass == "") {
    liveToast("Chưa nhập lại mật khẩu mới!", false);
    return false;
  }
  if (confirmNewPass != newPass) {
    liveToast("Mật khẩu mới không khớp!", false);
    return false;
  }
  if (
    oldPass.includes(" ") ||
    newPass.includes(" ") ||
    confirmNewPass.includes(" ")
  ) {
    liveToast("Mật khẩu không được chứa khoảng trắng!", false);
    return false;
  }
  return true;
}

$("#form-change-password").submit(function (e) {
  e.preventDefault();
  let oldPass = $("#old-pass").val();
  let newPass = $("#new-pass").val();
  let confirmNewPass = $("#confirm-new-pass").val();

  let formData = {
    oldPass: oldPass,
    newPass: newPass,
    confirmNewPass: confirmNewPass,
  };

  if (checkInput(oldPass, newPass, confirmNewPass)) {
    $.ajax({
      url: "http://localhost:8080/api/v1/user/change-pass",
      type: "post",
      contentType: "application/json",
      data: JSON.stringify(formData), // Chuyển đổi object thành chuỗi JSON
      headers: {
        Authorization: token,
      },
      success: function (data) {
        liveToast(data.message, true);
        console.log(data);
      },
      error: function (data) {
        console.log(data);
        if (data.responseText == undefined) {
          liveToast("Có lỗi xảy ra!!!", false);
        } else {
          let jsonObject = JSON.parse(data.responseText);
          liveToast(jsonObject.error, false);
        }
      },
    });
  }
});

function handleDataViPham(data) {
  let response = data;
  let strHTML = "";

  response.list.forEach(function (item) {
    let soTien = item.soTien ? item.soTien : "Không có";
    let trangThai =
      item.trangThaiXL == "1"
        ? `<td class="text-success">Đã xử lý</td>`
        : `<td class="text-danger">Chưa xử lý</td>`;
    strHTML += `<tr>
                  <td>${item.maXL}</td>
                  <td>${item.hinhThucXL}</td>
                  <td>${soTien}</td>
                  <td>${item.ngayXL}</td>
                  ${trangThai}
                </tr>`;
  });
  document.getElementById("xuLyContainer").innerHTML = strHTML;
}

function loadTrangThaiViPham() {
  $.ajax({
    url: "http://localhost:8080/api/v1/xuly/get-by-matv",
    type: "get",
    contentType: "application/json",
    headers: {
      Authorization: token,
    },
    success: function (data) {
      handleDataViPham(data);
    },
    error: function (data) {
      console.log(data);
    },
  });
}

function handleDataDatCho(data) {
  let response = data;
  let strHTML = "";

  response.list.forEach(function (item) {
    let thietBi = item.thietBi;
    let maTB = thietBi.maTB;
    let tenTB = thietBi.tenTB;

    let tgmuon = item.tgmuon ? item.tgmuon.replace("T", " ") : "";
    let tgtra = item.tgtra ? item.tgtra.replace("T", " ") : "";
    let tgdatCho = item.tgdatCho.replace("T", " ");

    let trangThai = "";

    if (tgmuon == "" && tgtra == "") {
      trangThai += `<td class="text-danger">Đã đặt chỗ</td>`;
    } else if (tgmuon != "" && tgtra == "") {
      trangThai += `<td class="text-warning">Đang mượn</td>`;
    } else if (tgmuon != "" && tgtra != "") {
      trangThai += `<td class="text-success">Đã trả</td>`;
    }

    strHTML += `<tr>
                  <td>${item.maTT}</td>
                  <td>${maTB}</td>
                  <td>${tenTB}</td>
                  <td>${tgdatCho}</td>
                  <td>${tgmuon}</td>
                  <td>${tgtra}</td>
                  ${trangThai}
                </tr>`;
  });
  document.getElementById("datChoContainer").innerHTML = strHTML;
}

function loadDatCho() {
  $.ajax({
    url: "http://localhost:8080/api/v1/thongtinsd/get-by-matv",
    type: "get",
    contentType: "application/json",
    headers: {
      Authorization: token,
    },
    success: function (data) {
      handleDataDatCho(data);
    },
    error: function (data) {
      console.log(data);
    },
  });
}

loadTrangThaiViPham();
loadDatCho();
