package org.springblade.entity;

import lombok.Data;

@Data
public class QuestionNumParam {
	Long bladeUsrId;//用户id
	String wxuserId;
	Integer serviceType;
	Integer num;
}
