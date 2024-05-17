package org.springblade.mng.config.util;


import lombok.extern.slf4j.Slf4j;
import org.springblade.core.tool.utils.Func;
import org.springblade.mng.cgform.entity.CgformButton;
import org.springblade.mng.cgform.entity.CgformEnhanceJs;
import org.springblade.mng.cgform.entity.CgformField;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * js增强公共类
 */
@Slf4j
public class EnhanceJsUtil {
    private static final String actions = "beforeAdd,beforeEdit,afterAdd,afterEdit,beforeDelete,afterDelete,mounted,created,show,loaded";
    private static final String interval = "\\}\\s*\r*\n*\\s*";
    private static final String separator = ",";


    public static String getCgJs(String cgJs, String buttonCode) {
        String buttonCodeStr = "(" + buttonCode + "\\s*\\(row\\)\\s*\\{)";
        String functionStr = buttonCode + ":function(that,row){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;";
        String jsFunctionStr = getJsFunction(cgJs, interval + buttonCodeStr, "}," + functionStr);
        if (jsFunctionStr == null) {
            cgJs = getRegexCgJs(cgJs, buttonCodeStr, functionStr);
        } else {
            cgJs = jsFunctionStr;
        }

        cgJs = getCgJs(cgJs, buttonCode, null);
        return cgJs;
    }

    public static String getCgJs(String cgJs, String buttonCode, String s) {
        String s1 = "(" + ConvertUtils.getString(s) + buttonCode + "\\s*\\(\\)\\s*\\{)";
        String s2 = buttonCode + ":function(that){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;";
        String jsFunction = getJsFunction(cgJs, interval + s1, "}," + s2);
        if (jsFunction == null) {
            cgJs = getRegexCgJs(cgJs, s1, s2);
        } else {
            cgJs = jsFunction;
        }

        return cgJs;
    }

    public static String getJsFunction(String cgJs, String regex, String s2) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cgJs);
        if (matcher.find()) {
            cgJs = cgJs.replace(matcher.group(0), s2);
            return cgJs;
        } else {
            return null;
        }
    }

    public static String getRegexCgJs(String cgJs, String regex, String s1) {
        String jsFunction = getJsFunction(cgJs, regex, s1);
        return jsFunction != null ? jsFunction : cgJs;
    }

    /*public static String getCgJs(String var0, List<CgformButton> var1) {
        log.info("最终的增强JS", var0);
        return "class OnlineEnhanceJs{constructor(getAction,postAction,deleteAction){this._getAction=getAction;this._postAction=postAction;this._deleteAction=deleteAction;}" + var0 + "}";
    }*/

    public static String getJsFunction(String cgJs, String dbFieldName) {
        String dbFieldNameStr = "(\\s+" + dbFieldName + "\\s*\\(\\)\\s*\\{)";
        String functionStr = dbFieldName + ":function(that,event){";
        String jsFunction = getJsFunction(cgJs, interval + dbFieldNameStr, "}," + functionStr);
        if (jsFunction == null) {
            cgJs = getRegexCgJs(cgJs, dbFieldNameStr, functionStr);
        } else {
            cgJs = jsFunction;
        }

        return cgJs;
    }

    public static String getCgJs(String cgJs) {
        String js = "function OnlineEnhanceJs(getAction,postAction,deleteAction){return {_getAction:getAction,_postAction:postAction,_deleteAction:deleteAction," + cgJs + "}}";
        log.info("最终的增强JS", js);
        return js;
    }

    public static String getJsFunction(String cgJs, List<CgformButton> onlCgformButtonList) {
        cgJs = getCgJs(cgJs, onlCgformButtonList);
        String js = "function OnlineEnhanceJs(getAction,postAction,deleteAction){return {_getAction:getAction,_postAction:postAction,_deleteAction:deleteAction," + cgJs + "}}";
        log.info("最终的增强JS", js);
        return js;
    }

    public static String getCgJs(String cgJs, List<CgformButton> buttons) {
        if (buttons != null) {
            Iterator<CgformButton> iterator = buttons.iterator();

            while (iterator.hasNext()) {
                CgformButton button = iterator.next();
                String code = button.getButtonCode();
                if ("link".equals(button.getButtonStyle())) {
                    cgJs = getCgJs(cgJs, code);
                } else if ("button".equals(button.getButtonStyle()) || "form".equals(button.getButtonStyle())) {
                    cgJs = getCgJs(cgJs, code, null);
                }
            }
        }

        String[] actionArr = actions.split(separator);
        int length = actionArr.length;

        for (int i = 0; i < length; ++i) {
            String action = actionArr[i];
            if ("beforeAdd,afterAdd,mounted,created,show,loaded".indexOf(action) >= 0) {
                cgJs = getCgJs(cgJs, action, null);
            } else {
                cgJs = getCgJs(cgJs, action);
            }
        }

        return cgJs;
    }

    public static void enhanceJs(CgformEnhanceJs onlCgformEnhanceJs, String var1, List<CgformField> fields) {
        if (Func.isEmpty(onlCgformEnhanceJs) || Func.isEmpty(onlCgformEnhanceJs.getCgJs())) {
            return;
        }

        String cgJs = " " + onlCgformEnhanceJs.getCgJs();
        log.info("one enhanceJs begin==> " + cgJs);
        Pattern pattern = Pattern.compile("(\\s{1}onlChange\\s*\\(\\)\\s*\\{)");
        Matcher matcher = pattern.matcher(cgJs);
        if (matcher.find()) {
            log.info("---JS 增强转换-main--enhanceJsFunctionName----onlChange");
            cgJs = getCgJs(cgJs, "onlChange", "\\s{1}");

            Iterator<CgformField> iterator = fields.iterator();
            while (iterator.hasNext()) {
                CgformField formField = iterator.next();
                cgJs = getJsFunction(cgJs, formField.getDbFieldName());
            }
        }

        log.info("one enhanceJs end==> " + cgJs);
        onlCgformEnhanceJs.setCgJs(cgJs);

    }

    public static void getJsFunction(CgformEnhanceJs onlCgformEnhanceJs, String var1, List<CgformField> fields) {
        if (Func.isEmpty(onlCgformEnhanceJs) || Func.isEmpty(onlCgformEnhanceJs.getCgJs())) {
            return;
        }

        log.info(" sub enhanceJs begin==> " + onlCgformEnhanceJs);
        String cgJs = onlCgformEnhanceJs.getCgJs();
        String onlChangeStr = var1 + "_" + "onlChange";
        Pattern pattern = Pattern.compile("(" + onlChangeStr + "\\s*\\(\\)\\s*\\{)");
        Matcher matcher = pattern.matcher(cgJs);
        if (matcher.find()) {
            log.info("---JS 增强转换-sub-- enhanceJsFunctionName----" + onlChangeStr);
            cgJs = getCgJs(cgJs, onlChangeStr, null);

            Iterator<CgformField> iterator = fields.iterator();
            while (iterator.hasNext()) {
                CgformField formField = iterator.next();
                cgJs = getJsFunction(cgJs, formField.getDbFieldName());
            }
        }

        log.info(" sub enhanceJs end==> " + cgJs);
        onlCgformEnhanceJs.setCgJs(cgJs);

    }
}
