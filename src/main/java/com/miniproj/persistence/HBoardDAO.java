package com.miniproj.persistence;

import java.util.List;

import com.miniproj.model.HBoardDTO;
import com.miniproj.model.HBoardVO;

public interface HBoardDAO {
	// 게시판 전체 목록 가져오는 메서드
	List<HBoardVO> selectAllBoard() throws Exception;

	// 게시글 저장하는 메서드
	int insertNewBoard(HBoardDTO newBoard) throws Exception;
	
	
}
