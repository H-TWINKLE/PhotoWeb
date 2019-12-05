package com.ccc.entrty;

import java.util.List;

public class Parts {
	
	private int status;

	private List<Data> data ;

	public void setStatus(int status){
	this.status = status;
	}
	public int getStatus(){
	return this.status;
	}
	public void setData(List<Data> data){
	this.data = data;
	}
	public List<Data> getData(){
	return this.data;
	}	

}
