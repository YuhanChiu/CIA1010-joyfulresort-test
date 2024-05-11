package com.joyfulresort.room.model;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyfulresort.roomtype.model.RoomType;
import com.joyfulresort.roomtype.model.RoomTypeRepository;


@Service("roomService")
public class RoomService {
	
	@Autowired
	private RoomRepository repository;

	@Autowired
	private RoomTypeRepository roomTypeRepository;
	
	@Autowired
    private SessionFactory sessionFactory;

	
	public RoomService(RoomRepository repository) {
		this.repository = repository;
	}

	public Room addRoom(Room room) {
		repository.save(room);
		return room;
	}
	

	public Room updateRoom(Room room) {
		repository.save(room);
		return room;
	}
	
	

//	public void deleteRoom(Integer room) {
//		if (repository.existsById(room))
//			repository.deleteByEmpno(room);
//		    repository.deleteById(room);
//	}

	public List<Room> getAll() {
		return repository.findAll();
	}

	public Room getOneRoom(Integer roomId) {
		Optional<Room> optional = repository.findById(roomId);
//		return optional.get();
		return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
		
//	public List<Room> getAll(Map<String, String[]> map) {
//		return HibernateUtil_CompositeQuery_Room.getAllC(map,sessionFactory.openSession()); 
//		//**重要!!關鍵行
//	}
	}
}

