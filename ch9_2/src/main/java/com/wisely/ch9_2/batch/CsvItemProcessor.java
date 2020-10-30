package com.wisely.ch9_2.batch;

import javax.validation.ValidationException;

import org.springframework.batch.item.validator.ValidatingItemProcessor;

import com.wisely.ch9_2.domain.Person;

public class CsvItemProcessor extends ValidatingItemProcessor<Person> {
	@Override
	public Person process(Person item) throws ValidationException {
		
		/** 9.2：1.需执行super.proces:(item)才会调用自定义校验器。 */
		super.process(item);
		
		/** 9.2：2.对数据做简单的处理，若民族为汉族，则数据转换成01，其余转换成02。 */
		if (item.getNation().equals("汉族")) {
			item.setNation("01");
		} else {
			item.setNation("02");
		}
		
		return item;
	}
	
}
