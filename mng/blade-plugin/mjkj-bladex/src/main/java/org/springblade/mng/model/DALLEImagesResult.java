package org.springblade.mng.model;

import lombok.Data;

import java.util.List;

@Data
public class DALLEImagesResult {
	private Integer code;
	private String resultStr;

	@Data
	public static class ResultStrModel{
		private Integer created;
		private List<DataModel> data;
	}

	@Data
	public static class DataModel{
		private String url;
	}

	//{"code":200,"resultStr":"{\n  \"created\": 1680356220,\n  \"data\": [\n    {\n      \"url\": \"https://oaidalleapiprodscus.blob.core.windows.net/private/org-S0lxwsCoVGkiphAv5HurKD8J/user-2PLmhwqTS44Ero7SNsyJKFVq/img-yXiKXMZj6jKt8XI512WVKVj3.png?st=2023-04-01T12%3A37%3A00Z&se=2023-04-01T14%3A37%3A00Z&sp=r&sv=2021-08-06&sr=b&rscd=inline&rsct=image/png&skoid=6aaadede-4fb3-4698-a8f6-684d7786b067&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2023-03-31T18%3A03%3A29Z&ske=2023-04-01T18%3A03%3A29Z&sks=b&skv=2021-08-06&sig=ijZpNor6nnO78a%2BY/7AdAsvoS/gPeVp%2B1pJuWq8JTig%3D\"\n    }\n  ]\n}\n"}

}
