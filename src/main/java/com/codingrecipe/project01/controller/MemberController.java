package com.codingrecipe.project01.controller;

import com.codingrecipe.project01.dto.MemberDTO;
import com.codingrecipe.project01.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // RequestMapping member선언으로 save만 받아도 돌아감
    //@GetMapping("/member/save")
    @GetMapping("/save")
    public String saveForm(){
        return "save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO){
        // ModelAttribute jsp form 필드에 있는 값을 dto와 매치 시켜서 가져옴
        // 필드의 name값이랑 dto 변수명일아 다르면 x
        // memberDTO 객체로 가져온다.
        // 흐름 jsp -> controller(dto에 매칭된? 값) -> service -> Repository
        // 받을때는 위에 역순
        int saveResult = memberService.save(memberDTO);

        if(saveResult > 0) { // 가입 성공
            return "login";
        }else { // 가입 실패
            return "save";
        }
    }

    @GetMapping("/login")
    public String loginForm(){
       return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session){
        boolean loginResult = memberService.login(memberDTO);
        if(loginResult){
            session.setAttribute("loginEmail",memberDTO.getMemberEmail());
            return "main";
        }else{
            return "login";
        }
    }

}
