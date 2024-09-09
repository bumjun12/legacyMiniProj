package com.miniproj.service.hboard;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.miniproj.model.BoardDetailInfo;
import com.miniproj.model.BoardUpFilesVODTO;
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
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean saveBoard(HBoardDTO newBoardDTO) throws Exception {
		boolean result = false;
		
		// 1) newBoard를 DAO 단을 통해서 insert (유저가 입력한 텍스트) 한다.
		if (bDao.insertNewBoard(newBoardDTO) == 1) { // 예) 10번 글 저장
			// 첨부파일이 있다면, 첨부파일 또한 저장한다. 예) boardNO = 10
			// 1-1) 방금 저장된 게시글의 번호
				int newBoardNo = bDao.selectMaxBoardNo();
			// 1-2) 1-1)에서 얻어온 게시글 번호를 참조하는 첨부파일정보를 insert 해야 한다.
				for(BoardUpFilesVODTO file : newBoardDTO.getFileList()) {
					file.setBoardNo(newBoardNo);
					bDao.insertBoardUpFile(file);
				}
			
			// 2) 1)에서 insert가 성공하면, pointlog 에 저장한다.
			if (pDao.insertPointLog(new PointLogDTO(newBoardDTO.getWriter(), "글작성")) == 1) {
				// 3) 작성자의 userPoint 값을 update 한다.
				if (mDao.updateUserPoint(newBoardDTO.getWriter()) == 1) {
					result = true;
				}
			}
		}
		return result;
	}

	@Override
	public HashMap<String, Object> viewBoardByBoardNo(int boardNo) throws Exception {
//		bDao.selectBoardByBoardNo(boardNo); // 유저가 입력한 텍스트만 hboard 에서 조회
//		bDao.selectBoardUpfileByBoardNo(boardNo); // 업로드한 파일만 조회
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("board", bDao.selectBoardByBoardNo(boardNo));
		map.put("fileList", bDao.selectBoardUpfileByBoardNo(boardNo));
		
		return map;
	}

	@Override
	public HBoardDTO testResultMap(int boardNo) throws Exception {
		return bDao.testResultMap(boardNo);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public List<BoardDetailInfo> read(int boardNo, String ipAddr) throws Exception {
		List<BoardDetailInfo> boardInfo = bDao.selectBoardDetailByBoardNo(boardNo);
		
		// 조회수 증가
		// dateDiff = 날짜차이 계산결과
		int dateDiff = bDao.selectDateDiff(ipAddr, boardNo);
		
		if (dateDiff == -1) { // - 해당 아이피 주소가 boardNo번 글을 최초로 조회
			// → 아이피 주소가 boardNo번 글을 읽은 시간을 테이블에 저장 (insert)
			if (bDao.insertBoardReadLog(ipAddr, boardNo) == 1) {
				// → 조회수 증가 (+1)
				updateReadCount(boardNo, boardInfo);
			}
		} else if (dateDiff >= 1) { // 해당 아이피 주소가 boardNo번 글을 최초로 조회
			// → 아이피 주소가 boardNo번 글을 읽은 시간을 테이블에 수정 (update)
			bDao.updateReadWhen(ipAddr, boardNo);
			// → 조회수 증가 (+1)
			updateReadCount(boardNo, boardInfo);
		}
		return boardInfo;
	}

	protected void updateReadCount(int boardNo, List<BoardDetailInfo> boardInfo) throws Exception {
		if (bDao.updateReadCount(boardNo) == 1) {
			for (BoardDetailInfo b : boardInfo) {
				b.setReadCount(b.getReadCount() + 1);
			}
		};
	}
}
