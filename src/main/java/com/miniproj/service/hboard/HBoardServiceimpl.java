package com.miniproj.service.hboard;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.miniproj.model.HBoardDTO;
import com.miniproj.model.HBoardVO;
import com.miniproj.model.PointLogDTO;
import com.miniproj.persistence.HBoardDAO;
import com.miniproj.persistence.MemberDAO;
import com.miniproj.persistence.PointLogDAO;

import lombok.extern.slf4j.Slf4j;

// Service단에서 해야 할 작업
// 1) Controller에서 넘겨진 파라미터를 처리한 후 (비즈니스 로직에 의해 ... 트랜잭션 처리를 통해)
// 2) DB작업이 필요하다면 DAO단 호출 ...
// 3) DAO단에서 반환된 값을 Controller단으로 넘겨준다.

@Slf4j
@Service // 아래의 클래스가 서비스 객체임을 컴파일러에게 알려줌


public class HBoardServiceimpl implements HBoardService {
	
	@Inject
	private HBoardDAO bDao;
	
	@Inject
	private PointLogDAO pDao;
	
	@Inject
	private MemberDAO mDao;
	
	@Override
	public List<HBoardVO> getAllBoard() throws Exception {
		System.out.println("HBoardServiceImpl...........");
		log.info("HBoardServiceImpl...........");
		
		List<HBoardVO> lst = bDao.selectAllBoard(); // DAO 메서드 호출
		
		return lst;
	}

	@Override
	public boolean saveBoard(HBoardDTO newBoard) throws Exception {
		boolean result = false;
		// 1) newBoard를 DAO 단을 통해서 insert 한다.
		if (bDao.insertNewBoard(newBoard) == 1) {
			// 2) 1)에서 insert가 성공하면, pointlog 에 저장한다.
			if (pDao.insertPointLog(new PointLogDTO(newBoard.getWriter(), "글작성")) == 1) {
				// 3) 작성자의 userPoint 값을 update 한다.
				if (mDao.updateUserPoint(newBoard.getWriter()) == 1) {
					result = true;
				}
			}
		}
		return result;
	}

}
