$(function () {
    SexAnalysis.init();
});

var SexAnalysis = {
    init: function () {
        SexAnalysis.AgeAnalysis();
        SexAnalysis.selectDate();
    },
    AgeAnalysis: function () {
        $("#ageAnalysisbtn").on("click",function () {
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
                url: "/user/ageanalysis/ageAnalysisById",
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
    showDiagramPie: function(data){
        var myChartBar = echarts.init(document.getElementById('main'));

        // 指定图表的配置项和数据

        var optionBar ={
            title : {
                text: '',
                x:'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: data
            },
            series : [
                {
                    name: '年龄数据',
                    type: 'pie',
                    radius : '65%',
                    center: ['50%', '60%'],
                    selectedMode: 'single',
                    label: {
                        normal: {

                            backgroundColor: '#eee',
                            borderColor: '#aaa',
                            borderWidth: 1,
                            borderRadius: 4,
                            rich: {
                                title: {
                                    color: '#999',
                                    lineHeight: 22,
                                    align: 'center'
                                },
                                abg: {
                                    backgroundColor: '#333',
                                    width: '100%',
                                    align: 'right',
                                    height: 25,
                                    borderRadius: [4, 4, 0, 0]
                                },
                                male: {
                                    height: 30,
                                    padding: [0, 0, 5, 0],
                                    align: 'left'

                                },
                                female: {
                                    height: 30,
                                    padding: [0, 0, 5, 0],
                                    align: 'left'

                                },
                                sexHead: {
                                    color: '#333',
                                    height: 24,
                                    padding: [0, 0, 5, 0],
                                    align: 'left'
                                },
                                value: {
                                    width: 20,
                                    padding: [0, 20, 5, 30],
                                    align: 'left'
                                },
                                valueHead: {
                                    color: '#333',
                                    width: 20,
                                    padding: [0, 20, 5, 30],
                                    align: 'center'
                                },
                                rate: {
                                    width: 40,
                                    align: 'right',
                                    padding: [0, 10, 5, 0]
                                },
                                rateHead: {
                                    color: '#333',
                                    width: 40,
                                    align: 'center',
                                    padding: [0, 0, 5, 10]
                                },
                                hr: {
                                    borderColor: '#aaa',
                                    width: '100%',
                                    borderWidth: 0.5,
                                    height: 0
                                },
                                b: {
                                    fontSize: 16,
                                    lineHeight: 33
                                },
                                per: {
                                    color: '#eee',
                                    backgroundColor: '#334455',
                                    padding: [2, 4],
                                    borderRadius: 2
                                },

                            }
                        }
                    },
                    data:(function(){
                        var res = [];
                        var len = 0;
                        var number_male = 0;
                        var number_female = 0;
                        var rate_male = 0;
                        var rate_female = 0;
                        for(var i=0,size=data.length;i<size;i++) {
                            number_male = data[i].man;
                            number_female = data[i].woman;
                            rate_male = data[i].man/data[i].num;
                            rate_male=rate_male.toFixed(2);
                            rate_female = 1-rate_male;
                            rate_female=rate_female.toFixed(2);
                            if(data[i].num!=0){
                                res.push({
                                    label:{
                                        normal:{
                                            formatter: [
                                                '{title|{b}}{abg|}',
                                                '  {sexHead|性别}{valueHead|人数}{rateHead|占比}',
                                                '{hr|}',
                                                '  {male|男性}{value|'+number_male+'}{rate|'+rate_male+'}',
                                                '  {female|女性}{value|'+number_female+'}{rate|'+rate_female+'}'
                                            ].join('\n')
                                        }
                                    },
                                    name: data[i].disc+'岁',
                                    value: data[i].num

                                });
                            }
                        }
                        return res;
                    })(),
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChartBar.setOption(optionBar);

    },


}
