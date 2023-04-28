package org.springblade.mng.param;

import lombok.Data;

/**
 * ChatGpt 相关参数
 */
@Data
public class ChatGptParam {
    private String url;//地址  必填
    private String key;//key 比填
    private String model;//模型名称，指定使用的ChatGPT模型。 必填
    private String prompt;//输入的文本提示 必填
    private Integer temperature;//控制生成文本的多样性
    private Integer max_tokens;//控制生成文本的长度
    private Integer top_p;//控制生成文本的多样性
}
