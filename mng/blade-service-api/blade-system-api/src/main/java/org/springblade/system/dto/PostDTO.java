
package org.springblade.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.system.entity.Post;

/**
 * 岗位表数据传输对象实体类
 *
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PostDTO extends Post {
	private static final long serialVersionUID = 1L;

}
