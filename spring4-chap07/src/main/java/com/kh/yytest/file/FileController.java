package com.kh.yytest.file;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FileController {
	
	//get방식으로 클라이언트의 요청이 들어왔을때, 그 값에 해당하는 파일이 있으면 정보출력
	@RequestMapping(value = "/files/{fileId:[a-zA-Z]\\d{3}}", method = RequestMethod.GET)
	public String fileInfo(@PathVariable String fileId) throws NoFileInfoException {
		FileInfo fileInfo = getFileInfo(fileId);
		if(fileInfo == null) {
			throw new NoFileInfoException();
		}
		return "files/fileInfo";
	}
	
	//파일아이디를 받아 해당 파일에 대한 정보를 객체로 리턴해줌
	private FileInfo getFileInfo(String fileId) {
		if("a111".equals(fileId)) 
			return null;
		return new FileInfo(fileId);
	}

	//파일 교체 버튼을 눌러서 post방식으로 클라이언트의 요청이 들어왔을때 처리하는 메서드.
	@RequestMapping(value = "/files/{fileId:[a-zA-Z]\\d{3}}", method = RequestMethod.POST)
	public String updateFile(@PathVariable String fileId) {
		return "redirect:/files/{fileId}";
	}
	
	@RequestMapping("/files/?*.download")
	public String fileInfo(HttpServletRequest request) {
		return "files/fileDownload";
	}
	
	//해당 폴더에 있는 파일목록을 출력하나??
	@RequestMapping("/folders/**/files")
	public String list(HttpServletRequest request, Model model) {
		String uri = request.getRequestURI();
		if(uri.endsWith("/folders/files")) {
			model.addAttribute("folderIds", new String[0]);
		}
		else {
			String ctxPath = request.getContextPath();
			String path = ctxPath.isEmpty() ?  uri: uri.substring(ctxPath.length());
			String folderTreePath = path.substring("/folders/".length(), path.length() - "/files".length());
			String[] folderIds = folderTreePath.split("/");
			model.addAttribute("folderIds",folderIds);
		}
		return "files/filesInFolder";
	}
	
}
