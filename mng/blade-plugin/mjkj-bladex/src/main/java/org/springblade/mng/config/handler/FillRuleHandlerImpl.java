package org.springblade.mng.config.handler;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FillRuleHandlerImpl implements IFillRuleHandler {
    public FillRuleHandlerImpl() {
    }

    public Object execute(JSONObject params, JSONObject formData) {
        String var3 = "CN";
        if (params != null) {
            Object var4 = params.get("prefix");
            if (var4 != null) {
                var3 = var4.toString();
            }
        }

        SimpleDateFormat var8 = new SimpleDateFormat("yyyyMMddHHmmss");
        int var5 = RandomUtils.nextInt(90) + 10;
        String var6 = var3 + var8.format(new Date()) + var5;
        String var7 = formData.getString("name");
        if (!StringUtils.isEmpty(var7)) {
            var6 = var6 + var7;
        }

        return var6;
    }
}
