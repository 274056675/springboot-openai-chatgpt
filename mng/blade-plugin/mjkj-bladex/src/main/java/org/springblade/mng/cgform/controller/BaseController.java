/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.springblade.mng.cgform.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.mng.cgform.entity.CgformField;
import org.springblade.mng.cgform.entity.CgformHead;
import org.springblade.mng.cgform.service.ICgformFieldService;
import org.springblade.mng.cgform.service.ICgformHeadService;

import org.springblade.mng.config.exception.DBException;
import org.springblade.mng.config.util.BrowserUtils;
import org.springblade.mng.config.util.ConvertUtils;
import org.springblade.mng.config.util.SqlSymbolUtil;
import org.springblade.mng.config.util.converter.ConverterUtil;

import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.incrementer.OracleSequenceMaxValueIncrementer;
import org.springframework.jdbc.support.incrementer.PostgresSequenceMaxValueIncrementer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.*;

/**
 * 魔晶科技公共Controller
 */
@Slf4j
public class BaseController extends BladeController {


    @Autowired
    private ICgformHeadService cgformHeadService;

    @Autowired
    private ICgformFieldService cgformFieldService;


	//开放服务
	public void isOpenServer(){
	//可操作角色
	//可查看角色
	/*	List<String> list=new ArrayList<>();
		list.add("mjkj-bladex");

		Properties props = System.getProperties();

		if(Func.isNotEmpty(property) && list.contains(property)){
			//通过逻辑
			return;
		}
		throw new ServiceException("-------数据未开放--------");*/
	}

	/***
	 * 参数转换
	 * @param request
	 * @return
	 */
	public Map<String, Object>  paramStr2Map(HttpServletRequest request){
		// 获取request中key为"paramStr"的value
		String paramsStr = request.getParameter("paramsStr");
		Map<String, Object> hashMap = new HashMap<>();
		String paramStr = "";
		if (Func.isNotEmpty(paramsStr)) {
			try {
				// utf-8编码
				paramStr = URLDecoder.decode(paramsStr, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				log.error(e.getMessage(), e);
			}
			// 转成Map
			if (paramStr != null) {
				hashMap = JSONObject.parseObject(paramStr, Map.class);
			}
		}
		return hashMap;
	}



	/**
	 * 输出到浏览器
	 *
	 * @param request
	 * @param response
	 */
	protected void outpuFile( String fileUrl,String name, HttpServletRequest request, HttpServletResponse response) {
		OutputStream out = null;
		try {
			byte[] buffer = HttpUtil.downloadBytes(fileUrl);
			response.setContentType("application/x-msdownload;charset=utf-8");
			String browse = BrowserUtils.checkBrowse(request);
			String suffix = fileUrl.substring(fileUrl.lastIndexOf("."));
			if ("MSIE".equalsIgnoreCase(browse.substring(0, 4))) {
				response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(name, "UTF-8") );
			} else {
				String filename = new String(name.getBytes("UTF-8"), "ISO8859-1");
				response.setHeader("content-disposition", "attachment;filename=" + filename );
			}
			out = response.getOutputStream();
			out.write(buffer);
			response.flushBuffer();
		} catch (Exception e) {
			log.error("--通过流的方式获取文件异常--" + e.getMessage(), e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}

		}
	}





    /**
     * 获取唯一id
     * @param head
     * @param dataSource
     * @param databaseType
     * @return
     * @throws SQLException
     * @throws DBException
     */
    protected Object getIdByType(CgformHead head, DataSource dataSource, String databaseType) throws SQLException, DBException {

        Object result = null;
        String id = head.getIdType();//主键类型
        String idSequence = head.getIdSequence();
        if (ConvertUtils.isNotEmpty(id) && "UUID".equalsIgnoreCase(id)) {//主键为uuid
            return SqlSymbolUtil.getIdWorkerId();
        }
		if (ConvertUtils.isNotEmpty(id) && "MJID".equalsIgnoreCase(id)) {//魔晶默认
			return Func.toStr(IdWorker.getId());
		}
        if (ConvertUtils.isNotEmpty(id) && "NATIVE".equalsIgnoreCase(id)) {//当前系统用该类型
            if (ConvertUtils.isNotEmpty(databaseType) && "oracle".equalsIgnoreCase(databaseType)) {
                OracleSequenceMaxValueIncrementer oracleSequence = new OracleSequenceMaxValueIncrementer(dataSource, "HIBERNATE_SEQUENCE");

                try {
                    result = oracleSequence.nextLongValue();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            } else if (ConvertUtils.isNotEmpty(databaseType) && "postgres".equalsIgnoreCase(databaseType)) {
                PostgresSequenceMaxValueIncrementer postgresSequence = new PostgresSequenceMaxValueIncrementer(dataSource, "HIBERNATE_SEQUENCE");

                try {
                    result = postgresSequence.nextLongValue();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            } else {//mysql模式
                result = null;
            }
        } else if (ConvertUtils.isNotEmpty(id) && "SEQUENCE".equalsIgnoreCase(id)) {
            if (ConvertUtils.isNotEmpty(databaseType) && "oracle".equalsIgnoreCase(databaseType)) {
                OracleSequenceMaxValueIncrementer oracleSequence = new OracleSequenceMaxValueIncrementer(dataSource, idSequence);

                try {
                    result = oracleSequence.nextLongValue();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            } else if (ConvertUtils.isNotEmpty(databaseType) && "postgres".equalsIgnoreCase(databaseType)) {
                PostgresSequenceMaxValueIncrementer postgresSequence = new PostgresSequenceMaxValueIncrementer(dataSource, idSequence);

                try {
                    result = postgresSequence.nextLongValue();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            } else {
                result = null;
            }
        } else {
            result = SqlSymbolUtil.getIdWorkerId();
        }


        return result;
    }


}
