package org.springblade.mng.cgform.model.file;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FileIndexModel implements Serializable {

	//资源总量 文档数量 视频数量 用户数量
	@Data
	public static class TopModel{
		private Long cou;
		private Long jrscl;
		private List<Xy> xyList;
	}

	//视频数量
	@Data
	public static class kv{
		private String k;
		private String v;
	}
	//访问量
	@Data
	public static class Fwl{
		private String tian;
		private String type;
		private String userCou;
		private String viewCou;
	}

	//视频数量
	@Data
	public static class Xy{
		private String x;
		private String y;

		public Xy(String x, String y) {
			this.x = x;
			this.y = y;
		}
	}
}
