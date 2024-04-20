package org.springblade.mng.utils.imagecode;

import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Random;


public class SlidePuzzleUtil {

	static Logger logger = LoggerFactory.getLogger(SlidePuzzleUtil.class);

	// 大图宽度（原图裁剪拼图后的背景图）
	private static final Integer bigWidth = 300;
	// 大图高度
	private static final Integer bigHeight = 150;
	// 小图宽度（滑块拼图）
	private static int smallWidth = 40;
	// 小图高度
	private static int smallHeight = 40;
	// 小圆半径，即拼图上的凹凸轮廓半径
	private static final Integer smallCircle = 8;
	// 小圆距离点
	private static int smallCircleR1 = smallCircle / 2;


	/**
	 * 生成滑块拼图验证码
	 *
	 * @param input
	 * @return 返回null,表示生成滑块拼图验证码异常
	 */
	public static SliderPuzzleInfo createImage(InputStream input) {
		SliderPuzzleInfo sliderPuzzleInfo = new SliderPuzzleInfo();
		try {
			// 1.获取原图对象
			BufferedImage originalImage = ImageIO.read(input);
			// 规范原图的大小
			BufferedImage bigImage = resizeImage(originalImage, bigWidth, bigHeight, true);

			// 2.随机生成离左上角的(X,Y)坐标，上限为 [bigWidth-smallWidth, bigHeight-smallHeight]。最好离大图左边远一点，上限不要紧挨着大图边界
			Random random = new Random();
			int randomX = random.nextInt(bigWidth - 4 * smallWidth - smallCircle) + 2 * smallWidth; // X范围：[2*smallWidth, bigWidth - 2*smallWidth - smallCircle)
			int randomY = random.nextInt(bigHeight - smallHeight - 2 * smallCircle) + smallCircle; // Y范围：[smallCircle, bigHeight - smallHeight - smallCircle)
			//logger.info("原图大小：{} x {}，大图大小：{} x {}，随机生成的坐标：(X,Y)=({},{})", originalImage.getWidth(), originalImage.getHeight(), bigImage.getWidth(), bigImage.getHeight(),
				//randomX, randomY);

			// 3.创建小图对象
			BufferedImage smallImage = new BufferedImage(smallWidth, smallHeight, BufferedImage.TYPE_4BYTE_ABGR);

			// 4.随机生成拼图轮廓数据
			int[][] slideTemplateData = getSlideTemplateData(smallWidth, smallHeight, smallCircle, smallCircleR1);

			// 5.从大图中裁剪拼图。抠原图，裁剪拼图
			cutByTemplate(bigImage, smallImage, slideTemplateData, randomX, randomY);

			sliderPuzzleInfo.setPosX(randomX);//裁剪位置 X 随机
			sliderPuzzleInfo.setPosY(randomY);//裁剪位置 Y 随机
			sliderPuzzleInfo.setBigWidth(bigWidth);//设置大宽度
			sliderPuzzleInfo.setBigHeight(bigHeight);//设置大高度
			sliderPuzzleInfo.setBigImage(bigImage);//设置大图像
			sliderPuzzleInfo.setBigImageBase64(getImageBASE64(bigImage));//设置大图像基础 64
			sliderPuzzleInfo.setSmallWidth(smallWidth);//设置小宽度
			sliderPuzzleInfo.setSmallHeight(smallHeight);//设置小高度
			sliderPuzzleInfo.setSmallImage(smallImage);//设置小图像
			sliderPuzzleInfo.setSmallImageBase64(getImageBASE64(smallImage));//设置小图像库 64
		} catch (Exception e) {
			sliderPuzzleInfo = null;
			//logger.info("创建生成滑块拼图验证码异常，e=", e);
		} finally {
			return sliderPuzzleInfo;
		}
	}

	/**
	 * 获取拼图图轮廓数据
	 * @param smallWidth
	 * @param smallHeight
	 * @param smallCircle
	 * @param r1
	 * @return 0和1，其中0表示没有颜色，1有颜色
	 */
	private static int[][] getSlideTemplateData(int smallWidth, int smallHeight, int smallCircle, int r1) {
		// 拼图轮廓数据
		int[][] data = new int[smallWidth][smallHeight];

		//拼图去掉凹凸的白色距离
		int xBlank = smallWidth - smallCircle - smallCircleR1; // 不写smallCircleR1时，凹凸为半圆
		int yBlank = smallHeight - smallCircle - smallCircleR1;

		// 圆的位置
		int rxa = xBlank / 2;
		int ryb = smallHeight - smallCircle;
		double rPow = Math.pow(smallCircle, 2);

		/**
		 * 计算需要的拼图轮廓(方块和凹凸)，用二维数组来表示，二维数组有两张值，0和1，其中0表示没有颜色，1有颜色
		 * 圆的标准方程 (x-a)²+(y-b)²=r²,标识圆心（a,b）,半径为r的圆
		 */
		for (int i = 0; i < smallWidth; i++) {
			for (int j = 0; j < smallHeight; j++) {
				// 圆在拼图下方内
				double topR = Math.pow(i - rxa, 2) + Math.pow(j - 2, 2);
				// 圆在拼图下方外
				double downR = Math.pow(i - rxa, 2) + Math.pow(j - ryb, 2);
				// 圆在拼图左侧内 || (i <= xBlank && leftR <= rPow)
				//double leftR = Math.pow(i - 2, 2) + Math.pow(j - rxa, 2);
				// 圆在拼图右侧外
				double rightR = Math.pow(i - ryb, 2) + Math.pow(j - rxa, 2);
				if ((j <= yBlank && topR <= rPow) || (j >= yBlank && downR >= rPow)
					|| (i >= xBlank && rightR >= rPow)) {
					data[i][j] = 0;
				} else {
					data[i][j] = 1;
				}
			}
		}
		return data;
	}


	/**
	 * 裁剪拼图
	 * @param bigImage - 原图规范大小之后的大图
	 * @param smallImage - 小图
	 * @param slideTemplateData - 拼图轮廓数据
	 * @param x - 坐标x
	 * @param y - 坐标y
	 */
	private static void cutByTemplate(BufferedImage bigImage, BufferedImage smallImage, int[][] slideTemplateData, int x, int y) {
		int[][] martrix = new int[3][3];
		int[] values = new int[9];
		//拼图去掉凹凸的白色距离
		int xBlank = smallWidth - smallCircle - smallCircleR1; // 不写smallCircleR1时，凹凸为半圆
		int yBlank = smallHeight - smallCircle - smallCircleR1;

		// 创建shape区域，即原图抠图区域模糊和抠出小图
		/**
		 * 遍历小图轮廓数据,创建shape区域。即原图抠图处模糊和抠出小图
		 */
		for (int i = 0; i < smallImage.getWidth(); i++) {
			for (int j = 0; j < smallImage.getHeight(); j++) {
				// 获取大图中对应位置变色
				//logger.info("随机生成的坐标：(X,Y)=({},{}),（i,j=({},{})，获取原图大小：{} x {}", x, y, i, j, x + i, y + j);
				int rgb_ori = bigImage.getRGB(x + i, y + j);

				//0和1，其中0表示没有颜色，1有颜色
				int rgb = slideTemplateData[i][j];
				if (rgb == 1) {
					// 设置小图中对应位置变色
					smallImage.setRGB(i, j, rgb_ori);

					// 大图抠图区域高斯模糊
					readPixel(bigImage, x + i, y + j, values);
					fillMatrix(martrix, values);
					bigImage.setRGB(x + i, y + j, avgMatrix(martrix));

					//边框颜色
					Color white = new Color(230,230,230);
					Color black = new Color(20,20,20);
					//左侧边界，加重高亮阴暗
					if (j < yBlank) {
						bigImage.setRGB(x, y + j, black.getRGB());
						//smallImage.setRGB(0, j, white.getRGB());
					}
				} else {
					// 这里把背景设为透明
					smallImage.setRGB(i, j, rgb_ori & 0x00ffffff);
				}
			}
		}
	}

	/**
	 * 图片转BASE64
	 *
	 * @param image
	 * @return
	 * @throws IOException
	 */
	public static String getImageBASE64(BufferedImage image) throws IOException {
		byte[] imagedata = null;
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		ImageIO.write(image, "png", bao);
		imagedata = bao.toByteArray();
		String BASE64IMAGE = Base64.getEncoder().encodeToString(imagedata);
		return BASE64IMAGE;
	}

	/**
	 * 改变图片大小
	 *
	 * @param image
	 *            原图
	 * @param width
	 *            目标宽度
	 * @param height
	 *            目标高度
	 * @return 目标图
	 */
	public static BufferedImage resizeImage(final Image image, int width, int height, boolean type) {
		BufferedImage bufferedImage;
		if (type) {
			bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		} else {
			bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		}

		final Graphics2D graphics2D = bufferedImage.createGraphics();
		graphics2D.setComposite(AlphaComposite.Src);
		// below three lines are for RenderingHints for better image quality at cost of
		// higher processing time
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2D.drawImage(image, 0, 0, width, height, null);
		graphics2D.dispose();
		return bufferedImage;
	}


	private static void readPixel(BufferedImage img, int x, int y, int[] pixels) {
		int xStart = x - 1;
		int yStart = y - 1;
		int current = 0;
		for (int i = xStart; i < 3 + xStart; i++) {
			for (int j = yStart; j < 3 + yStart; j++) {
				int tx = i;
				if (tx < 0) {
					tx = -tx;

				} else if (tx >= img.getWidth()) {
					tx = x;
				}
				int ty = j;
				if (ty < 0) {
					ty = -ty;
				} else if (ty >= img.getHeight()) {
					ty = y;
				}
				pixels[current++] = img.getRGB(tx, ty);

			}
		}

	}

	private static void fillMatrix(int[][] matrix, int[] values) {
		int filled = 0;
		for (int i = 0; i < matrix.length; i++) {
			int[] x = matrix[i];
			for (int j = 0; j < x.length; j++) {
				x[j] = values[filled++];
			}
		}
	}

	private static int avgMatrix(int[][] matrix) {
		int r = 0;
		int g = 0;
		int b = 0;
		for (int i = 0; i < matrix.length; i++) {
			int[] x = matrix[i];
			for (int j = 0; j < x.length; j++) {
				if (j == 1) {
					continue;
				}
				Color c = new Color(x[j]);
				r += c.getRed();
				g += c.getGreen();
				b += c.getBlue();
			}
		}
		return new Color(r / 8, g / 8, b / 8).getRGB();
	}

}
