package org.springblade.mng.config.pool;

import lombok.extern.slf4j.Slf4j;
import org.springblade.mng.cgform.model.AccumulatorRecursiveActionParam;
import org.springblade.mng.cgform.service.ICgformEnhanceSqlService;
import org.springblade.mng.config.exception.BusinessException;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;


@Slf4j
public class AccumulatorRecursiveAction extends RecursiveAction {
	private final int start;

	private final int end;

	private AccumulatorRecursiveActionParam param;

	private final int LIMIT = 200;
	private ICgformEnhanceSqlService sqlService;
	private ServletRequestAttributes sra;


	public AccumulatorRecursiveAction(int start, int end, AccumulatorRecursiveActionParam param, ServletRequestAttributes sra) {
		this.start = start;
		this.end = end;
		this.param = param;
		this.sqlService = param.getSqlService();
		this.sra = sra;
	}

	@Override
	protected void compute() {
		try {
			if ((end - start) <= LIMIT) {
				List addDataList = new ArrayList();
				for (int i = start; i < end; i++) {
					addDataList.add(param.getImportList().get(i));
				}
				sqlService.saveBatchCodeOnlineTable(param.getHead(), param.getCgformFieldList(), addDataList, param.getBatchCode(), param.getImportList().size(), param.getSqlMapper(), param.getOtherMap(), param.getJava(), sra);
			} else {
				int mid = (start + end) / 2;
				AccumulatorRecursiveAction left = new AccumulatorRecursiveAction(start, mid, param, sra);
				AccumulatorRecursiveAction right = new AccumulatorRecursiveAction(mid, end, param, sra);
				left.fork();
				right.fork();
				left.join();
				right.join();
			}
		} catch (BusinessException e) {
			e.getMessage();
		}

	}

}
