package com.kh.yytest.quickstart;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
	
	@RequestMapping("/hello.do")  //http://localhost:8080/yytest/hello.do로 테스트
	//model객체를 받아 스트링으로 리턴하는 hello메서드
	public String hello(Model model) { 
		model.addAttribute("greeting", "안녕하세요 ★이곳은 쭈꾸미월드★"); //model에 greeting이라는 키?같은거에 값은 안녕하세요.
		return "hello"; // hello jsp 반환
	}

}
