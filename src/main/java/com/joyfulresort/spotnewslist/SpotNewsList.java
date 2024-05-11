package com.joyfulresort.spotnewslist;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


	@Entity
	@Table(name = "spot_news_list")
	public class SpotNewsList implements java.io.Serializable {
		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "spot_news_id", updatable = false)
		private Integer spotNewsId;
		

		@NotNull(message="最新消息標題:請勿空白")
		@Column(name = "spot_news_title")
		private String spotNewsTitle;
	
		
		@NotNull(message="最新消息日期: 請勿空白")
		@Column(name ="spot_news_date")
		private Date spotNewsDate;
	
		
		@NotNull(message="最新消息摘要: 請勿空白")
		@Column(name = "spot_news_abstract")
		private String spotNewsAbstract;
		
	
		@NotNull(message="最新消息內容: 請勿空白")
		@Column(name = "spot_news_content")
		private String spotNewsContent;
	
		
		@NotNull(message="最新消息圖片: 請選擇")
		@Column(name = "spot_news_photo", columnDefinition = "longblob")
		private byte[] spotNewsPhoto;
	
		
		@Column(name = "spot_news_state",columnDefinition = "BOOLEAN DEFAULT TRUE")
		private Boolean spotNewsState;


		public Integer getSpotNewsId() {
			return spotNewsId;
		}


		public void setSpotNewsId(Integer spotNewsId) {
			this.spotNewsId = spotNewsId;
		}


		public String getSpotNewsTitle() {
			return spotNewsTitle;
		}


		public void setSpotNewsTitle(String spotNewsTitle) {
			this.spotNewsTitle = spotNewsTitle;
		}


		public Date getSpotNewsDate() {
			return spotNewsDate;
		}


		public void setSpotNewsDate(Date spotNewsDate) {
			this.spotNewsDate = spotNewsDate;
		}


		public String getSpotNewsAbstract() {
			return spotNewsAbstract;
		}


		public void setSpotNewsAbstract(String spotNewsAbstract) {
			this.spotNewsAbstract = spotNewsAbstract;
		}


		public String getSpotNewsContent() {
			return spotNewsContent;
		}


		public void setSpotNewsContent(String spotNewsContent) {
			this.spotNewsContent = spotNewsContent;
		}


		public byte[] getSpotNewsPhoto() {
			return spotNewsPhoto;
		}


		public void setSpotNewsPhoto(byte[] spotNewsPhoto) {
			this.spotNewsPhoto = spotNewsPhoto;
		}


		public Boolean getSpotNewsState() {
			return spotNewsState;
		}


		public void setSpotNewsState(Boolean spotNewsState) {
			this.spotNewsState = spotNewsState;
		}


		
		
		
}
