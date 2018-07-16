package com.kh.yytest.quickstart;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
	
	@RequestMapping("/hello.do")  //http://localhost:8080/yytest/hello.do�� �׽�Ʈ
	//model��ü�� �޾� ��Ʈ������ �����ϴ� hello�޼���
	public String hello(Model model) { 
		model.addAttribute("greeting", "�ȳ��ϼ��� ���̰��� �޲ٹ̿����"); //model�� greeting�̶�� Ű?�����ſ� ���� �ȳ��ϼ���.
		return "hello"; // hello jsp ��ȯ
	}

}
