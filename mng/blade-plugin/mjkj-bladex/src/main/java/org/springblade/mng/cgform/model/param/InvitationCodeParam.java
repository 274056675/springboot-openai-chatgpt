package org.springblade.mng.cgform.model.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class InvitationCodeParam implements Serializable {
    private Long activity_id;
    private Long blade_user_id;
}
