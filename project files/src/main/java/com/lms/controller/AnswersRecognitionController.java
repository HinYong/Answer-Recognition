package com.lms.controller;

import com.lms.service.AnswersRecognitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.lms.service.MultipartFileToFile;

@CrossOrigin
@RestController
@RequestMapping("/answersRecognition")
public class AnswersRecognitionController {
    @Autowired
    private AnswersRecognitionService answersRecognitionService;
    @Autowired
    private MultipartFileToFile tofile;
    @PostMapping()
    @ResponseBody
    public void answerRecognition(@RequestParam("zipfile") MultipartFile zipfile, @RequestParam(value = "id") int courseId, @RequestParam(value = "answer1") String answer1, @RequestParam(value = "answer2") String answer2) throws Exception{
        // System.out.println(courseid+"  "+answer1+"  "+answer2);
        answersRecognitionService.UnZip(tofile.multipartFileToFile(zipfile),answer1,answer2,courseId);

    }
}
