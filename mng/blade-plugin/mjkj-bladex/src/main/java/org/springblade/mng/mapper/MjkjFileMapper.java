package org.springblade.mng.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 文件管理
 */
public interface MjkjFileMapper {

	//是否存在私人文件夹
	Integer isExistPrivateFolder(String tenantId);
	//是否存在公共文件夹
	Integer isExistPublicFolder(String tenantId);
	//用户是否有私人文件
	Integer isExistUserFolder(Long userId, String tenantId);


	String getUserName(List<Long> userIdList);
	String getDeptName(List<Long> deptIdList);
	String getRoleName(List<Long> roleIdList);



	Integer removeUserIdByFileId(Long fileId);
	Integer removeRoleIdByFileId(Long fileId);
	Integer removeDeptIdByFileId(Long fileId);

	String getDeptParentId(Long deptId);


	Integer getFileRoleUserId(Long fileId, Long userId);

	Integer getFileRoleRoleId(Long fileId, Long roleId);

	Integer getFileRoleDeptId(Long fileId, Long deptId);

	List<Long> getMyRoleFileIdList(@Param("bladeUserId") Long bladeUserId, @Param("roleIdList") List<Long> roleIdList, @Param("deptIdList") List<Long> deptIdList);

	List<Long> getMyRoleFileIdListUserId(Long fileId);
	List<Long> getMyRoleFileIdListDeptId(Long fileId);
	List<Long> getMyRoleFileIdListRoleId(Long fileId);

	List<Map<String,Object>> getAllFolderList();

	List<Long> getSubDeptId(Long deptId);

	void updateSubPstr(String oPstr, String nPstr);

	List<Map<String,Object>> getFileList();

	Page<Map<String, Object>> getFileViewEnhanceList(@Param("page") Page<Map<String, Object>> page, Map<String, Object> params);

	List<Map<String,Object>> getFileTypeList();

	List<Map<String,Object>> getMyPrivateFile(Long userId);
	List<Map<String,Object>> getMyPublicFile();

	List<Map<String,Object>> getSubFileList(Long fileId);

	List<Long> getSrkjList();

	Map<String, Object> getPrivateFile(String tenantId);

	//平台
	List<Map<String,Object>> getMyPtFile();

	Map<String,Object> fileListById(Long id);
}
