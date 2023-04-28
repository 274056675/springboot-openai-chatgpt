package org.springblade.mng.model;

import lombok.Data;

import java.util.List;

@Data
public class ChatGptTurboResult {
	private String id;
	private String object;
	private String model;
	private List<ChoiceModel> choices;
	private UsageModel usage;
	@Data
	public static class UsageModel{
		private Integer prompt_tokens;
		private Integer completion_tokens;
		private Integer total_tokens;
	}

	@Data
	public static class ChoiceModel{
		private MessageModel message;
	}
	@Data
	public static class MessageModel{
		private String role;
		private String content;
	}

}
