package com.lms.service;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lms.entity.WordLocation;
import com.lms.utils.DocAnalysis;

@Service
public class WordRecognitionService {
	/**
	 * @param multipartFile 图像文件
	 * @param numbers 题号
	 * @param marks 标准分
	 * @return readImage(file.getAbsolutePath(), questionNumber, questionMark)的结果
	 */
	public List<String> wordRecognition(MultipartFile multipartFile, String[] numbers, String[] marks){
		File file = null;
		try {
			String fileName = multipartFile.getOriginalFilename();
			String prefix = fileName.substring(fileName.lastIndexOf("."));
			file = File.createTempFile("wordRecognitionTempImage", prefix);
			multipartFile.transferTo(file);
			ArrayList<String> questionNumber = new ArrayList<String>();
			ArrayList<String> questionMark = new ArrayList<String>();
			//numbers内容应为下示
			//numbers = {"一","二","三","四","五","六","七","八","九","总分"};
			for(int i = 0;i<numbers.length;i++) {
				questionNumber.add(numbers[i]);
			}
			//marks内容应为下示
			//marks = {"20","15","10","12","6","6","6","4","21","100"};
			for(int i = 0;i<marks.length;i++) {
				questionMark.add(marks[i]);
			}
			  			
			return readImage(file.getAbsolutePath(), questionNumber, questionMark);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		} finally {
		     // 操作完上面的文件 需要删除在根目录下生成的临时文件
		     File f = new File(file.toURI());
		     f.delete();
		}
	} 
	
	/**
	 * @param file 图像文件
	 * @return String形式的文字识别结果
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public String runPythonCode(String file) throws IOException, InterruptedException {
		String exe = "python";
		String command = "src\\main\\python\\infer.py";
		String[] cmdArr = new String[] {exe, command, file};
		Process process = Runtime.getRuntime().exec(cmdArr);
		InputStream inputStream = process.getInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		String result = bufferedReader.readLine();
//		process.waitFor();
		return result;
	}
	
	/**
	 * @param file 图像文件
	 * @param name 图像名
	 * @param location 图像位置信息
 	 * @return 图像名
	 */
	public String cutImage(String file, String name, WordLocation location) {
		File sourcePic = new File(file);
		try {
			BufferedImage pic1 = ImageIO.read(sourcePic);
                        //参数依次为，截取起点的x坐标，y坐标，截取宽度，截取高度
			BufferedImage pic2 = pic1.getSubimage(location.getLeft(), location.getTop(), location.getWidth(), location.getHeight());
                        //将截取的子图另行存储
			File desImage = new File(name);
			ImageIO.write(pic2, "png", desImage);
			return desImage.toString();
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}
	}
	
	//根据输入的图片、题号数组和标准分数组，使用cutImage函数对图片进行截取并利用python代码进行识别
	/**
	 * @param file 图像文件
	 * @param questionNumber 题号
	 * @param standardMark 标准分
	 * @return String列表List形式的文字识别结果
	 */
	public List<String> readImage(String file, ArrayList<String> questionNumber, ArrayList<String> standardMark) {
		ArrayList<String> resultList = new ArrayList<String>();
		ArrayList<WordLocation> targetLocations = new ArrayList<WordLocation>();
		ArrayList<WordLocation> questionNumberLocations = new ArrayList<WordLocation>();
		ArrayList<WordLocation> standardMarkLocations = new ArrayList<WordLocation>();
		WordLocation wordStandardMark = null;//记录word为“标准分”格的位置
		WordLocation wordMark = null;//记录word为“得分”格的位置
		final int heightDifference = 40;//两格间字符的最大高度差
		final int larger = 30;//定位后向外扩大的量
		
		DocAnalysis docAnalysis = new DocAnalysis(); 
		JSONObject jsonObject = new JSONObject(docAnalysis.docAnalysis(file));
				
		JSONArray jsonArray = jsonObject.getJSONArray("results");
		for(int i = 0, j = 0, k = 0;i<jsonArray.length();i++) {//j用于遍历questionNumber，k用于遍历standardMark
			JSONObject wordsJson = jsonArray.getJSONObject(i).getJSONObject("words");
			
//			System.out.println("j = " + j + ", k = " + k);
			
			if(j < questionNumber.size() && wordsJson.getString("word").equals(questionNumber.get(j))) {
				JSONObject wordsLocationJson = wordsJson.getJSONObject("words_location");
				WordLocation tempWordLocation = new WordLocation(wordsLocationJson.getInt("top"), 
						wordsLocationJson.getInt("left"), 
						wordsLocationJson.getInt("width"), 
						wordsLocationJson.getInt("height"));
				questionNumberLocations.add(tempWordLocation);
				j++;
			}
			
			if(k < standardMark.size() && wordsJson.getString("word").equals(standardMark.get(k))) {
				JSONObject wordsLocationJson = wordsJson.getJSONObject("words_location");
				WordLocation tempWordLocation = new WordLocation(wordsLocationJson.getInt("top"), 
						wordsLocationJson.getInt("left"), 
						wordsLocationJson.getInt("width"), 
						wordsLocationJson.getInt("height"));
				standardMarkLocations.add(tempWordLocation);
				k++;
			}
			
			if(wordsJson.getString("word").equals("标准分")) {
				JSONObject wordsLocationJson = wordsJson.getJSONObject("words_location");
				wordStandardMark = new WordLocation(wordsLocationJson.getInt("top"), 
						wordsLocationJson.getInt("left"), 
						wordsLocationJson.getInt("width"), 
						wordsLocationJson.getInt("height"));
			}
			
			if(wordsJson.getString("word").equals("得分")) {
				JSONObject wordsLocationJson = wordsJson.getJSONObject("words_location");
				wordMark = new WordLocation(wordsLocationJson.getInt("top"), 
						wordsLocationJson.getInt("left"), 
						wordsLocationJson.getInt("width"), 
						wordsLocationJson.getInt("height"));
			}
			
			//已完成对json的遍历时
			if(i == jsonArray.length() - 1) {
				//遍历后未找到questionNumber.get(j)对应的位置
				if(j <= questionNumber.size() - 1) {
					i = 0;
					j++;
					questionNumberLocations.add(null);
				}
				//遍历后未找到standardMark.size(k)对应的位置
				if(k <= standardMark.size() - 1) {
					i = 0;
					k++;
					standardMarkLocations.add(null);
				}
			}
		}
		
		for(int i = 0;i<questionNumber.size();i++) {
			if(questionNumberLocations.get(i) != null && standardMarkLocations.get(i) != null) {
				WordLocation tempLocation = new WordLocation(0, 0, 0, 0);
				tempLocation.setTop(wordMark.getTop() - larger);
				tempLocation.setLeft(standardMarkLocations.get(i).getLeft() - 
						(standardMarkLocations.get(i).getLeft() - questionNumberLocations.get(i).getLeft()) - larger);
				tempLocation.setHeight(questionNumberLocations.get(i).getHeight() + larger * 4);
				tempLocation.setWidth(questionNumberLocations.get(i).getWidth() + larger * 2);
				targetLocations.add(tempLocation);
			}
			else if(questionNumberLocations.get(i) != null && standardMarkLocations.get(i) == null) {
				WordLocation tempLocation = new WordLocation(0, 0, 0, 0);
				tempLocation.setTop(wordMark.getTop() - larger);
				tempLocation.setLeft(questionNumberLocations.get(i).getLeft() - larger);
				tempLocation.setHeight(questionNumberLocations.get(i).getHeight() + larger * 4);
				tempLocation.setWidth(questionNumberLocations.get(i).getWidth() + larger * 2);
				targetLocations.add(tempLocation);
			}
			else if(questionNumberLocations.get(i) == null && standardMarkLocations.get(i) != null) {
				WordLocation tempLocation = new WordLocation(0, 0, 0, 0);
				tempLocation.setTop(wordMark.getTop() - larger);
				tempLocation.setLeft(standardMarkLocations.get(i).getLeft() - larger);
				tempLocation.setHeight(standardMarkLocations.get(i).getHeight() + larger * 4);
				tempLocation.setWidth(standardMarkLocations.get(i).getWidth() + larger * 2);
				targetLocations.add(tempLocation);
			}
			else {
				targetLocations.add(null);
			}
		}
		
		for(int i = 0;i<targetLocations.size();i++) {
			if(targetLocations.get(i) != null) {
				if(i > 0 && targetLocations.get(i - 1) == null) {
					WordLocation tempLocation = new WordLocation(0, 0, 0, 0);
					tempLocation.setTop(targetLocations.get(i).getTop());
					tempLocation.setLeft(targetLocations.get(i).getLeft() - targetLocations.get(i).getWidth() - larger);
					tempLocation.setHeight(targetLocations.get(i).getHeight());
					tempLocation.setWidth(targetLocations.get(i).getWidth());
					targetLocations.set(i - 1, tempLocation);
					i -= 2;
					continue;
				}else if(i < targetLocations.size() - 1 && targetLocations.get(i + 1) == null) {
					WordLocation tempLocation = new WordLocation(0, 0, 0, 0);
					tempLocation.setTop(targetLocations.get(i).getTop());
					tempLocation.setLeft(targetLocations.get(i).getLeft() + targetLocations.get(i).getWidth() + larger);
					tempLocation.setHeight(targetLocations.get(i).getHeight());
					tempLocation.setWidth(targetLocations.get(i).getWidth());
					targetLocations.set(i + 1, tempLocation);
					continue;
				}
			}
		}
		
		for(int i = 0;i<targetLocations.size();i++) {
//			targetLocations.get(i).show();
			String name = "temp.png";
			cutImage(file, name, targetLocations.get(i));
			try {
//				System.out.println(System.currentTimeMillis() + ":before python");
				resultList.add(runPythonCode(name));
//				System.out.println(System.currentTimeMillis() + ":after python");
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} catch (InterruptedException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		return resultList;
	}
}
