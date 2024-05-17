package org.springblade.mng.cgform.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class OnlGenerateModel implements Serializable {
    /**
	 *
	 */
	private static final long serialVersionUID = 684098897071177558L;
    private Long headId;
    private String projectPath;
    private String packageStyle;
    private String ftlDescription;
    private String jformType;
    private String tableName;
    private String entityPackage;
    private String entityName;
    private String jspMode;
    private List<OnlGenerateModel> subList = new ArrayList<>();
}
