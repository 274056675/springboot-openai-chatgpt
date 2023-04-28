package com.mjkj.chatgpt.model;

import lombok.Data;

/**
 * 返回值
 */
@Data
public class ResultModel {
    private int code;//返回码
    private String resultStr;
}
