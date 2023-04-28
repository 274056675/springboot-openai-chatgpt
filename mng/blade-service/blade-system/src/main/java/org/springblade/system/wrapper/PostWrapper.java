
package org.springblade.system.wrapper;

import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.system.cache.DictCache;
import org.springblade.system.entity.Post;
import org.springblade.system.enums.DictEnum;
import org.springblade.system.vo.PostVO;

import java.util.Objects;

/**
 * 岗位表包装类,返回视图层所需的字段
 *
 *
 */
public class PostWrapper extends BaseEntityWrapper<Post, PostVO> {

	public static PostWrapper build() {
		return new PostWrapper();
	}

	@Override
	public PostVO entityVO(Post post) {
		PostVO postVO = Objects.requireNonNull(BeanUtil.copy(post, PostVO.class));
		String categoryName = DictCache.getValue(DictEnum.POST_CATEGORY, post.getCategory());
		postVO.setCategoryName(categoryName);
		return postVO;
	}

}
