package org.springblade.mng.cgform.model;

import lombok.Data;
import org.springblade.core.tool.node.INode;

import java.util.ArrayList;
import java.util.List;

/**
 * 树结构
 */
@Data
public class TreeDataModel  implements INode<TreeDataModel> {
    private Long id;
    private Long pId;//父节点
    private Long parentId;//父节点
    private String title;//显示列
    private Object data;
    private List<TreeDataModel> children;


    @Override
    public List<TreeDataModel> getChildren() {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        return this.children;
    }
}
