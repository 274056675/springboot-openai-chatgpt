/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mjkj.chatgpt.controller;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import com.alibaba.fastjson.JSONObject;
import com.mjkj.chatgpt.model.ChatGptParam;
import com.mjkj.chatgpt.model.ChatGptPublicParam;
import com.mjkj.chatgpt.model.ResultModel;
import com.mjkj.chatgpt.service.IChatGPTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("chatgpt")
public class ChatGptController {

    @Autowired
    private IChatGPTService chatGPTService;

    private static String aesKey = "vWkzDxDfXruFpgjDH7Jy0mIWamCQvdct";



    //获取gpt内容
    @PostMapping({"/getContent"})
    public ResultModel getContent(@RequestBody String paramStr) {
        try {
            if (StrUtil.isEmpty(paramStr)) {
                return this.getErrorModel("参数为空2");
            }
            AES aes = SecureUtil.aes(aesKey.getBytes());
            // 解密为字符串
            String jsonStr = aes.decryptStr(paramStr, CharsetUtil.CHARSET_UTF_8);

            ChatGptParam gptModel = JSONObject.parseObject(jsonStr, ChatGptParam.class);
            if(ObjectUtil.isEmpty(gptModel)){
                return this.getErrorModel("参数为空3");
            }

            String chatContent = chatGPTService.getChatContent(gptModel);
            return this.getSuccessModel(chatContent);
        } catch (Exception e) {
           return this.getErrorModel(e.getMessage());
        }
    }

    /**
     * gpt-3.5-turbo-0301
     * @param paramStr
     * @return
     */
    @PostMapping({"/api/getContent"})
    public ResultModel getChatGptContent(@RequestBody String paramStr) {
        try {
            if (StrUtil.isEmpty(paramStr)) {
                return this.getErrorModel("参数为空2");
            }
            AES aes = SecureUtil.aes(aesKey.getBytes());
            // 解密为字符串
            String jsonStr = aes.decryptStr(paramStr, CharsetUtil.CHARSET_UTF_8);

            ChatGptPublicParam param = JSONObject.parseObject(jsonStr, ChatGptPublicParam.class);
            if(ObjectUtil.isEmpty(param)){
                return this.getErrorModel("参数为空3");
            }
            String chatContent = chatGPTService.getChatGptPublic(param);
            return this.getSuccessModel(chatContent);
        } catch (Exception e) {
            return this.getErrorModel(e.getMessage());
        }
    }




    private ResultModel getErrorModel(String str){
        ResultModel model=new ResultModel();
        model.setCode(500);
        model.setResultStr(str);
        return model;
    }

    private ResultModel getSuccessModel(String str){
        ResultModel model=new ResultModel();
        model.setCode(200);
        model.setResultStr(str);
        return model;
    }

}
