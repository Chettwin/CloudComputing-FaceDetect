$(function () {
    SexAnalysis.init();
});

var SexAnalysis = {
    init: function () {
        SexAnalysis.sexAnalysis();
        SexAnalysis.selectDate();
    },
    sexAnalysis: function () {
        $("#sexAnalysisbtn").on("click",function () {
            var userId = $("#userid").val();

            var endDate = $("#startTime").val();
            var startDate = $("#endTime").val();
            console.log(userId);
            if (endDate == "" || startDate == "") {
                console.log("dsm,dmskmdksd");
                swal("日期不能为空", "","error");
                return;
            }
            $.ajax({
                type: "post",
                url: "/user/sexanalysis/sexAnalysisById",
                data: JSON.stringify({"userId": userId, "startDate": startDate,"endDate":endDate}),
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                success: function (data, status, xhr) {
                    if (data.code == 200) {
                        console.log(data.data);
                        if(data.data == "没有数据"){
                            swal("暂无数据,请换个日期查询", "","error");
                            return;
                        }else{

                            SexAnalysis.showDiagramPie(data.data);
                            SexAnalysis.showDiagramBar(data.data);
                        }




                    } else if (data.code == 500) {
                        swal("失败", data.msg, "error");
                    } else {
                        swal("败", data.msg, "error");
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
            $("#beginTime-error").hide();
            var startTime = $("#startTime").val();
            $("#endTime").datepicker('setStartDate', startTime);
            $("#startTime").datepicker('hide');
        });

        $('#endTime').datepicker({
            language: "zh-CN",
            format: "yyyy-mm-dd",
            autoclose: true//选中之后自动隐藏日期选择框
        }).on('changeDate', function () {
            var startTime = $("startTime").val();
            var endtime = $("endTime").val();
            $("#startTime").datepicker('setEndDate', endtime);
            $("#endTime").datepicker('hide');
        });
    },
    showDiagramBar: function(data){
        var myChartBar = echarts.init(document.getElementById('main'));

        // 指定图表的配置项和数据
        
        var optionBar = {
            title: {
                text: ''
            },
            tooltip: {},
            legend: {
                data:['人数']
            },
            xAxis: {
                data: ["男","女"]
            },
            yAxis: {},
            series: [{
                name: '人数',
                type: 'bar',
                data: [data[0].sexNum, data[1].sexNum]
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChartBar.setOption(optionBar);

    },
    showDiagramPie: function (data) {
        var myCharPie = echarts.init(document.getElementById('pie'));
        var optionPie = {
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b}: {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                x: 'left',
                data:['男','女']
            },
            series: [
                {
                    name:'访问来源',
                    type:'pie',
                    radius: ['50%', '70%'],
                    avoidLabelOverlap: false,
                    label: {
                        normal: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            show: true,
                            textStyle: {
                                fontSize: '30',
                                fontWeight: 'bold'
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            show: false
                        }
                    },
                    data:[
                        {value:data[0].sexNum, name:'男'},
                        {value:data[1].sexNum, name:'女'}
                    ]
                }
            ]
        };
        myCharPie.setOption(optionPie);
    }

        // $.ajax({
        //     type: "post",
        //     url: "/user/login",
        //     data: JSON.stringify({"username": username, "password": password}),
        //     dataType: "json",
        //     contentType: "application/json;charset=UTF-8",
        //     success: function (data, status, xhr) {
        //         if (data.code == 200) {
        //             window.location.href = data.data;
        //         } else if (data.code == 400) {
        //             swal("登录失败", resp.msg, "error");
        //         } else {
        //             $("#changeBtn").attr("src", "/admin/captcha.do");
        //             swal("登录失败", resp.msg, "error");
        //         }
        //     },
        // })

}
