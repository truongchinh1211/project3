var listTb;

var headerAuthorization = Cookies.get("Authorization") ?? "Bearer eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MTUzMjk5NTMsInN1YiI6ImFAZ21haWwuY29tIn0.2_wAav2uisQZ0zODNa3r7t9TFaLx7reBlQ873ztflgI";
// var headerAuthorization = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MTUzMTQxNDAsInN1YiI6ImFAZ21haWwuY29tIn0.QcxaNPCiJxIck-Mi6RzixjhCnbq41wsiAzu3FWWi0Gk";

function loadListThietBi() {
    return new Promise(function (myResolve, myReject) {
        $.ajax({
            url: "http://localhost:8080/api/v1/thietbi",
            type: "get",

            headers: {
                "Authorization": headerAuthorization
            },
            success: function (result) {
                listTb = result;

                var html = ``;
                listTb.forEach(function (tb, index) {
                    let injecthtml = `<tr>
                    <th scope="row">${index}</th>
                    <th >${tb.maTB}</th>
                    <td>${tb.tenTB}</td>
                    <td>${tb.moTaTB}</td>
                    <td>
                      <div class="btn-group btn-group-justified">
                        <button type="button" class="btn btn-primary" index=${index} 
                        data-bs-toggle="modal" data-bs-target="#datchoModal">Đặt chỗ</button>
                      </div>
                    </td>
                  </tr>`;


                    html += injecthtml

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

    $('#datchoModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var listIndex = button.attr('index') // Extract info from data-* attributes
        // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
        // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
        var modal = $(this)
        modal.find('.modal-body #maTBInput').val(listTb[listIndex].maTB)
        modal.find('.modal-body #tenTBInput').val(listTb[listIndex].tenTB)
        modal.find('.modal-body #moTaTBInput').val(listTb[listIndex].moTaTB)
        modal.find('.modal-footer .datchoBtn').attr("index", button.attr("index"))
        $("#message-modal").addClass("invisible")
        modal.find('.modal-body #tgDatchoInput').val("")


    })

    $(".datchoBtn").click(function () {
        // if ($(".tgDatchoInput").val() == null) {
        //     $("#message-modal").removeClass()
        //     $("#message-modal").addClass("text-danger")
        //     $("#message-modal").text("Thời gian đặt chỗ không được bỏ trống!!")
        //     return;
        // }

        var button = $(this);
        
        button.prop("disabled", true);
        button.html('<i class="spinner-border spinner-border-sm"></i> Đang Đặt Chỗ...');
 

        let index = $(this).attr("index")
        let formData = {
            maTB: listTb[index].maTB,
            TGDatcho: $("#tgDatchoInput").val()

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
                    $("#message-modal").removeClass()   
                    $("#message-modal").addClass("text-success")
                    $("#message-modal").text("Đặt chỗ thành công!!")
                    button.prop("disabled", false);
                    button.html('Đặt Chỗ');
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    $("#message-modal").removeClass()
                    $("#message-modal").addClass("text-danger")
                    var resText = JSON.parse(jqXHR.responseText)
                    $("#message-modal").text(resText.error)
                    button.prop("disabled", false);
                    button.html('Đặt Chỗ');


                    console.log("Request failed:", textStatus, errorThrown);
                }
            });
        }, 1000)
    })



    $(".searchBtn").click (function () {
        var keyword = $(".searchText").val()
        $.ajax({
            url: `http://localhost:8080/api/v1/thietbi?keyword=${keyword}`,
            type: "get",

            headers: {
                "Authorization": headerAuthorization
            },
            success: function (result) {
                listTb = result;

                var html = ``;
                listTb.forEach(function (tb, index) {
                    let injecthtml = `<tr>
                    <th scope="row">${index}</th>
                    <th >${tb.maTB}</th>
                    <td>${tb.tenTB}</td>
                    <td>${tb.moTaTB}</td>
                    <td>
                      <div class="btn-group btn-group-justified">
                        <button type="button" class="btn btn-primary" index=${index} 
                        data-bs-toggle="modal" data-bs-target="#datchoModal">Đặt chỗ</button>
                      </div>
                    </td>
                  </tr>`;


                    html += injecthtml

                })
                $(".table-body").html(html);

          

            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log("Request failed:", textStatus, errorThrown);
            }
        });
    })

}

$(document).ready(function () {

    loadListThietBi().then(function () {
        loadBtn();
    });





})

