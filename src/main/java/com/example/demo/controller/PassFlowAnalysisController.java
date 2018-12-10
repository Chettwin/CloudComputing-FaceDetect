package com.example.demo.controller;

import com.example.demo.common.ParamObject;
import com.example.demo.common.Result;
import com.example.demo.model.AgeAnalysis;
import com.example.demo.model.PassengerFlow;
import com.example.demo.service.AgeAnalysisService;
import com.example.demo.service.PassengerFlowAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/passflowanalysis")
public class PassFlowAnalysisController {

    @Autowired
    PassengerFlowAnalysisService passflowanalysis;
    /*
    *    param：ParamObject {userId startDate大 endDate小} 用户id, 查询时间(默认查询时间为当前日期直过去的七天)
    *    return: {code, msg, data} data: 用户id，以及男女数量[id, manNum, womanNum]
     */
    @RequestMapping("/passFlowAnalysisById")
    public Result showPassFlowAnalysisById(@RequestBody ParamObject params){

        System.out.print(params);

        List<PassengerFlow> passFlowAnalyResult = passflowanalysis.showPassFlowAnalysisById(params);
        System.out.print(passFlowAnalyResult.size());
        if(passFlowAnalyResult==null){
            return Result.fail();
        }else if(passFlowAnalyResult.size() == 0){
            return Result.success("null");
        }else{
            return Result.success(passFlowAnalyResult);
        }

    }
}
