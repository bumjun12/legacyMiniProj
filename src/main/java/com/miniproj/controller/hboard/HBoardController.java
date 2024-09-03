package com.miniproj.controller.hboard;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.miniproj.model.HBoardDTO;
import com.miniproj.model.HBoardVO;
import com.miniproj.service.hboard.HBoardService;
import com.miniproj.util.FileProcess;

// Controller 단에서 해야 할 일
// 1) URI 매핑 (어떤 URI가 어떤 방식 (GET/POST)으로 호출되었을 때 어떤 메서드에 매핑 시킬 것이냐)
// 2) 있다면 view단에서 보내준 매개변수 수집
// 3) 데이터베이스에 대한 CRUD 를 수행하기 위해 service 단의 해당 메서드를 호출 .
//    service단에서 return 값을 view 로 바인딩 + view단 호출
// 4) 부가적으로 컨트롤러단은 Servlet에 의해서 동작되는 모듈이기 때문에, HttpServletRequest HttpServletReponse,
//	  HttpSession 등의 Servlet 객체를 이용할 수 있다. -> 이러한 객체들을 이용하여 구현 할 기능이 있다면,
// 	  그 기능은 컨트롤러단에서 구현한다.

@Controller // 아래의 클래스가 컨트롤러 객체임을 명시
@RequestMapping("/hboard")
public class HBoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(HBoardController.class);
	
	@Inject
	private HBoardService service; // 서비스객체 주입
	
	@Inject
	private FileProcess fileProcess; // FilePorcess 객체 주입
	
	@RequestMapping("/listAll") // /hboard/listAll
	public void listAll(Model model) {
		logger.info("HBoardController.listAll().............");
		
		List<HBoardVO> lst;
		try {
			lst = service.getAllBoard();
			model.addAttribute("boardList", lst); // model 객체에 데이터 바인딩
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("exception", "error");
			
		} // 서비스 메서드 호출
	
//		for (HBoardVO b : lst) {
//			System.out.println(b.toString());
//			
//		}
	}
	
	@RequestMapping(value="/saveBoard", method = RequestMethod.GET)
	public String showSaveBoardForm() { // 게시판 글 저장페이지를 출력하는 메서드
		return "/hboard/saveBoardForm";
	}
	
	// 게시글 저장 버튼을 누르면 해당 게시글을 DB에 저장하는 메서드
	@RequestMapping(value="/saveBoard", method = RequestMethod.POST)
	public String saveBoard(HBoardDTO boardDTO, RedirectAttributes rttr) {
		System.out.println("글 저장하러 가자 : " + boardDTO.toString());
		
		String returnPage = "redirect:/hboard/listAll";
		
		try {
			if(service.saveBoard(boardDTO)) {
				System.out.println("저장 성공");
				rttr.addAttribute("status", "success");
			}
		} catch (Exception e) {	
			e.printStackTrace();
			rttr.addAttribute("status", "fail");
		}
		
		return returnPage; // 게시글 전체 목록 페이지로 돌아감
	}
	
	@RequestMapping(value="/upfiles", method=RequestMethod.POST)
	public void saveBoardfile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		System.out.println("파일 전송 요청됨");
		System.out.println("파일의 오리지널 이름 : " + file.getOriginalFilename());
		System.out.println("파일의 사이즈 : " + file.getSize());
		System.out.println("파일의 contentType : " + file.getContentType());
		
		try {
			fileSave(file, request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void fileSave(MultipartFile file, HttpServletRequest request) throws IOException {
		// 파일의 기본 정보 저장
		String originalFileName = file.getOriginalFilename();
		Long fileSize = file.getSize();
		String conteneType = file.getContentType();
		byte[] upfile = file.getBytes(); // 파일의 실제 데이터
		
		String realPath = request.getSession().getServletContext().getRealPath("/resources/boardUpFiles");
		System.out.println("서버의 실제 물리적 경로 : " + realPath);
		
		// 실제 파일 저장
		fileProcess.saveFileToRealPath(upfile, realPath, conteneType, originalFileName, fileSize);
	}

	
}


