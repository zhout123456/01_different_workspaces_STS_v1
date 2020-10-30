package com.wisely.ch11_1.healthindicator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.wisely.ch11_1.status.StatusService;

/** 11.1：1.实现HealthIndicator接口并重写health()方法。 */
@Component
public class StatusHealth implements HealthIndicator {
	
	@Autowired
	StatusService statusService;
	
	@Override
	public Health health() {
		String status = statusService.getStatus();
		if (status == null || !status.equals("running")) {
			/** 11.1：2.当status的值为非running时构造失败。 */
			return Health.down().withDetail("Error", "Not Running").build();
		}
		
		/** 11.1：3.其余情况运行成功。 */
		return Health.up().build();
	}

}
