package org.springblade.mng.model;

import lombok.Data;

/**
 * @author JX
 * @create 2023-09-08 14:46
 * @dedescription: 给文心一言发问题后的返回类型
 */
@Data
public class ErnieBotTurboResult {


	private String id; //本轮对话的id

	private String object; //回包类型

	private Integer created; //时间戳

	private String sentence_id; //当前子句的序号，流式模式下才有

	private Boolean is_end; //表示当前子句是否是最后一句，流式模式下才有

	private Boolean is_truncated; //当前生成的结果是否被截断

	private String result; //对话返回结果

	private Boolean need_clear_history; //表示用户输入是否安全，是否关闭当前会话，清理历史会话信息

	private Integer ban_round; //当need_clear_history为true时，此字段会告知第几轮对话有敏感信息，如果是当前问题，ban_round=-1

	private Usage usage; //token统计信息，token数约为汉字数+单词数*1.3

	private Integer error_code;

	private String error_msg;


	@Data
	public static class Usage {
		private Integer prompt_tokens;
		private Integer completion_tokens;
		private Integer total_tokens;

	}

}
