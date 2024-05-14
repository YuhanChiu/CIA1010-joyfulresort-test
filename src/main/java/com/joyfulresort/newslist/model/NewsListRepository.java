package com.joyfulresort.newslist.model;

import org.springframework.data.jpa.repository.JpaRepository;


public interface NewsListRepository extends JpaRepository<NewsList, Integer> {

}
