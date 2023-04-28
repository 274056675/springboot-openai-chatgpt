
package org.springblade.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.system.entity.DictBiz;
import org.springblade.system.service.IDictBizService;
import org.springblade.system.vo.DictBizVO;
import org.springblade.system.wrapper.DictBizWrapper;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static org.springblade.core.cache.constant.CacheConstant.DICT_CACHE;

/**
 * 控制器
 *
 *
 */
@NonDS
@RestController
@AllArgsConstructor
@RequestMapping("/dict-biz")
@Api(value = "业务字典", tags = "业务字典")
public class DictBizController extends BladeController {

	private final IDictBizService dictService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入dict")
	public R<DictBizVO> detail(DictBiz dict) {
		DictBiz detail = dictService.getOne(Condition.getQueryWrapper(dict));
		return R.data(DictBizWrapper.build().entityVO(detail));
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "字典编号", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "dictValue", value = "字典名称", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "列表", notes = "传入dict")
	public R<List<DictBizVO>> list(@ApiIgnore @RequestParam Map<String, Object> dict) {
		List<DictBiz> list = dictService.list(Condition.getQueryWrapper(dict, DictBiz.class).lambda().orderByAsc(DictBiz::getSort));
		return R.data(DictBizWrapper.build().listNodeVO(list));
	}

	/**
	 * 顶级列表
	 */
	@GetMapping("/parent-list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "字典编号", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "dictValue", value = "字典名称", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "列表", notes = "传入dict")
	public R<IPage<DictBizVO>> parentList(@ApiIgnore @RequestParam Map<String, Object> dict, Query query) {
		return R.data(dictService.parentList(dict, query));
	}

	/**
	 * 子列表
	 */
	@GetMapping("/child-list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "字典编号", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "dictValue", value = "字典名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "parentId", value = "字典名称", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "列表", notes = "传入dict")
	public R<List<DictBizVO>> childList(@ApiIgnore @RequestParam Map<String, Object> dict, @RequestParam(required = false, defaultValue = "-1") Long parentId) {
		return R.data(dictService.childList(dict, parentId));
	}

	/**
	 * 获取字典树形结构
	 */
	@GetMapping("/tree")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public R<List<DictBizVO>> tree() {
		List<DictBizVO> tree = dictService.tree();
		return R.data(tree);
	}

	/**
	 * 获取字典树形结构
	 */
	@GetMapping("/parent-tree")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public R<List<DictBizVO>> parentTree() {
		List<DictBizVO> tree = dictService.parentTree();
		return R.data(tree);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入dict")
	public R submit(@Valid @RequestBody DictBiz dict) {
		CacheUtil.clear(DICT_CACHE);
		return R.status(dictService.submit(dict));
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		CacheUtil.clear(DICT_CACHE);
		return R.status(dictService.removeDict(ids));
	}

	/**
	 * 获取字典
	 */
	@GetMapping("/dictionary")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "获取字典", notes = "获取字典")
	public R<List<DictBiz>> dictionary(String code) {
		List<DictBiz> tree = dictService.getList(code);
		return R.data(tree);
	}

	/**
	 * 获取字典树
	 */
	@GetMapping("/dictionary-tree")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "获取字典树", notes = "获取字典树")
	public R<List<DictBizVO>> dictionaryTree(String code) {
		List<DictBiz> tree = dictService.getList(code);
		return R.data(DictBizWrapper.build().listNodeVO(tree));
	}


}
