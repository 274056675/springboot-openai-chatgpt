package org.springblade.modules.mjkj.common.cgform.model;

import lombok.Data;

@Data
public class HrefSlots {
    private String slotName;
    private String href;

    public HrefSlots(String slotName, String href) {
        this.slotName = slotName;
        this.href = href;
    }
}
