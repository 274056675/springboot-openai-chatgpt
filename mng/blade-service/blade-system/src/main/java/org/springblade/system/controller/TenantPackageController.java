
package org.springblade.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.annotation.PreAuth;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.RoleConstant;
import org.springblade.core.tool.utils.Func;
import org.springblade.system.entity.TenantPackage;
import org.springblade.system.service.ITenantPackageService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springblade.core.cache.constant.CacheConstant.SYS_CACHE;

/**
 * 租户产品表 控制器
 *
 *
 */
@RestController
@AllArgsConstructor
@RequestMapping("/tenant-package")
@Api(value = "租户产品表", tags = "租户产品表接口")
public class TenantPackageController extends BladeController {

	private final ITenantPackageService tenantPackageService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入tenantPackage")
	public R<TenantPackage> detail(TenantPackage tenantPackage) {
		TenantPackage detail = tenantPackageService.getOne(Condition.getQueryWrapper(tenantPackage));
		return R.data(detail);
	}

	/**
	 * 分页 租户产品表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入tenantPackage")
	public R<IPage<TenantPackage>> list(TenantPackage tenantPackage, Query query) {
		IPage<TenantPackage> pages = tenantPackageService.page(Condition.getPage(query), Condition.getQueryWrapper(tenantPackage));
		return R.data(pages);
	}

	/**
	 * 新增 租户产品表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "新增", notes = "传入tenantPackage")
	public R save(@Valid @RequestBody TenantPackage tenantPackage) {
		return R.status(tenantPackageService.save(tenantPackage));
	}

	/**
	 * 修改 租户产品表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "修改", notes = "传入tenantPackage")
	public R update(@Valid @RequestBody TenantPackage tenantPackage) {
		return R.status(tenantPackageService.updateById(tenantPackage));
	}

	/**
	 * 新增或修改 租户产品表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "新增或修改", notes = "传入tenantPackage")
	public R submit(@Valid @RequestBody TenantPackage tenantPackage) {
		CacheUtil.clear(SYS_CACHE, Boolean.FALSE);
		return R.status(tenantPackageService.saveOrUpdate(tenantPackage));
	}


	/**
	 * 删除 租户产品表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		CacheUtil.clear(SYS_CACHE, Boolean.FALSE);
		return R.status(tenantPackageService.deleteLogic(Func.toLongList(ids)));
	}


	/**
	 * 下拉数据源
	 */
	@GetMapping("/select")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "下拉数据源", notes = "传入tenant")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R<List<TenantPackage>> select(TenantPackage tenantPackage) {
		return R.data(tenantPackageService.list(Condition.getQueryWrapper(tenantPackage)));
	}


}
