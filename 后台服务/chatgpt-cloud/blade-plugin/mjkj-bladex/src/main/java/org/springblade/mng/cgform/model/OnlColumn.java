package org.springblade.mng.cgform.model;

import lombok.Data;

@Data
public class OnlColumn {
    private String title;
    private String dataIndex;
    private String align;
    private String customRender;
    private ScopedSlots scopedSlots;
    private String hrefSlotName;
    private ScopedSlots jsSlots;
    private String jsEnhance;
    private Integer width;
    private boolean sorter = false;

    public OnlColumn(String title, String dataIndex, Integer width) {
        this.align = "center";
        this.title = title;
        this.dataIndex = dataIndex;
        this.width = width;
    }
}
