package com.codingrecipe.project01.controller;

import com.codingrecipe.project01.dto.MemberDTO;
import com.codingrecipe.project01.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller //컨트롤러 어노테이션(컨트롤러 객체를 자동으로 생성)
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService; //@RequiredArgsConstructor -> 한번 선언하면 밑에 쓸대마다 일일히 선언 안해도 됨

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


    // @RequestParam -> 1:1 매칭 객체 선언 후 손수 set을 해줘야함
    // @RequestParam("name") String name, -> ("name") -> 실제값 String name -> 실제값을 담은 변수명
    // ex) /getDriver?name="name에 담긴 value"
    // @RequestParam("name") -> 괄호 안에 name는 "name에 담긴 value"
    // 이렇게 @RequestParam의 경우 url뒤에 붙는 파라미터의 값을 가져올 때 사용합니다.

    // @ModelAttribute -> 객체 매핑 dto,vo등 객체가 있어야함 자동으로 set한다고 생각
    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session){
        System.out.println(memberDTO.getMemberAge());
        boolean loginResult = memberService.login(memberDTO);
        if(loginResult){
            session.setAttribute("loginEmail",memberDTO.getMemberEmail());
            return "main";
        }else{
            return "login";
        }
    }

    // @ResponseBody
    // view 조회를 무시하고, Http message body에 직접 해당 내용 입력하여
    // 화면에 return 값 출력
    // viewResolver를 무시하고 화면에 출력하겠다는 의미
    // @ResponseBody 선언시 return에 페이지 이동이 아닌 매핑한 경로에 값 그대로 출력


    // @RequestBody
    // ajax에서 파라미터를 받아올떄 컨트롤러에서 저 어노테이션을 쓰면 됨
    // 이렇게 받지 않으면 자바객체로 취급을 안하기에 오류가 남
    
    @GetMapping("/")
    public String findAll(Model model){ // Model : 데이터를 담는 그릇 역할, map 구조로 저장됨// key와 value로 구성
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList",memberDTOList);

        // <beans:property name="prefix" value="/WEB-INF/views/" />
        // <beans:property name="suffix" value=".jsp" />
        // /WEB-INF/views/list.jsp
        return  "list";
        // list.jsp로 포워딩됨 //JSP에서 dispatcher 객체로 forward한것과 같은 역할
        //나머지 경로는 spring > appServlet > servlet-context.xml에 설정돼있음
        //컨트롤러에 대한 설정은 서블릿컨텍스트에 있다.
    }//>> list.jsp를 컨트롤 클래스를 경유하여 실행한다.

    // member?id = 1
    @GetMapping
    public String findById(@RequestParam("id") Long id, Model model){
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member",memberDTO);
        return "detail";
    }
}
