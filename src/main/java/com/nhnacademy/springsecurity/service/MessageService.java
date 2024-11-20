package com.nhnacademy.springsecurity.service;

import com.nhnacademy.springsecurity.client.MessageClient;
import com.nhnacademy.springsecurity.exception.InvalidResponseException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final MessageClient messageClient;


    public MessageService(MessageClient messageClient) {
        this.messageClient = messageClient;
    }

    public void send(String name, String text) {
        // JSON 요청 본문을 Map으로 구성
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("botName", name);
        requestBody.put("text", text);

        Map<String, String> attachment = new HashMap<>();
//        attachment.put("title", "메세지 타이틀");
//        attachment.put("text", "안녕하세요.");
        attachment.put("titleLink", "http://naver.com");
        attachment.put("botIconImage", "https://static.dooray.com/static_images/dooray-bot.png");
        attachment.put("color", "red");

        requestBody.put("attachments", new Object[]{attachment});

        // FeignClient 호출
        ResponseEntity<Object> response = messageClient.sendMessage(requestBody);

        // 응답 상태 코드 확인
        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            throw new InvalidResponseException();
        }
    }
}

