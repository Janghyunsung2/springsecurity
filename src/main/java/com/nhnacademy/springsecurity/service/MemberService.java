package com.nhnacademy.springsecurity.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.springsecurity.model.Member;
import com.nhnacademy.springsecurity.model.MemberCreateCommend;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final MessageService messageService;
    private String HASH_NAME = "Member:";

    private final PasswordEncoder passwordEncoder;

    public MemberService(RedisTemplate<String, Object> redisTemplate, MessageService messageService,
        PasswordEncoder passwordEncoder) {
        this.redisTemplate = redisTemplate;
        this.messageService = messageService;
        this.passwordEncoder = passwordEncoder;
    }

    public void createMember(MemberCreateCommend memberCreateCommand){
        Object object = redisTemplate.opsForHash().get(HASH_NAME, memberCreateCommand.getId());
        if(object != null){
            throw new RuntimeException();
        }

        Member member = new Member(
            memberCreateCommand.getId(),
            memberCreateCommand.getName(),
            passwordEncoder.encode(memberCreateCommand.getPassword()),
            memberCreateCommand.getAge(),
            memberCreateCommand.getRole()
        );

        redisTemplate.opsForHash().put(HASH_NAME, member.getId(), member);
    }


    public Member getMember(String memberId){
        Object object = redisTemplate.opsForHash().get(HASH_NAME, memberId);
        if(object == null){
            throw new RuntimeException();
        }
        return (Member) object;
    }

    public List<Member> getMembers(){
        Map<Object, Object> entries =
            redisTemplate.opsForHash().entries(HASH_NAME);

        List<Member> memberList = new ArrayList<>(entries.size());

        ObjectMapper objectMapper = new ObjectMapper();

        for (Object value : entries.values()) {
            Member member = objectMapper.convertValue(value,Member.class);
            memberList.add(member);
        }
        return memberList;
    }


}
