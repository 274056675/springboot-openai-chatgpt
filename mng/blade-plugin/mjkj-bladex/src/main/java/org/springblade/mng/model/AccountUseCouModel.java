package org.springblade.mng.model;

import lombok.Data;

@Data
public class AccountUseCouModel {
	private String id;
	private String apiKey;
	private int cou;//使用次数
}
