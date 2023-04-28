package com.mjkj.chatgpt.model;

import lombok.Data;

/**
 * ChatGpt 公共接口
 */
@Data
public class ChatGptPublicParam {
    private String url;//地址  必填
    private String key;//key 比填
    private String body;//请求体
}
