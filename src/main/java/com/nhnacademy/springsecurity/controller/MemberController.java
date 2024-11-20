package com.nhnacademy.springsecurity.controller;

import com.nhnacademy.springsecurity.model.Member;
import com.nhnacademy.springsecurity.model.MemberCreateCommend;
import com.nhnacademy.springsecurity.service.MemberService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public Page<Member> getMembers(Pageable pageable){
        List<Member> members = memberService.getMembers();
        return new PageImpl<>(members, pageable,pageable.getOffset());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMember(@PathVariable String id){
        try {
            Member member = memberService.getMember(id);
            return ResponseEntity.ok(member);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }


    }
    @PostMapping
    public ResponseEntity<String> createMember(@RequestBody MemberCreateCommend memberCreateCommend){
        try{
            memberService.createMember(memberCreateCommend);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }



}

