
package org.springblade.system.user.excel;

import lombok.RequiredArgsConstructor;
import org.springblade.core.excel.support.ExcelImporter;
import org.springblade.system.user.service.IUserService;

import java.util.List;

/**
 * 用户数据导入类
 *
 *
 */
@RequiredArgsConstructor
public class UserImporter implements ExcelImporter<UserExcel> {

	private final IUserService service;
	private final Boolean isCovered;

	@Override
	public void save(List<UserExcel> data) {
		service.importUser(data, isCovered);
	}
}
