$(function () {
    indexManger.init();
});

var indexManger = {
    init: function () {
        //indexManger.indexData();
        indexManger.registerLogout();

    },
    registerLogout: function () {
        $("#logoutBtn").on("click",function () {
            // swal({
            //         title: "确定注销吗？",
            //         text: "",
            //         type: "warning",
            //         showCancelButton: true,
            //         confirmButtonColor: "#DD6B55",
            //         confirmButtonText: "确定",
            //         cancelButtonText: "取消",
            //         closeOnConfirm: false
            //     },
            //     function(){
            //         $.getJSON("/admin/logout",function (resp) {
            //             if (resp.code == 200) {
            //                 window.location.href = resp.data;
            //             } else {
            //                 swal("注销失败", resp.msg,"error");
            //             }
            //         });
            //     });
                $.getJSON("/user/logout",function (resp) {
                    if (resp.code == 200) {
                        window.location.href = resp.data;
                    } else {
                        swal("注销失败", resp.msg,"error");
                    }
                });
            });
    },


}