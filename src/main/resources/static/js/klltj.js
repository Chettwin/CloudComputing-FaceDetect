$(function () {
    PassflowAnalysis.init();
});

var PassflowAnalysis = {
    init: function () {
        PassflowAnalysis.selectDate();
        PassflowAnalysis.passflowAnalysis();
    },
    passflowAnalysis: function () {
        $("#passflowAnalysisbtn").on("click",function () {
            var userId = $("#userid").val();

            var endDate = $("#startTime").val();
            var startDate = $("#endTime").val();
            //console.log(userId);
            if (endDate == "" || startDate == "") {
                //console.log("dsm,dmskmdksd");
                swal("日期不能为空", "","error");
                return;
            }
            $.ajax({
                type: "post",
                url: "/user/passflowanalysis/passFlowAnalysisById",
                data: JSON.stringify({"userId": userId, "startDate": startDate,"endDate":endDate}),
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                success: function (data, status, xhr) {
                    if (data.code == 200) {
                        if(data.data == "null"){
                            swal("暂无数据,请换个日期查询", "","error");
                            return;
                        }
                        console.log(data);
                        PassflowAnalysis.showDiagramLine(data.data);
                    } else if (data.code == 500) {
                        swal("失败", data.msg, "error");
                    } else {
                        swal("失败", data.msg, "error");
                    }
                },
            })
        });

    },
    
    selectDate: function(){
        $('#startTime').datepicker({
            language: "zh-CN",
            format: "yyyy-mm-dd",
            autoclose: true//选中之后自动隐藏日期选择框
        }).on('changeDate', function () {
            //$("#beginTime-error").hide();
            var startTime = $("#startTime").val();
            $("#endTime").datepicker('setStartDate', startTime);//设置一个起始时间给endTime保证结束时间大于起始时间
            $("#startTime").datepicker('hide');
        });

        $('#endTime').datepicker({
            language: "zh-CN",
            format: "yyyy-mm-dd",
            autoclose: true//选中之后自动隐藏日期选择框
        }).on('changeDate', function () {//这个事件用来改变日期
            var endtime = $("endTime").val();
            $("#startTime").datepicker('setEndDate', endtime);//设置一个起始时间给startTime保证起始时间小于结束时间
            $("#endTime").datepicker('hide');
        });
    },
    showDiagramLine: function(data){
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));
        // 指定图表的配置项和数据
        var xAxisData = [];
        var yAxisData = [];
        for (var i = 0; i < data.length; i++) {
            xAxisData.push(data[i].date);    //挨个取出类别并填入类别数组
        }
        for (var i = 0; i < data.length; i++) {
            yAxisData.push(data[i].passengerFolw);    //挨个取出类别并填入类别数组
        }
        var option = {
            title: {
                text: ''
            },
            tooltip: {},
            legend: {
                data:['人数']
            },
            xAxis: {
                data: xAxisData
            },
            yAxis: {},
            series: [{
                data: yAxisData,
                type: 'line',
                name: '人数'
            }]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    },
}
