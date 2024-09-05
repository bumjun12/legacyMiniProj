package com.miniproj.persistence;

import java.util.List;

import com.miniproj.model.BoardUpFilesVODTO;
import com.miniproj.model.HBoardDTO;
import com.miniproj.model.HBoardVO;

public interface HBoardDAO {
	// 게시판 전체 목록 가져오는 메서드
	List<HBoardVO> selectAllBoard() throws Exception;

	// 게시글 저장하는 메서드
	int insertNewBoard(HBoardDTO newBoard) throws Exception;

	// 최근 저장된 글의 글번호
	int selectMaxBoardNo() throws Exception;

	// 업로드된 첨부파일을 저장하는 메서드
	int insertBoardUpFile(BoardUpFilesVODTO file) throws Exception;

	HBoardVO selectBoardByBoardNo(int boardNo) throws Exception;// 유저가 입력한 텍스트만 hboard 에서 조회

	List<BoardUpFilesVODTO> selectBoardUpfileByBoardNo(int boardNo) throws Exception; // 업로드한 파일만 조회
	
	
}
