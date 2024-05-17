package org.springblade.mng.cgform.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DictModel implements Serializable{
	private static final long serialVersionUID = 1L;

	public DictModel() {
	}

	public DictModel(String value, String text) {
		this.value = value;
		this.text = text;
	}

	/**
	 * 字典value
	 */
	private String value;
	/**
	 * 字典文本
	 */
	private String text;

	private String label;
	private String id;
	private String key;



	public void setText(String text) {
		this.text = text;
		this.label = text;
	}


	public void setValue(String value) {
		this.value = value;
		this.id =value;
		this.key=value;
	}

	/**
	 * 特殊用途： JgEditableTable
	 * @return
	 */
	public String getTitle() {
		return this.text;
	}

}
