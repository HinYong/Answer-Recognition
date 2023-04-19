package com.lms.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lms.entity.Assignment;
import com.lms.entity.Message;
import com.lms.service.AssignmentService;

@CrossOrigin
@RestController
@RequestMapping("/assignment")
public class AssignmentController {

	@Autowired
	private AssignmentService assignmentService;
	
	/**
	 * @param course 作业所属的课程ID
	 * @return assignmentService.addAssignment(course)的结果
	 */
	@PutMapping
	@ResponseBody
	public Object addAssignment(@RequestParam(value = "id") int course) {
		return assignmentService.addAssignment(course);
	}
	
	
	/**
	 * @param assignmentId 作业的ID
	 * @return assignmentService.deleteAssignment(assignmentId)的结果
	 */
	@DeleteMapping
	@ResponseBody
	public Object deleteAssignment(@RequestParam(value = "assignmentId") int assignmentId) {
		return assignmentService.deleteAssignment(assignmentId);
	}
	
	/**
	 * @param assignmentId 作业的ID
	 * @param assignmentName 作业的名字
	 * @param weight 作业的权重
	 * @param startTimeString 作业开始时间的字符串
	 * @param endTimeString 作业结束时间的字符串
	 * @return 创建的Message对象
	 * @throws ParseException
	 */
	@PostMapping
	@ResponseBody
	public Object updateAssignment(@RequestParam(value = "assignmentId") int assignmentId,
			@RequestParam(value = "assignmentName") String assignmentName,
			@RequestParam(value = "weight") float weight,
			@RequestParam(value = "startTime") String startTimeString,
			@RequestParam(value = "endTime") String endTimeString) throws ParseException {
		Assignment assignment = assignmentService.getAssignment(assignmentId);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		assignment.setAssignmentName(assignmentName);
		assignment.setWeight(weight);
		assignment.setStartTime(dateFormat.parse(startTimeString));
		assignment.setEndTime(dateFormat.parse(endTimeString));
		
		if(assignmentService.updateAssignment(assignmentId, assignment)) {
			return new Message(true, "保存成功");
		} else {
			return new Message(false, "保存失败，请检查信息内容");
		}
	}
	
	/**
	 * @param assignmentId 作业的ID
	 * @return assignmentService.getAssignment(assignmentId)的结果
	 */
	@GetMapping("/get")
	@ResponseBody
	public Object getAssignment(@RequestParam(value = "id") int assignmentId) {
		return assignmentService.getAssignment(assignmentId);
	}
	
	/**
	 * @param course 作业所属课程的ID
	 * @return assignmentService.getAssignmentList(course)的结果
	 */
	@GetMapping("/list")
	@ResponseBody
	public Object getAssignmentList(@RequestParam(value = "id") int course) {
		return assignmentService.getAssignmentList(course);
	}
}
