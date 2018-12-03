package com.example.demo.controller;

import com.example.demo.common.ParamObject;
import com.example.demo.common.Result;
import com.example.demo.model.SexAnalysis;
import com.example.demo.service.SexAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/sexanalysis")
public class SexAnalysisController {

    @Autowired
    SexAnalysisService sexanalysis;
    /*
    *    param：ParamObject 用户id, 查询时间(默认查询时间为当前日期直过去的七天)
    *    return: {code, msg, data} data: 用户id，以及男女数量[id, manNum, womanNum]
     */
    @RequestMapping("/sexAnalysisById")
    public Result showSexAnalysisById(ParamObject params){
        List<SexAnalysis> sexAnalyResult = sexanalysis.showSexAnalysisById(params);

        return Result.success(sexAnalyResult);
    }
}
