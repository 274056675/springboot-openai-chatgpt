package org.springblade.modules.mjkj.common.cgform.model;

import lombok.Data;

@Data
public class ScopedSlots {
    private String customRender;

    public ScopedSlots(String customRender) {
        this.customRender = customRender;
    }
}
