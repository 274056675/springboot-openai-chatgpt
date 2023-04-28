package org.springblade.mng.utils.imagecode;

import lombok.Data;

import java.awt.image.BufferedImage;

@Data
public class SliderPuzzleInfo{
	/**
	 * 大图宽度
	 */
	private Integer bigWidth;

	/**
	 * 大图高度
	 */
	private Integer bigHeight;

	/**
	 * 大图转BASE64字符串
	 */
	private String bigImageBase64;

	/**
	 * 大图
	 */
	private BufferedImage bigImage;

	/**
	 * 随机坐标Y
	 */
	private Integer posY;
	/**
	 * 随机坐标X
	 */
	private Integer posX;

	/**
	 * 小图宽度
	 */
	private Integer smallWidth;
	/**
	 * 小图高度
	 */
	private Integer smallHeight;

	/**
	 * 小图转BASE64字符串
	 */
	private String smallImageBase64;

	/**
	 * 小图
	 */
	private BufferedImage smallImage;

	/*token*/
	private String token;


}
