package com.joyfulresort.spotnewslist.controller;

import javax.servlet.http.HttpServletRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import com.joyfulresort.spotnewslist.model.SpotNewsList;
import com.joyfulresort.spotnewslist.model.SpotNewsListService;



@Controller
@Validated
@RequestMapping("/spotnewslist")
public class SpotNewsListIdController {
	
	@Autowired
	SpotNewsListService spotNewsListSvc;
	

	/*
	 * This method will be called on select_page.html form submission, handling POST
	 * request It also validates the user input
	 */
	@PostMapping("getOne_For_Display")
	public String getOne_For_Display(
		/***************************1.接收請求參數 - 輸入格式的錯誤處理*************************/
		@NotEmpty(message="最新消息編號: 請勿空白")
		@Digits(integer = 4, fraction = 0, message = "最新消息: 請填數字-請勿超過{integer}位數")
		@Min(value = 1, message = "最新消息編號: 不能小於{value}")
		@Max(value = 100, message = "最新消息編號: 不能超過{value}")
		@RequestParam("spotNewsListId") String spotNewsListId,
		ModelMap model) {
		
		/***************************2.開始查詢資料*********************************************/
//		EmpService empSvc = new EmpService();
		SpotNewsList spotNewsList = spotNewsListSvc.getOneSpotNewsList(Integer.valueOf(spotNewsListId));
		
		List<SpotNewsList> list = spotNewsListSvc.getAll();
		model.addAttribute("spotNewsListListData", list);     // for select_page.html 第97 109行用
	
		
		if (spotNewsList == null) {
			model.addAttribute("errorMessage", "查無資料");
			return "back-end/spotnewslist/spotnewslistselect";
		}
		
		/***************************3.查詢完成,準備轉交(Send the Success view)*****************/
		model.addAttribute("spotNewsList", spotNewsList);
		model.addAttribute("getOne_For_Display", "true"); // 旗標getOne_For_Display見select_page.html的第156行 -->
		
		return "back-end/spotnewslist/listOneSpotNewsList";  // 查詢完成後轉交listOneEmp.html
//		return "back-end/emp/select_page"; // 查詢完成後轉交select_page.html由其第158行insert listOneEmp.html內的th:fragment="listOneEmp-div
	}

	
//	@ExceptionHandler(value = { ConstraintViolationException.class })
//	//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
//	public ModelAndView handleError(HttpServletRequest req,ConstraintViolationException e,Model model) {
//	    Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
//	    StringBuilder strBuilder = new StringBuilder();
//	    for (ConstraintViolation<?> violation : violations ) {
//	          strBuilder.append(violation.getMessage() + "<br>");
//	    }
//	    //==== 以下第92~96行是當前面第77行返回 /src/main/resources/templates/back-end/emp/select_page.html用的 ====   
//	    model.addAttribute("empVO", new EmpVO());
//    	EmpService empSvc = new EmpService();
//		List<SpotNewsList> list = spotNewsListSvc.getAll();
//		model.addAttribute("spotNewsListSvcListData", list);     // for select_page.html 第97 109行用
//		model.addAttribute("deptVO", new DeptVO());  // for select_page.html 第133行用
//		List<DeptVO> list2 = deptSvc.getAll();
//    	model.addAttribute("deptListData",list2);    // for select_page.html 第135行用
//		String message = strBuilder.toString();
//	    return new ModelAndView("back-end/emp/select_page", "errorMessage", "請修正以下錯誤:<br>"+message);
//	}
	
}