package org.springblade.mng.model;

import lombok.Data;

/**
 * @author JX
 * @create 2023-09-22 14:07
 * @dedescription:
 */
@Data
public class AiModel {
	private String mx_lx;
	private Integer is_use_rl;
	private Integer use_num;
	private Integer model_type;
	private String show_name;
	private String image_size;
}
