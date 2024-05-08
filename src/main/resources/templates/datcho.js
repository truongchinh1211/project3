var listTb;
const myHeaders = new Headers();
// var headerAuthorization = myHeaders.get("Authorization") ?? "Bearer eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MTUxOTg4ODUsInN1YiI6ImFAZ21haWwuY29tIn0.Y108RtqQaSw2naFwcnXyhxnoKx7w3lpO6hQj-WlLT-I";
var headerAuthorization =  "Bearer eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MTUyMDAxNjMsInN1YiI6ImFAZ21haWwuY29tIn0.Wm4-0PO01P11Q3CeaMBtQ1Qoo8jjCQ_QImknajeXlH0";

function loadListThietBi() {
    return new Promise(function (myResolve, myReject) {
        $.ajax({
            url: "http://localhost:8080/api/v1/thietbi/list-by-current-user",
            type: "get",

            headers: {
                "Authorization": headerAuthorization
            },
            success: function (result) {
                listTb = result.listTb;

                var html = ``;
                listTb.forEach(function (tb, index) {
                    let injecthtml = ``;
                    let subhtml = ``;
                    if (tb.isMuon == true) {
                        if (tb.isCurrentThanhVien == true) {
                            injecthtml = `<td class="text-primary">Bạn Đang Mượn</td>
                            <td >
                              <div class="btn-group btn-group-justified">
                                <button  type="button" class="btn btn-primary traThietBiBtn" value=${index}>Trả Thiết Bị</button >
                              </div>
                            </td>`
                        }
                        else {
                            injecthtml = `<td class="text-primary">Đã Được Mượn</td>
                            <td >
                              <div class="btn-group btn-group-justified">
                                <button  type="button" class="btn invisible">Đặt Chỗ</button >
                              </div>
                            </td>`
                        }
                    }
                    else {
                        if (tb.isDatCho == true) {
                            if (tb.isCurrentThanhVien == true) {
                                injecthtml = `<td class="text-danger">Bạn Đang Đặt Chỗ</td>
                                <td >
                                  <div class="btn-group btn-group-justified">
                                    <button  type="button" class="btn btn-danger huyDatChoBtn " value=${index}>Hủy đặt chỗ</button >
                                  </div>
                                </td>`
                            }
                            else {
                                injecthtml = `<td class="text-danger ">Đã Được Đặt Chỗ</td>
                                <td >
                                  <div class="btn-group btn-group-justified">
                                    <button  type="button" class="btn  invisible">Đặt Chỗ</button >
                                  </div>
                                </td>`
                            }
                        }
                        else {
                            injecthtml = `<td class="text-success">Rảnh</td>
                                <td >
                                  <div class="btn-group btn-group-justified">
                                    <button  type="button" class="btn btn-success datChoBtn" value=${index}>Đặt Chỗ</button >
                                  </div>
                                </td>`
                        }
                    }

                    subhtml = `<tr>
                    <th scope="row">${tb.maTB}</th>
                    <td>${tb.tenTB}</td>
                    <td>${tb.moTaTB}</td>
                    ${injecthtml}
                  </tr>`
                    html += subhtml;

                })
                $(".table-body").html(html);

                myResolve();

            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log("Request failed:", textStatus, errorThrown);
            }
        });
    });

}

function loadBtn() {
    $(".datChoBtn").click(function () {
        $(this).attr("data-original-text", $(this).html());
        $(this).prop("disabled", true);
        $(this).html('<i class="spinner-border spinner-border-sm"></i> Loading...');


        let index = $(this).attr("value")
        let formData = {
            maTB: listTb[index].maTB,

        };

        setTimeout(function () {

            $.ajax({
                url: "http://localhost:8080/api/v1/thongtinsd/datcho",
                type: "post",
                contentType: "application/json",
                data: JSON.stringify(formData), // Chuyển đổi object thành chuỗi JSON
                headers: {
                    "Authorization": headerAuthorization
                },
                success: function (data) {
                    console.log(data);
                    loadListThietBi().then(function() {
                        loadBtn();
                    });
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    console.log("Request failed:", textStatus, errorThrown);
                }
            });
        }, 1000)
    })



    // huy dat cho


    $(".huyDatChoBtn").click (function () { 
        $(this).attr("data-original-text", $(this).html());
        $(this).prop("disabled", true);
        $(this).html('<i class="spinner-border spinner-border-sm"></i> Loading...');


        let index = $(this).attr("value")
        let formData = {
            maTB: listTb[index].maTB,

        };

        setTimeout(function () {

            $.ajax({
                url: "http://localhost:8080/api/v1/thongtinsd/huydatcho",
                type: "post",
                contentType: "application/json",
                data: JSON.stringify(formData), // Chuyển đổi object thành chuỗi JSON
                headers: {
                    "Authorization": headerAuthorization
                },
                success: function (data) {
                    console.log(data);
                    loadListThietBi().then(function() {
                        loadBtn();
                    });
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    console.log("Request failed:", textStatus, errorThrown);
                }
            });
        }, 1000)
    })


    $(".traThietBiBtn").click (function () { 
        $(this).attr("data-original-text", $(this).html());
        $(this).prop("disabled", true);
        $(this).html('<i class="spinner-border spinner-border-sm"></i> Loading...');


        let index = $(this).attr("value")
        let formData = {
            maTB: listTb[index].maTB,

        };

        setTimeout(function () {

            $.ajax({
                url: "http://localhost:8080/api/v1/thongtinsd/trathietbi",
                type: "post",
                contentType: "application/json",
                data: JSON.stringify(formData), // Chuyển đổi object thành chuỗi JSON
                headers: {
                    "Authorization": headerAuthorization
                },
                success: function (data) {
                    console.log(data);
                    loadListThietBi().then(function() {
                        loadBtn();
                    });
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    console.log("Request failed:", textStatus, errorThrown);
                }
            });
        }, 1000)
    })
}

$(document).ready(function () {

    loadListThietBi().then(function() {
        loadBtn();
    });





})

