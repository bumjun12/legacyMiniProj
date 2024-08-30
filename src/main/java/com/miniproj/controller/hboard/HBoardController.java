package com.miniproj.controller.hboard;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.miniproj.model.HBoardVO;
import com.miniproj.service.hboard.HBoardService;

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
	
	@RequestMapping("/listAll") // /hboard/listAll
	public void listAll(Model model) {
		logger.info("HBoardController.listAll().............");
		
		List<HBoardVO> lst = service.getAllBoard(); // 서비스 메서드 호출
	
//		for (HBoardVO b : lst) {
//			System.out.println(b.toString());
//			
//		}
	
		model.addAttribute("boardList", lst); // model 객체에 데이터 바인딩
	}
	
}


