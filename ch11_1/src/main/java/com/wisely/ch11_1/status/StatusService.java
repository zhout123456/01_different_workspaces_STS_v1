package com.wisely.ch11_1.status;

import org.springframework.stereotype.Service;

/** 11.1：此类仅为改变status的值。 */
@Service
public class StatusService {
	
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
