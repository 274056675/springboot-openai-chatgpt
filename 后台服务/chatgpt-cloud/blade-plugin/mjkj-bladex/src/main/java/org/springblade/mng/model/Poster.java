package org.springblade.mng.model;

import lombok.Data;

import java.awt.*;
import java.io.Serializable;

@Data
public class Poster  implements Serializable {
	private int width;//海报的宽（像素为单位）
	private int height;//海报的高
	//头像
	private String avatarUrl;//头像url
	private int avatarX;//头像左上角横坐标
	private int avatarY;//头像左上角纵坐标
	private int avatarWidth;//头像宽
	private int avatarHeight;//头像高
	private boolean isCircle;//是否圆形头像
	//名字
	private String name;
	private Font nameFont;
	private Color nameColor;
	private int nameX;
	private int nameY;
	//商品
	private String goodsUrl;
	private int goodsX;
	private int goodsY;
	private int goodsWidth;
	private int goodsHeight;
	//商品描述
	private String desc;
	private Font descFont;
	private Color descColor;
	private int descX;
	private int descY;
	//商品价格
	private String price;
	private Font priceFont;
	private Color priceColor;
	private int priceX;
	private int priceY;

	//测评结果标题
	private String result;
	private Font resultFont;
	private Color resultColor;
	private int resultX;
	private int resultY;

	//测评结果描述
	private String result_content;
	private Font result_contentFont;
	private Color result_contentColor;
	private int result_contentX;
	private int result_contentY;

	//小程序码
	private String qrCodeUrl;
	private int qrCodeX;
	private int qrCodeY;
	private int qrCodeWidth;
	private int qrCodeHeight;

	//空白
	private String blankUrl;
	private int blankX;
	private int blankY;
	private int blankWidth;
	private int blankHeight;

	//提示1
	private String tip1;
	private Font tip1Font;
	private Color tip1Color;
	private int tip1X;
	private int tip1Y;
	//提示2
	private String tip2;
	private Font tip2Font;
	private Color tip2Color;
	private int tip2X;
	private int tip2Y;
	//底部栏
	private Color footerColor;
	private int footerWidth;
	private int footerHeight;
	private int footerX;
	private int footerY;
	//底部栏提示字
	private String footerTip;
	private Font footerTipFont;
	private Color footerTipColor;
	private int footerTipX;
	private int footerTipY;


}
