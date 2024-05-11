package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.joyfulresort.room.model.Room;
import com.joyfulresort.room.model.RoomService;
//import com.dept.model.DeptService;
//import com.dept.model.DeptVO;
import com.joyfulresort.roomtype.model.RoomType;
import com.joyfulresort.roomtype.model.RoomTypeService;

import java.util.*;



//@PropertySource("classpath:application.properties") // 於https://start.spring.io建立Spring Boot專案時, application.properties文件預設已經放在我們的src/main/resources 目錄中，它會被自動檢測到
@Controller
public class IndexController_inSpringBoot {
	
	// @Autowired (●自動裝配)(Spring ORM 課程)
	// 目前自動裝配了EmpService --> 供第66使用
	@Autowired
	RoomTypeService roomTypeSvc;
	
	@Autowired
	RoomService roomSvc;
	
    // inject(注入資料) via application.properties
    @Value("${welcome.message}")
    private String message;
	
    private List<String> myList = Arrays.asList("Spring Boot Quickstart 官網 : https://start.spring.io", "IDE 開發工具", "直接使用(匯入)官方的 Maven Spring-Boot-demo Project + pom.xml", "直接使用官方現成的 @SpringBootApplication + SpringBootServletInitializer 組態檔", "依賴注入(DI) HikariDataSource (官方建議的連線池)", "Thymeleaf", "Java WebApp (<font color=red>快速完成 Spring Boot Web MVC</font>)");
    @GetMapping("/")
    public String index(Model model) {
    	model.addAttribute("message", message);
        model.addAttribute("myList", myList);
        return "index"; //view
    }
    
//     http://......../hello?name=peter1
    @GetMapping("/hello")
    public String indexWithParam(
            @RequestParam(name = "name", required = false, defaultValue = "") String name, Model model) {
        model.addAttribute("message", name);
        return "index"; //view
    }

  
    //=========== 以下第63~75行是提供給 /src/main/resources/templates/back-end/emp/select_page.html 與 listAllEmp.html 要使用的資料 ===================   
    @GetMapping("/main_page")
	public String main_page(Model model) {
		return "back-end/main_page";
	}
    @GetMapping("/test")
  	public String test(Model model) {
  		return "front-end/test";
  	}
    
    @GetMapping("/roomtype/roomtypeselect")
	public String roomTypeSelect(Model model) {
		return "back-end/roomtype/roomtypeselect";
	}
    
//    @GetMapping("/roomtype/addRoomType")
//   	public String addRoomType(Model model) {
//   		return "back-end/roomtype/addRoomType";
//   	}
    
    
    @GetMapping("/roomtype/listAllRoomTypes")
   	public String listAllRoomTypes(Model model) {
   		return "back-end/roomtype/listAllRoomTypes";
   	}
    
    @GetMapping("/roomtype/listCompositeQueryRoomTypes")
   	public String listCompositeQueryRoomTypes(Model model) {
   		return "back-end/roomtype/listCompositeQueryRoomTypes";
   	}
    
    @GetMapping("/roomtype/listOneRoomType")
   	public String listOneRoomType(Model model) {
   		return "back-end/roomtype/listOneRoomType";
   	}
    
    @GetMapping("/roomtype/updateRoomType")
   	public String updateRoomType(Model model) {
   		return "back-end/roomtype/updateRoomType";
   	}
    
    @ModelAttribute("roomTypeListData") 
    protected List<RoomType> referenceListData_RoomType(Model model) {
     model.addAttribute("roomType", new RoomType()); 
     System.out.println("123");
     List<RoomType> list = roomTypeSvc.getAll();
     System.out.println(list);
     return list;
    }
    
    @ModelAttribute("roomType")  // for select_page.html 第97 109行用 // for listAllEmp.html 第85行用
	protected List<RoomType> referenceroomTypeListData(Model model) {	
    	List<RoomType> list = roomTypeSvc.getAll();
		return list;
	}
    
    @GetMapping("/room/roomselect")
   	public String roomSelect(Model model) {
   		return "back-end/room/roomselect";
   	}
       
//       @GetMapping("/room/addRoom")
//      	public String addRoom(Model model) {
//      		return "back-end/room/addRoom";
//      	}
       
       
       @GetMapping("/room/listAllRooms")
      	public String listAllRooms(Model model) {
      		return "back-end/room/listAllRooms";
      	}
       
       @GetMapping("/room/listCompositeQueryRooms")
      	public String listCompositeQueryRooms(Model model) {
      		return "back-end/room/listCompositeQueryRooms";
      	}
       
       @GetMapping("/room/listOneRoom")
      	public String listOneRoom(Model model) {
      		return "back-end/room/listOneRoom";
      	}
       
       @GetMapping("/room/updateRoom")
      	public String updateRoomT(Model model) {
      		return "back-end/room/updateRoom";
      	}
       
       @ModelAttribute("room")  // for select_page.html 第97 109行用 // for listAllEmp.html 第85行用
   		protected List<Room> referenceroomListData(Model model) {	
       	List<Room> list = roomSvc.getAll();
   		return list;
   		}
       
       @GetMapping("/roomtypephoto/roomtypephotoselect")
   		public String roomTypePhotoSelect(Model model) {
   		return "back-end/roomtypephoto/roomtypephotoselect";		
       }
       
//     @GetMapping("/roomtypephoto/addRoomTypePhoto")
//    	public String addRoomTypePhoto(Model model) {
//    		return "back-end/roomtypephoto/addRoomTypePhoto";
//    	}
       
       @GetMapping("/roomtypephoto/listAllRoomPhotos")
     	public String listAllRoomPhotos(Model model) {
     	return "back-end/roomtypephoto/listAllRoomTypePhotos";
     	}
       
       @GetMapping("/roomtypephoto/updateRoomTypePhoto")
      	public String updateRoomTypePhoto(Model model) {
      	return "back-end/roomtypephoto/updateRoomTypePhoto";
      	}
       
       
       
	@ModelAttribute("roomTypeListData") // for select_page.html 第135行用
	protected List<RoomType> referenceListData_RoomType1(Model model) {
		model.addAttribute("roomType", new RoomType()); // for select_page.html 第133行用
		List<RoomType> list = roomTypeSvc.getAll();
		return list;
	}
}
