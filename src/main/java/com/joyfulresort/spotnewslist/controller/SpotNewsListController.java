package com.joyfulresort.spotnewslist.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.joyfulresort.spotnewslist.model.SpotNewsList;
import com.joyfulresort.spotnewslist.model.SpotNewsListService;

@Controller
@RequestMapping("/spotnewslist")
public class SpotNewsListController {
	
	@Autowired
	SpotNewsListService spotNewsListSvc;
	
	
	@GetMapping("getAll")
	public String getAll(ModelMap model) {
		List<SpotNewsList> spotNewsList = spotNewsListSvc.getAll();
		model.addAttribute("spotNewsList", spotNewsList);
		return "back-end/spotnewslist/listAllSpotNewsLists";
	}
	
	@GetMapping("addSpotNewsList")
		public String addSpotNewsList(ModelMap model) {
			SpotNewsList spotNewsList = new SpotNewsList();
			model.addAttribute("spotNewsList", spotNewsList);
			return "back-end/spotnewslist/addSpotNewsList";
		}
	
	
	@ModelAttribute("SpotNewsListData")
	protected List<SpotNewsList> referenceListData() {
		List<SpotNewsList> list = spotNewsListSvc.getAll();
		return list;
	}

	/*
	 * This method will be called on addEmp.html form submission, handling POST request It also validates the user input
	 */
	@PostMapping("insert")
	public String insert(@Valid SpotNewsList spotNewsList, BindingResult result, ModelMap model,
			@RequestParam("spotNewsPhoto") MultipartFile[] parts) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(spotNewsList, result, "spotNewsPhoto");

		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的圖片時
			model.addAttribute("errorMessage", "最新消息照片: 請上傳照片");
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] buf = multipartFile.getBytes();
				spotNewsList.setSpotNewsPhoto(buf);
			}
		}
		if (result.hasErrors() || parts[0].isEmpty()) {
			return "back-end/spotnewslist/addSpotNewsList";
		}
		/*************************** 2.開始新增資料 *****************************************/
		// RoomTypePhotoService roomTypePhotoSvc = new RoomTypePhotoService();
		spotNewsListSvc.addSpotNewsList(spotNewsList);
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		List<SpotNewsList> list = spotNewsListSvc.getAll();
		model.addAttribute("spotNewsList", list); //此為VO
		model.addAttribute("success", "- (新增成功)");
		return "redirect:/spotnewslist/getAll";  //新增成功後重導至IndexController_inSpringBoot.java的第58行@GetMapping("/emp/listAllEmp")
	
	}



	/*
	 * This method will be called on listAllRoomTypePhotos.html form submission, handling POST request
	 */
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("spotNewsId") String spotNewsId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		SpotNewsList spotNewsList = spotNewsListSvc.getOneSpotNewsList(Integer.valueOf(spotNewsId));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("spotNewsList", spotNewsList);
		return "back-end/spotnewslist/updateSpotNewsList"; // 查詢完成後轉交updateRoomTypePhoto.html
	}

	/*
	 * This method will be called on updateRoomTypePhoto.html form submission, handling POST request It also validates the user input
	 */
	@PostMapping("update")
	public String update(@Valid SpotNewsList spotNewsList, BindingResult result, ModelMap model,
			@RequestParam("spotNewsPhoto") MultipartFile[] parts) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(spotNewsList, result, "spotNewsPhoto");

		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的新圖片時
			// RoomTypePhotoService roomTypePhotoSvc = new RoomTypePhotoService();
			byte[] spotNewsPhoto = spotNewsListSvc.getOneSpotNewsList(spotNewsList.getSpotNewsId()).getSpotNewsPhoto();
			spotNewsList.setSpotNewsPhoto(spotNewsPhoto);
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] spotNewsPhoto = multipartFile.getBytes();
				spotNewsList.setSpotNewsPhoto(spotNewsPhoto);
			}
		}
		if (result.hasErrors()) {
			return "back-end/spotnewslist/updateSpotNewsList";
		}
		/*************************** 2.開始修改資料 *****************************************/
		// RoomTypePhotoService roomTypePhotoSvc = new RoomTypePhotoService();
		spotNewsListSvc.updateSpotNewsList(spotNewsList);

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		spotNewsList = spotNewsListSvc.getOneSpotNewsList(Integer.valueOf(spotNewsList.getSpotNewsId()));
		model.addAttribute("spotNewsList", spotNewsList);
		return "back-end/spotnewslist/listAllSpotNewsLists"; // 修改成功後轉交listOneRoomTypePhoto.html
	}
	
	
	// 去除BindingResult中某個欄位的FieldError紀錄
	public BindingResult removeFieldError(SpotNewsList spotNewsList, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
				.collect(Collectors.toList());
		result = new BeanPropertyBindingResult(spotNewsList, "spotNewsList");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}
}



