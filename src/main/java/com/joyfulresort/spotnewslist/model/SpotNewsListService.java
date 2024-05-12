package com.joyfulresort.spotnewslist.model;
import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("spotNewsListService")
public class SpotNewsListService {

		@Autowired
		SpotNewsListRepository repository;
		
		@Autowired
	    private SessionFactory sessionFactory;
		
		public SpotNewsListService(SpotNewsListRepository repository) {
			this.repository = repository;
		}
		
		public SpotNewsList addSpotNewsList(SpotNewsList spotNewsList) {
			repository.save(spotNewsList);
			return spotNewsList;
		}
		

		public SpotNewsList updateSpotNewsList(SpotNewsList spotNewsList) {
			repository.save(spotNewsList);
			return spotNewsList;
		}
		
		public List<SpotNewsList> getAll() {
			return repository.findAll();
		}
		
		public SpotNewsList getOneSpotNewsList(Integer spotNewsListId) {
			Optional<SpotNewsList> optional = repository.findById(spotNewsListId);
//			return optional.get();
			return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
		}
	}

