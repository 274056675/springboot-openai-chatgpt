
package org.springblade.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.system.entity.Post;
import org.springblade.system.vo.PostVO;

import java.util.List;

/**
 * 岗位表 Mapper 接口
 *
 *
 */
public interface PostMapper extends BaseMapper<Post> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param post
	 * @return
	 */
	List<PostVO> selectPostPage(IPage page, PostVO post);

	/**
	 * 获取岗位名
	 *
	 * @param ids
	 * @return
	 */
	List<String> getPostNames(Long[] ids);

}
