package org.springblade.mng.model;

import lombok.Data;

import java.util.List;

@Data
public class ChatGptResult {
	private String id;
	private String object;
	private String model;
	private List<choiceModel> choices;

	@Data
	public static class choiceModel{
		private String text;
	}
}
