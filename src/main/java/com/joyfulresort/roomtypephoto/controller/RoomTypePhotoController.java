package com.joyfulresort.roomtypephoto.controller;

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


import com.joyfulresort.roomtype.model.RoomType;
import com.joyfulresort.roomtype.model.RoomTypeService;
import com.joyfulresort.roomtypephoto.model.RoomTypePhoto;
import com.joyfulresort.roomtypephoto.model.RoomTypePhotoService;

@Controller
@RequestMapping("/roomtypephoto")
public class RoomTypePhotoController {
		
	@Autowired
	RoomTypePhotoService roomTypePhotoSvc;
	
	@Autowired
	RoomTypeService roomTypeSvc;
	
	
	
	@GetMapping("getAll")
	public String getAll(ModelMap model) {
		List<RoomTypePhoto> roomTypePhoto = roomTypePhotoSvc.getAll();
		model.addAttribute("roomTypePhoto", roomTypePhoto);
		return "back-end/roomtypephoto/listAllRoomTypePhotos";
	}
	
	@GetMapping("addRoomTypePhoto")
	public String addRoomTypePhoto(ModelMap model) {
		RoomTypePhoto roomTypePhoto = new RoomTypePhoto();
	    List<RoomType> roomTypeListData = roomTypeSvc.getAll();  // 獲取所有房型資料
	    model.addAttribute("roomTypeListData", roomTypeListData);
	    model.addAttribute("roomTypePhoto", roomTypePhoto);
		return "back-end/roomtypephoto/addRoomTypePhoto";
	}
	
	@ModelAttribute("roomTypeListData")
	protected List<RoomType> referenceListData() {
		List<RoomType> list = roomTypeSvc.getAll();
		return list;
	}

	/*
	 * This method will be called on addEmp.html form submission, handling POST request It also validates the user input
	 */
	@PostMapping("insert")
	public String insert(@Valid RoomTypePhoto roomTypePhoto, BindingResult result, ModelMap model,
			@RequestParam("roomTypePhoto") MultipartFile[] parts) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(roomTypePhoto, result, "roomTypePhoto");

		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的圖片時
			model.addAttribute("errorMessage", "房型照片: 請上傳照片");
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] buf = multipartFile.getBytes();
				roomTypePhoto.setRoomTypePhoto(buf);
			}
		}
		if (result.hasErrors() || parts[0].isEmpty()) {
			return "back-end/roomtypephoto/addRoomTypePhoto";
		}
		/*************************** 2.開始新增資料 *****************************************/
		// RoomTypePhotoService roomTypePhotoSvc = new RoomTypePhotoService();
		roomTypePhotoSvc.addRoomTypePhoto(roomTypePhoto);
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		List<RoomTypePhoto> list = roomTypePhotoSvc.getAll();
		model.addAttribute("roomTypePhoto", list);
		model.addAttribute("success", "- (新增成功)");
		return "redirect:/roomtypephoto/getAll";  //新增成功後重導至IndexController_inSpringBoot.java的第58行@GetMapping("/emp/listAllEmp")
	
	}



	/*
	 * This method will be called on listAllRoomTypePhotos.html form submission, handling POST request
	 */
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("roomTypePhotoId") String roomTypePhotoId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		RoomTypePhoto roomTypePhoto = roomTypePhotoSvc.getOneRoomTypePhoto(Integer.valueOf(roomTypePhotoId));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("roomTypePhoto", roomTypePhoto);
		return "back-end/roomtypephoto/updateRoomTypePhoto"; // 查詢完成後轉交updateRoomTypePhoto.html
	}

	/*
	 * This method will be called on updateRoomTypePhoto.html form submission, handling POST request It also validates the user input
	 */
	@PostMapping("update")
	public String update(@Valid RoomTypePhoto roomTypePhoto, BindingResult result, ModelMap model,
			@RequestParam("roomTypePhoto") MultipartFile[] parts) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(roomTypePhoto, result, "roomTypePhoto");

		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的新圖片時
			// RoomTypePhotoService roomTypePhotoSvc = new RoomTypePhotoService();
			byte[] roomTypePhoto1 = roomTypePhotoSvc.getOneRoomTypePhoto(roomTypePhoto.getRoomTypePhotoId()).getRoomTypePhoto();
			roomTypePhoto.setRoomTypePhoto(roomTypePhoto1);
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] roomTypePhoto1 = multipartFile.getBytes();
				roomTypePhoto.setRoomTypePhoto(roomTypePhoto1);
			}
		}
		if (result.hasErrors()) {
			return "back-end/roomtypephoto/updateRoomTypePhoto";
		}
		/*************************** 2.開始修改資料 *****************************************/
		// RoomTypePhotoService roomTypePhotoSvc = new RoomTypePhotoService();
		roomTypePhotoSvc.updateRoomTypePhoto(roomTypePhoto);

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		roomTypePhoto = roomTypePhotoSvc.getOneRoomTypePhoto(Integer.valueOf(roomTypePhoto.getRoomTypePhotoId()));
		model.addAttribute("roomTypePhoto", roomTypePhoto);
		return "back-end/roomtypephoto/listAllRoomTypePhotos"; // 修改成功後轉交listOneRoomTypePhoto.html
	}
	
	
	// 去除BindingResult中某個欄位的FieldError紀錄
	public BindingResult removeFieldError(RoomTypePhoto roomTypePhoto, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
				.collect(Collectors.toList());
		result = new BeanPropertyBindingResult(roomTypePhoto, "roomTypePhoto");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}
}
