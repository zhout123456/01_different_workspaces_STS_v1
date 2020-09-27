package com.wisely.ch8_2_M2.entity;

public class bnUser {
	private String userId;
	private String userPhone;
	private String userName;
	private double hisMoney;
	
	
	public String getUserId() {
		return userId;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public String getUserName() {
		return userName;
	}
	public double getHisMoney() {
		return hisMoney;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setHisMoney(double hisMoney) {
		this.hisMoney = hisMoney;
	}
	
	@Override
	public String toString() {
 
		return "{userId:"+userId+",userPhone:"+userPhone+",userName:"+userName+",hisMoney:"+hisMoney+"}";
	}

}
