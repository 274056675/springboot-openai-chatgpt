
package org.springblade.system.user.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * UserExcel
 *
 *
 */
@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class UserExcel implements Serializable {
	private static final long serialVersionUID = 1L;

	@ColumnWidth(15)
	@ExcelProperty("租户编号")
	private String tenantId;

	@ExcelIgnore
	@ExcelProperty("用户平台")
	private String userType;

	@ColumnWidth(20)
	@ExcelProperty("用户平台名称")
	private String userTypeName;

	@ColumnWidth(15)
	@ExcelProperty("账户")
	private String account;

	@ColumnWidth(10)
	@ExcelProperty("昵称")
	private String name;

	@ColumnWidth(10)
	@ExcelProperty("姓名")
	private String realName;

	@ExcelProperty("邮箱")
	private String email;

	@ColumnWidth(15)
	@ExcelProperty("手机")
	private String phone;

	@ExcelIgnore
	@ExcelProperty("角色ID")
	private String roleId;

	@ExcelIgnore
	@ExcelProperty("部门ID")
	private String deptId;

	@ExcelIgnore
	@ExcelProperty("岗位ID")
	private String postId;

	@ExcelProperty("角色名称")
	private String roleName;

	@ExcelProperty("部门名称")
	private String deptName;

	@ExcelProperty("岗位名称")
	private String postName;

	@ColumnWidth(20)
	@ExcelProperty("生日")
	private Date birthday;

}
