package org.springblade.mng.model;

import lombok.Data;

@Data
public class FlagstudioTokenR {
	Integer code;
	dataModel data;
	@Data
	public static class dataModel{
		String token;
	}
}
