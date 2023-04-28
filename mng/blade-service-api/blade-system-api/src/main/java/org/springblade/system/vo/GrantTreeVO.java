
package org.springblade.system.vo;

import lombok.Data;
import org.springblade.core.tool.node.TreeNode;

import java.io.Serializable;
import java.util.List;

/**
 * GrantTreeVO
 *
 *
 */
@Data
public class GrantTreeVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<TreeNode> menu;

	private List<TreeNode> dataScope;

	private List<TreeNode> apiScope;

}
