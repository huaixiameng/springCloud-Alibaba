package com.springCloud.controller;

import com.springCloud.pojo.CommonResult;
import com.springCloud.pojo.Payment;
import com.springCloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RefreshScope
public class PaymentController {

    @Value("${server.port}")
    String servicePort;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/payment/create")
    public CommonResult create(@RequestBody Payment dept) {
        int i = paymentService.create(dept);
        log.info("***************插入成功*******" + i);
        if (i > 0) {
            return new CommonResult(200, "插入数据库成功", i);
        } else {
            return new CommonResult(444, "插入数据库失败", null);
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult queryById(@PathVariable("id") Long id) {
        Payment payment = paymentService.queryById(id);
        payment.setServicePort(servicePort);
        log.info("***************查询成功*********" + payment);
        if (payment != null) {
            return new CommonResult(200, "查询成功", payment);
        } else {
            return new CommonResult(444, "查询失败", null);
        }
    }

    @Value("${config.info}")
    private  String configInfo;

    @GetMapping("/config/info")
    public String getConfigInfo(){
        System.out.println(configInfo);
        return configInfo;
    }

}