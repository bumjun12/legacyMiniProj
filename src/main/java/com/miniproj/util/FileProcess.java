package com.miniproj.util;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component // 스프링 컨테이너에 객체를 만들어 관리하도록 하는 어노테이션
public class FileProcess {

	public void saveFileToRealPath(byte[] upfile, String realPath, String conteneType, String originalFileName,
			Long fileSize) {
		
		// 파일이 실제 저장되는 경로 : realPath + "년/월/일"
		String[] ymd = makeCalenderPath(realPath);
		makeDirectory(realPath, ymd); // 실제 directory 를 만드는 메서드
		
		String saveFilePath = realPath + ymd[ymd.length-1]; // 실제 파일의 저장 경로
		
		String newFileName = null;
		
		if (fileSize > 0) {
			if (checkFileExist(saveFilePath, originalFileName)) { // 파일이름 중복 검사 -> 파일이름 변경
				newFileName = renameFileName(originalFileName);
			} else {
				newFileName = originalFileName;
			}
			
		}
		
	}

	private String renameFileName(String originalFileName) {
		String timestamp = System.currentTimeMillis() + "";

		// new.png -> new_timestamp.png
		
		String newFileName = null;
		System.out.println("originalFileName : " + originalFileName);
		System.out.println("newFileName : " + newFileName);
		
		return null;
	}

	private boolean  checkFileExist(String saveFilePath, String originalFileName) {
		// 파일이름 중복 검사 : 중복된 파일이 있다면 true, 없다면 false
		boolean isFind = false;
		File tmp = new File (saveFilePath);
		String[] dirs = tmp.list();
		if (dirs != null) {
			for (String name : dirs) {
				if (name.equals(originalFileName)) {
					System.out.println("이름이 같은 것이 있다!");
					isFind = true;
					break;
				}
			}
		}
		
		if (!isFind) {
			System.out.println("이름이 같은 것이 없다!");
		}
		
		return isFind;
	}

	private void makeDirectory(String realPath, String[] ymd) {
		// 실제 directory 를 만드는 메서드
		System.out.println(new File(realPath + ymd[ymd.length-1]).exists());
		if (!new File(realPath + ymd[ymd.length-1]).exists()) {
			// realPath + \2024\09\03 디렉토리가 모두 존재하지 않는다면 ...
			for (String path : ymd) {
				File tmp = new File(realPath + path);
				if (!tmp.exists()) {
					tmp.mkdir();
				}
			}
		}
	}

	private String[] makeCalenderPath(String realPath) {
		// 파일이 실제 저장되는 경로 : realPath + "/년/월/일"
		
		Calendar now = Calendar.getInstance(); // 현재 날짜 시간 객체
		String year = File.separator + now.get(Calendar.YEAR ) + ""; // \2024
		String month = year + File.separator + new DecimalFormat("00").format((now.get(Calendar.MONTH) + 1)); // \2024\09
		String date = month + File.separator + new DecimalFormat("00").format(now.get(Calendar.DATE)); // \2024\09\03 
		
		System.out.println("year/month/date : " + year + "," + month + "," + date);
		
		String[] ymd = {year, month, date};
		
		return ymd;

	}
}
