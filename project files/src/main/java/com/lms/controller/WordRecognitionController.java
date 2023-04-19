package com.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lms.service.WordRecognitionService;

@CrossOrigin
@RestController
@RequestMapping("/wordRecognition")
public class WordRecognitionController {
	@Autowired
	private WordRecognitionService wordRecognitionService;
	
	/**
	 * @param questionNumber 题号
	 * @param questionMark 标准分
	 * @param file 图片文件
	 * @return wordRecognitionService.wordRecognition(file, questionNumber, questionMark)的结果
	 */
	@PostMapping
	@ResponseBody
	public Object wordRecognition(@RequestParam("questionNumber") String[] questionNumber, 
			@RequestParam("questionMark") String[] questionMark,
			@RequestParam("image") MultipartFile file) {
		return wordRecognitionService.wordRecognition(file, questionNumber, questionMark);
	}
}
