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
package org.springblade.modules.mjkj.common.cgform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springblade.modules.mjkj.common.cgform.entity.CgformIndex;
import org.springblade.modules.mjkj.common.cgform.mapper.CgformHeadMapper;
import org.springblade.modules.mjkj.common.cgform.mapper.CgformIndexMapper;
import org.springblade.modules.mjkj.common.cgform.service.ICgformIndexService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * 服务实现类
 *
 * @author BladeX
 * @since 2021-05-20
 */
@Service
public class CgformIndexServiceImpl extends BaseServiceImpl<CgformIndexMapper, CgformIndex> implements ICgformIndexService {

    @Autowired
    private CgformHeadMapper headMapper;

    @Override
    public List<CgformIndex> getCgformIndexsByCgformId(Long cgformId) {
        return this.baseMapper.selectList((new LambdaQueryWrapper<CgformIndex>()).in(CgformIndex::getCgformHeadId, new Object[]{cgformId}));
    }

    @Override
    public boolean isExistIndex(String countSql) {
        if (countSql == null) {
            return true;
        } else {
            Integer count = baseMapper.queryIndexCount(countSql);
            return count != null && count > 0;
        }
    }

    @Override
    public void createIndex(Long headId, String databaseType, String tbname) {
        LambdaQueryWrapper<CgformIndex> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CgformIndex::getCgformHeadId, headId);
        List<CgformIndex> list = this.list(wrapper);
        if (Func.isEmpty(list)) {
            return;
        }
        Iterator<CgformIndex> iterator = list.iterator();

        while (iterator.hasNext()) {
            CgformIndex index = iterator.next();
            if ("N".equals(index.getIsDbSynch())) {
                String ddlStr = "";
                String indexName = index.getIndexName();
                String indexField = index.getIndexField();
                String var11 = "normal".equals(index.getIndexType()) ? " index " : index.getIndexType() + " index ";
                byte type = -1;
                switch (databaseType.hashCode()) {
                    case -1955532418:
                        if (databaseType.equals("ORACLE")) {
                            type = 1;
                        }
                        break;
                    case -1620389036:
                        if (databaseType.equals("POSTGRESQL")) {
                            type = 3;
                        }
                        break;
                    case 73844866:
                        if (databaseType.equals("MYSQL")) {
                            type = 0;
                        }
                        break;
                    case 912124529:
                        if (databaseType.equals("SQLSERVER")) {
                            type = 2;
                        }
                }

                switch (type) {
                    case 0:
                        ddlStr = "create " + var11 + indexName + " on " + tbname + "(" + indexField + ")";
                        break;
                    case 1:
                        ddlStr = "create " + var11 + indexName + " on " + tbname + "(" + indexField + ")";
                        break;
                    case 2:
                        ddlStr = "create " + var11 + indexName + " on " + tbname + "(" + indexField + ")";
                        break;
                    case 3:
                        ddlStr = "create " + var11 + indexName + " on " + tbname + "(" + indexField + ")";
                        break;
                    default:
                        ddlStr = "create " + var11 + indexName + " on " + tbname + "(" + indexField + ")";
                }

                headMapper.executeDDL(ddlStr);
                index.setIsDbSynch("Y");
                this.updateById(index);
            }
        }


    }
}
