package com.example.demo.controller;

import com.example.demo.common.ParamObject;
import com.example.demo.common.Result;
import com.example.demo.model.SexAnalysis;
import com.example.demo.service.SexAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/sexanalysis")
public class SexAnalysisController {

    @Autowired
    SexAnalysisService sexanalysis;
    /*
    *    param：ParamObject {userId startDate大 endDate小} 用户id, 查询时间(默认查询时间为当前日期直过去的七天)
    *    return: {code, msg, data} data: 用户id，以及男女数量[id, manNum, womanNum]
     */
    @RequestMapping("/sexAnalysisById")
    public Result showSexAnalysisById(@RequestBody ParamObject params){

        System.out.print(params);

        List<SexAnalysis> sexAnalyResult = sexanalysis.showSexAnalysisById(params);
        System.out.print(sexAnalyResult.size());
        if(sexAnalyResult.size() == 0){
            System.out.print("NUKLLLLLLL");
            return Result.success("没有数据");
        }
        if(sexAnalyResult.size() == 2){
            sexAnalyResult.get(0).setUserId(params.getUserId());
            sexAnalyResult.get(1).setUserId(params.getUserId());

        }else if(sexAnalyResult.size() == 1){
            if(sexAnalyResult.get(0).getSexType() == 1){

                SexAnalysis woman = new SexAnalysis();
                woman.setUserId(params.getUserId());
                woman.setSexType(2);
                woman.setSexNum(0);
                sexAnalyResult.get(0).setUserId(params.getUserId());
                sexAnalyResult.add(woman);
            }else {
                SexAnalysis man = new SexAnalysis();
                man.setUserId(params.getUserId());
                man.setSexType(1);
                man.setSexNum(0);
                sexAnalyResult.get(0).setUserId(params.getUserId());
                sexAnalyResult.add(man);
            }
        }
        return Result.success(sexAnalyResult);




    }
}
