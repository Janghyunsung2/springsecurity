package com.nhnacademy.springsecurity.client;

import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "messageClient", url = "https://hook.dooray.com/services/3204376758577275363/3939383629139110347/6GoYNd1QT2SBd1DXke0Yrg")
public interface MessageClient {

    @PostMapping(consumes = "application/json")
    ResponseEntity<Object> sendMessage(@RequestBody Map<String, Object> requestBody);
}
