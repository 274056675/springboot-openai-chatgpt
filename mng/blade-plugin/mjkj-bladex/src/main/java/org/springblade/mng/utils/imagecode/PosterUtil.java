package org.springblade.mng.utils.imagecode;

import org.springblade.mng.model.Poster;
import sun.font.FontDesignMetrics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;


public class PosterUtil {
	public static Poster initPoster(Poster poster_) {
		Poster poster = new Poster();
		//画布
		poster.setWidth(poster_.getWidth());
		poster.setHeight(poster_.getHeight());
		//头像
       poster.setAvatarUrl(poster_.getAvatarUrl());
       poster.setCircle(true);
	   poster.setAvatarWidth(50);
	   poster.setAvatarHeight(5);
	   poster.setAvatarX(30);
	   poster.setAvatarY(poster.getAvatarY()+poster.getAvatarHeight());

       //名字
       poster.setName(poster_.getName());
       poster.setNameFont(new Font("宋体",Font.BOLD,14));
       poster.setNameColor(Color.GRAY);
       poster.setNameX(25);
       poster.setNameY(497);
		//顶部图片
		poster.setGoodsUrl(poster_.getGoodsUrl());
		poster.setGoodsWidth(425); //顶部图片宽度
		poster.setGoodsHeight(410); //顶部图片高度400
		poster.setGoodsX((poster_.getWidth()-poster.getGoodsWidth())/2);
		poster.setGoodsY(poster.getAvatarY()+poster.getAvatarHeight()+50); //+100是往下移动
		//测评结果
		int size = poster_.getWidth()/17;
		/*poster.setDesc("----------------------------------");
		poster.setDescColor(Color.GRAY);
		poster.setDescFont(new Font("宋体",Font.BOLD,18));
		poster.setDescX((poster_.getWidth()-"---------------------------------".length()*size)/6-15);
		poster.setDescY(poster.getGoodsY()+poster.getGoodsHeight()+(int)(poster_.getHeight()/21.3)+15);*/
		//潇洒的寒风666
		poster.setPrice(poster_.getPrice());
		poster.setPriceColor(Color.GRAY);
		poster.setPriceFont(new Font("宋体",Font.BOLD,16));
		poster.setPriceX(25);
		poster.setPriceY(550);

		//测评说明
		poster.setResult(poster_.getResult());
		poster.setResultColor(Color.GRAY);
		poster.setResultFont(new Font("宋体",Font.BOLD,18));
		poster.setResultX(-9);
		poster.setResultY(525);

		//时间
		poster.setResult_content(poster_.getDesc());
		poster.setResult_contentColor(Color.GRAY);
		poster.setResult_contentFont(new Font("宋体",Font.BOLD,14));
		poster.setResult_contentX(0);
		poster.setResult_contentY(575);

		//------------------------
		//二维码
		poster.setQrCodeUrl(poster_.getQrCodeUrl());
		poster.setQrCodeWidth((int)100);//二维码宽度
		poster.setQrCodeHeight((int)100);////二维码高度
		poster.setQrCodeX((int)(335));//二维码x轴位置
		poster.setQrCodeY(575);//二维码y轴位置
		//长按图片识别二维码
		poster.setTip1("长按图片识别二维码");
		poster.setTip1Color(Color.GRAY);
		poster.setTip1Font(new Font("宋体",Font.PLAIN,poster_.getWidth()/35));
		poster.setTip1X(200);
		poster.setTip1Y(623);
		//和我一起生成AI创作图像
		poster.setTip2("和我一起生成AI创意图像");
		poster.setTip2Color(Color.GRAY);
		poster.setTip2Font(new Font("宋体",Font.PLAIN,poster_.getWidth()/35));
		poster.setTip2X(174);
		poster.setTip2Y(645);//90是这句问题与上面的间距

		poster.setBlankUrl(poster_.getBlankUrl());
		poster.setBlankWidth((int)500);//二维码宽度
		poster.setBlankHeight((int)100);////二维码高度
		poster.setBlankX((int)(0));//二维码x轴位置
		poster.setBlankY(-95);//二维码y轴位置
		//footer
		/*poster.setFooterColor(new Color(49,196,141));
		poster.setFooterWidth(poster_.getWidth());
		poster.setFooterHeight(poster_.getHeight()/13);
		poster.setFooterX(0);
		poster.setFooterY(poster_.getHeight()-poster.getFooterHeight());*/
		//footer tips
		poster.setFooterTip("");
		poster.setFooterTipColor(Color.WHITE);
		poster.setFooterTipFont(new Font("宋体",Font.BOLD,poster_.getWidth()/21));
		poster.setFooterTipX((poster_.getWidth()-(poster.getFooterTip().length()*poster_.getWidth()/21))/2);
		poster.setFooterTipY(poster_.getHeight()-poster.getFooterHeight()/3);
		return poster;
	}

	public  static void drawPoster(String fileUrl,Poster poster) throws Exception {
		long startTime = System.currentTimeMillis();
		String qrCodeUrl = poster.getQrCodeUrl();//二维码图片地址
		String goodsUrl = poster.getGoodsUrl();//顶部图片地址
		String avatarUrl = poster.getAvatarUrl();//头像图片地址
		String blankUrl = poster.getBlankUrl();//头像图片地址
		BufferedImage qrCodeImage = ImageIO.read(new URL(qrCodeUrl));
		BufferedImage goodsImage = ImageIO.read(new URL(goodsUrl));
		BufferedImage blankImage = ImageIO.read(new URL(blankUrl));
		int width = poster.getWidth();
		int height = poster.getHeight();
		//画布
		BufferedImage canvas = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) canvas.getGraphics();
		g.setBackground(Color.WHITE);//设置背景色
		g.clearRect(0, 0, width, height);

		// 设置文字抗锯齿
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
       //圆形头像
       BufferedImage newAvatar = circle(avatarUrl,poster.getAvatarWidth());
       //画头像
       g.drawImage(newAvatar.getScaledInstance(newAvatar.getWidth(), newAvatar.getHeight(), Image.SCALE_SMOOTH), poster.getAvatarX(), poster.getAvatarY(), null);
       // 4. 写字（昵称）
       g.setColor(poster.getNameColor());
       g.setFont(poster.getNameFont());
       g.drawString(poster.getName(), poster.getNameX(), poster.getNameY());
		//画商品
		//g.drawImage(Thumbnails.of(goodsImage).size(poster.getGoodsWidth(), poster.getGoodsHeight()).asBufferedImage(), poster.getGoodsX(), poster.getGoodsY(), null);
		//g.drawImage(goodsImage.getScaledInstance(poster.getGoodsWidth(), poster.getGoodsHeight(), Image.SCALE_SMOOTH), poster.getGoodsX(), poster.getGoodsY(), null);
		g.drawImage(goodsImage.getScaledInstance(poster.getGoodsWidth(), poster.getGoodsHeight(), Image.SCALE_SMOOTH), poster.getGoodsX(), poster.getGoodsY(), null);

		//创作时间
		g.setColor(poster.getDescColor());
		g.setFont(poster.getDescFont());
		g.drawString("测评结果", poster.getDescX(), poster.getDescY());
		//潇洒的寒风微信名
		g.setColor(poster.getPriceColor());
		g.setFont(poster.getPriceFont());
		g.drawString(poster.getPrice(), poster.getPriceX(), poster.getPriceY());

		//画测评说明
		g.setColor(poster.getResultColor());
		g.setFont(poster.getResultFont());
		g.drawString(poster.getResult(), poster.getResultX(), poster.getResultY());


		//画测评说明描述
       g.setColor(poster.getResult_contentColor());
       g.setFont(poster.getResult_contentFont());
       g.drawString("", poster.getResult_contentX()+25, poster.getResult_contentY());


		FontDesignMetrics metrics = FontDesignMetrics.getMetrics(poster.getResult_contentFont());
		String zh = poster.getResult_content();
		String[] rows = makeLineFeed(zh, metrics, 300).split("\n");
		int  y = poster.getResult_contentY();
		for (int i = 0; i < rows.length; i++) {
			g.setColor(poster.getResult_contentColor());
			g.setFont(poster.getResult_contentFont());
			if(i > 0){
				y+=28;
			}
			if(i>4){
				break;
			}else if(i==4){
				g.drawString(rows[i].substring(0, rows[i].length()-3)+" ...", poster.getResult_contentX()+25, y);
				break;
			}
			g.drawString(rows[i], poster.getResult_contentX()+25, y);

		}


		//画微信二维码
		g.drawImage(qrCodeImage.getScaledInstance(poster.getQrCodeWidth(), poster.getQrCodeHeight(), Image.SCALE_SMOOTH),
			poster.getQrCodeX(), poster.getQrCodeY(), null);

		g.drawImage(blankImage.getScaledInstance(poster.getBlankWidth(), poster.getBlankHeight(), Image.SCALE_SMOOTH),
			poster.getBlankX(), poster.getBlankY(), null);
		//画tips1
		g.setColor(poster.getTip1Color());
		g.setFont(poster.getTip1Font());
		g.drawString(poster.getTip1(), poster.getTip1X(), poster.getTip1Y());
		//画tips2
		g.setColor(poster.getTip2Color());
		g.setFont(poster.getTip2Font());
		g.drawString(poster.getTip2(), poster.getTip2X(), poster.getTip2Y());
		//画底部栏
		g.setColor(poster.getFooterColor());
		g.fillRect(poster.getFooterX(),poster.getFooterY(),poster.getFooterWidth(),poster.getFooterHeight());
		//画底部栏提示
		g.setColor(poster.getFooterTipColor());
		g.setFont(poster.getFooterTipFont());
		g.drawString(poster.getFooterTip(), poster.getFooterTipX(), poster.getFooterTipY());
		g.dispose();
		File resultImg = new File(fileUrl);//将生成图片写进D盘
		ImageIO.write(canvas, "png", resultImg);
		//上传服务器代码
		//ByteArrayOutputStream bs = new ByteArrayOutputStream();
		//ImageOutputStream imgOut = ImageIO.createImageOutputStream(bs);
		//ImageIO.write(canvas, "png", imgOut);
		//InputStream inSteam = new ByteArrayInputStream(bs.toByteArray());
		//String url = OSSFactory.build().upload(inSteam, UUID.randomUUID().toString()+".png");
		System.out.println("生成成功！");
		System.out.println("耗时: " + (System.currentTimeMillis()-startTime)/1000.0 + "s");
		System.out.println("生成文件路径: " + resultImg.getAbsolutePath());
	}


	private static BufferedImage circle(String avatar_img,int width) throws Exception {
		BufferedImage avatar = ImageIO.read(new URL(avatar_img));
		BufferedImage newAvatar = new BufferedImage(width, width, BufferedImage.TYPE_INT_ARGB);
		Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, width, width);
		Graphics2D g2 = newAvatar.createGraphics();
		newAvatar = g2.getDeviceConfiguration().createCompatibleImage(width, width, Transparency.TRANSLUCENT);
		g2 = newAvatar.createGraphics();
		g2.setComposite(AlphaComposite.Clear);
		g2.fill(new Rectangle(newAvatar.getWidth(), newAvatar.getHeight()));
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 1.0f));
		g2.setClip(shape);
		// 使用 setRenderingHint 设置抗锯齿
		g2 = newAvatar.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.fillRoundRect(0, 0,width, width, width, width);
		g2.setComposite(AlphaComposite.SrcIn);
		g2.drawImage(avatar, 0, 0, width, width, null);
		g2.dispose();
		return newAvatar;
	}

	//文本换行处理
	public static String makeLineFeed(String zh, FontDesignMetrics metrics, int max_width) {
		StringBuilder sb = new StringBuilder();
		int line_width = 0;
		for (int i = 0; i < zh.length(); i++) {
			char c = zh.charAt(i);
			sb.append(c);
			// 如果主动换行则跳过
			if (sb.toString().endsWith("\n")) {
				line_width = 0;
				continue;
			}
			// FontDesignMetrics 的 charWidth() 方法可以计算字符的宽度
			int char_width = metrics.charWidth(c);
			line_width += char_width;
			// 如果当前字符的宽度加上之前字符串的已有宽度超出了海报的最大宽度，则换行
			if (line_width >= max_width - char_width) {
				line_width = 0;
				sb.append("\n");
			}
		}
		return sb.toString();
	}
}


