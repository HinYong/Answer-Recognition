package com.lms.service;

import com.lms.impl.ExamDAOImpl;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.File;
import java.util.*;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AnswersRecognitionService {
    @Autowired
    private ExamDAOImpl examDAOImpl;
    //解压路径
    private String dest = "C:/Answers/";
    private String destDir;

    public void UnZip(File tofile, String answer1, String answer2,int courseId) {
        List<String> picPaths = new ArrayList<>();
        try {
            ZipFile zFile = new ZipFile(tofile);  // 首先创建ZipFile指向磁盘上的.zip文件

            String filename = tofile.getName();
            String caselsh = filename.substring(0,filename.lastIndexOf("."));
            destDir=dest+caselsh+"/";
            // System.out.println(destDir);

            zFile.setFileNameCharset("GBK");

            File destDir = new File(dest);  // 解压目录
            zFile.extractAll(dest);  // 将文件抽出到解压目录(解压)

            List<net.lingala.zip4j.model.FileHeader> headerList = zFile.getFileHeaders();
            List<File> extractedFileList = new ArrayList<File>();
            for (FileHeader fileHeader : headerList) {
                if (!fileHeader.isDirectory()) {
                    extractedFileList.add(new File(destDir, fileHeader.getFileName()));
                }
            }
            File[] extractedFiles = new File[extractedFileList.size()];
            extractedFileList.toArray(extractedFiles);

            for (File f : extractedFileList) {
                System.out.println(f.getAbsolutePath() + "....");
            }

            // 创建文件夹用于存放处理过的图片
            File file=new File("C:/Answers/processed_answers");
            if(!file.exists()){  //如果文件夹不存在
                file.mkdir();  //创建文件夹
            }

            // System.out.println(courseId+"  "+answer1+"  "+answer2);
            runPythonCode(courseId,answer1,answer2);

        } catch (ZipException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runPythonCode(int courseId,String answer1,String answer2) throws IOException, InterruptedException {
        String[] args = new String[] {"python", "src\\main\\python\\loop_detection.py", destDir, answer1, answer2};
        Process pr = Runtime.getRuntime().exec(args);
        BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream(), "GBK"));
        String line;
        while ((line = in.readLine()) != null) {
            if(line.charAt(0)>=48&&line.charAt(0)<=57){
                System.out.println(line);
                String studentNumber=line.substring(0,9);
                String str1=line.substring(0, line.indexOf(":"));
                String gradestring=line.substring(str1.length()+2);
                double grade=Double.valueOf(gradestring);
                // System.out.println(studentNumber+"  "+courseId+"  "+grade);
                examDAOImpl.updategrade(studentNumber,courseId,grade);
            }
        }
        in.close();
        pr.waitFor();
    }

    @Test
    public void testrunPythonCode() throws IOException, InterruptedException {
        // python src\main\python\loop_detection.py C:/Users/DELL/Desktop/code/rect_answer/ BACBDACDBC TFTTF
        // 创建文件夹用于存放处理过的图片
        File file=new File("C:/Answers/processed_answers");
        if(!file.exists()){  //如果文件夹不存在
            file.mkdir();  //创建文件夹
        }
        String answer1 = "BACBDACDBC";
        String answer2 = "TFTTF";
        Process pr = Runtime.getRuntime().exec("cmd /k start dir src\\main\\python\\loop_detection.py");  // 测试代码
        BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream(), "GBK"));
        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
        in.close();
        pr.waitFor();
    }

}
