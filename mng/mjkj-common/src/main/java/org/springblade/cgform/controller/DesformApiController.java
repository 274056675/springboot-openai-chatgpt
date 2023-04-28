
package org.springblade.cgform.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import org.springblade.cgform.entity.CgformHead;
import org.springblade.cgform.entity.DesformData;
import org.springblade.cgform.entity.DesformHead;
import org.springblade.cgform.entity.DesformRoute;
import org.springblade.cgform.enums.MjkjAeskey;
import org.springblade.cgform.model.DesformUpdateTimeModel;
import org.springblade.cgform.model.param.FormDataSaveModel;
import org.springblade.cgform.model.query.QueryGenerator;
import org.springblade.cgform.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.config.util.DateUtils;
import org.springblade.core.api.crypto.annotation.decrypt.ApiDecryptAes;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("desform-api")
@Api(value = "表单设计器开发接口", tags = "表单设计器开发接口")
public class DesformApiController {


    private final IDesformHeadService headService;


    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "表单设计器 - 详情数据", notes = "详情数据")
    @GetMapping({"/desform/{headId}"})
    public R getFormDetail(@PathVariable("headId") Long headId, @ApiParam(value = "锁校验 true需要校验") String lock) throws Exception {
		String redisKey="desform_data:"+headId;
		DesformHead head=null;
			head = headService.getById(headId);

        if(Func.isEmpty(head)){
        	return R.data(head);
		}
		//判断该接口是否要的登录
		String nologinSelect = head.getNologinSelect();
		if(Func.equals(nologinSelect,"0")){//需要登录
			BladeUser user = AuthUtil.getUser();
			if(Func.isEmpty(user)){
				return R.fail("登录已过期，请重新登录");
			}
		}


        return R.data(head);
    }
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "表单设计器 - 详情数据", notes = "详情数据")
    @GetMapping({"/desform/code/{code}"})
    public R getFormIdByCode(@PathVariable("code") String code) throws Exception {
        LambdaQueryWrapper<DesformHead> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(DesformHead::getFormCode,code);
        DesformHead head = headService.getOne(wrapper);
        return R.data(head);
    }




}
