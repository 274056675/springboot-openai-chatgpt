package org.springblade.mng.cgform.model;

import lombok.Data;

@Data
public class ScopedSlots {
    private String customRender;

    public ScopedSlots(String customRender) {
        this.customRender = customRender;
    }
}
