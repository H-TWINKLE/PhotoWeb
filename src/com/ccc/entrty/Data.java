package com.ccc.entrty;

import com.alibaba.fastjson.JSONObject;

public class Data {

	private String document_id;

	private String class_id;

	private String subclass_id;

	private String title;

	private String author;

	private String username;

	private String published;

	private String picture_id;

	private String date;

	private String head_src;

	private String url;

	private String pic_url;

	private String content;

	private int comm_count;

	public String getDocument_id() {
		return document_id;
	}

	public void setDocument_id(String document_id) {
		this.document_id = document_id;
	}

	public String getClass_id() {
		return class_id;
	}

	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}

	public String getSubclass_id() {
		return subclass_id;
	}

	public void setSubclass_id(String subclass_id) {
		this.subclass_id = subclass_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPublished() {
		return published;
	}

	public void setPublished(String published) {
		this.published = published;
	}

	public String getPicture_id() {
		return picture_id;
	}

	public void setPicture_id(String picture_id) {
		this.picture_id = picture_id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHead_src() {
		return head_src;
	}

	public void setHead_src(String head_src) {
		this.head_src = head_src;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPic_url() {
		return pic_url;
	}

	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getComm_count() {
		return comm_count;
	}

	public void setComm_count(int comm_count) {
		this.comm_count = comm_count;
	}
	
	@Override
	public String toString() {
		// TODO 自动生成的方法存根
		return JSONObject.toJSONString(this);
	}

}
