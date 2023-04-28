
package org.springblade.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;
import org.springblade.core.tool.utils.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 实体类
 *
 *
 */
@Data
@TableName("blade_tenant")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Tenant对象", description = "Tenant对象")
public class Tenant extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 租户ID
	 */
	@ApiModelProperty(value = "租户ID")
	private String tenantId;
	/**
	 * 租户名称
	 */
	@ApiModelProperty(value = "租户名称")
	private String tenantName;
	/**
	 * 域名地址
	 */
	@ApiModelProperty(value = "域名地址")
	private String domainUrl;
	/**
	 * 系统背景
	 */
	@ApiModelProperty(value = "系统背景")
	private String backgroundUrl;
	/**
	 * 联系人
	 */
	@ApiModelProperty(value = "联系人")
	private String linkman;
	/**
	 * 联系电话
	 */
	@ApiModelProperty(value = "联系电话")
	private String contactNumber;
	/**
	 * 联系地址
	 */
	@ApiModelProperty(value = "联系地址")
	private String address;
	/**
	 * 账号额度
	 */
	@ApiModelProperty(value = "账号额度")
	private Integer accountNumber;
	/**
	 * 过期时间
	 */
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
	@ApiModelProperty(value = "过期时间")
	private Date expireTime;
	/**
	 * 产品包ID
	 */
	@JsonSerialize(nullsUsing = NullSerializer.class)
	@ApiModelProperty(value = "产品包ID")
	private Long packageId;
	/**
	 * 数据源ID
	 */
	@JsonSerialize(nullsUsing = NullSerializer.class)
	@ApiModelProperty(value = "数据源ID")
	private Long datasourceId;
	/**
	 * 授权码
	 */
	@ApiModelProperty(value = "授权码")
	private String licenseKey;


}
