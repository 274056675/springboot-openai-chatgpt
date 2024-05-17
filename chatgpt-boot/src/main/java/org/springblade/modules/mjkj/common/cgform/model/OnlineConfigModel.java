package org.springblade.modules.mjkj.common.cgform.model;

import lombok.Data;
import org.springblade.core.secure.BladeUser;
import org.springblade.modules.mjkj.common.cgform.entity.CgformButton;
import org.springblade.modules.system.entity.BladeDept;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class OnlineConfigModel {
    private Long headId;
    private String formTemplate;
    private String description;
    private String currentTableName;
    private Integer tableType;
    private String paginationFlag;
    private String checkboxFlag;
    private Integer scrollFlag;
    private List<OnlColumn> columns;
    private List<String> hideColumns;
    private Map<String, List<DictModel>> dictOptions = new HashMap<>();
    private Map<String, List<BladeDept>> deptOptions = new HashMap<>();
    private Map<String, List<BladeUser>> userOptions = new HashMap<>();
    private List<CgformButton> cgButtonList;
    private List<HrefSlots> fieldHrefSlots;
    private String enhanceJs;
	private String enhanceJsApp;
    private List<OnlForeignKey> foreignKeys;
    private String pidField;
    private String hasChildrenField;
    private String textField;
}
