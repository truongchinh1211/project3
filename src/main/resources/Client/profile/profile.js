var token =
  "Bearer eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MTUxNDc5NDMsInN1YiI6ImFAZ21haWwuY29tIn0.wZa_-ZNcXawSbSaXwUnJQvi_Ey8H1J7eW0vg5VIompI";

function checkInput(oldPass, newPass, confirmNewPass) {
  if (oldPass == "") {
    alert("Chưa nhập mật khẩu hiện tại!");
    return false;
  }
  if (newPass == "") {
    alert("Chưa nhập mật khẩu mới!");
    return false;
  }
  if (confirmNewPass == "") {
    alert("Chưa nhập lại mật khẩu mới!");
    return false;
  }
  if (confirmNewPass != newPass) {
    alert("Mật khẩu mới không khớp!");
    return false;
  }
  if (
    oldPass.includes(" ") ||
    newPass.includes(" ") ||
    confirmNewPass.includes(" ")
  ) {
    alert("Mật khẩu không được chứa khoảng trắng!");
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
        alert(data.message);
        console.log(data);
      },
      error: function (data) {
        alert(data.responseText);
        console.log(data.responseText);
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

    let tgvao = item.tgvao ? item.tgvao.replace("T"," ") : "";
    let tgmuon = item.tgmuon ? item.tgmuon.replace("T"," ") : "";
    let tgtra = item.tgtra ? item.tgtra.replace("T"," ") : "";
    let tgdatCho = item.tgdatCho.replace("T"," ");

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
                  <td>${tgvao}</td>
                  <td>${tgmuon}</td>
                  <td>${tgtra}</td>
                  <td>${tgdatCho}</td>
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
