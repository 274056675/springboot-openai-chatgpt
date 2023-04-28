package org.springblade.cgform.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class KvModel implements Serializable {
    private String k;
    private String v;
}
