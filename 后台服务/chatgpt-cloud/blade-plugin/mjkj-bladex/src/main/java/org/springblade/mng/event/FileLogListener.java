package org.springblade.mng.event;

import org.springblade.mng.cgform.model.file.FileLogModel;
import org.springblade.mng.cgform.service.IMjkjBaseSqlService;

import org.springblade.mng.config.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class FileLogListener {

	@Autowired
	private IMjkjBaseSqlService mjkjBaseSqlService;

	@EventListener
	public void FileLogEventListener(FileLogEvent fileLogEvent) {
		FileLogModel model = (FileLogModel) fileLogEvent.getSource();
		Map<String,Object> insertData=new HashMap<>();
		insertData.put("file_id",model.getFile_id());
		insertData.put("type",model.getType());
		insertData.put("file_title",model.getFile_title());
		insertData.put("remark",model.getRemark());
		insertData.put("operate_user_id",model.getOperate_user_id());
		insertData.put("operate_time", DateUtils.formatDate(DateUtils.now()));
		insertData.put("operate_user_name",model.getOperate_user_name());
		insertData.put("tenant_id",model.getTenant_id());
		mjkjBaseSqlService.baseInsertData("mjkj_file_log",insertData);
	}

}
