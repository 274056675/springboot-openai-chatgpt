
package org.springblade.cgform.service.impl;

import org.springblade.cgform.entity.Category;
import org.springblade.cgform.mapper.CategoryMapper;
import org.springblade.cgform.service.ICategoryService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.tool.utils.Func;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 分类字典 服务实现类
 *
 *
 * @since 2021-05-27
 */
@Service
public class CategoryServiceImpl extends BaseServiceImpl<CategoryMapper, Category> implements ICategoryService {


    @Transactional(rollbackFor = Exception.class)
    public  void addSysCategory(Category category){
        if(Func.isNotEmpty(category.getPid())){//有子集
            Long pid = category.getPid();
            if(pid!=0){
                Category parent = baseMapper.selectById(pid);
                if(!Func.equals(parent.getHasChild(),"0")){
                    parent.setHasChild("0");
                    baseMapper.updateById(parent);
                }
                if(Func.isEmpty(parent.getPstr())){
                    String pstr=parent.getId()+"#";
                    category.setPstr(pstr);
                }else{
                    String pstr=parent.getPstr() + parent.getId()+"#";
                    category.setPstr(pstr);
                }

            }
        }

        this.save(category);
    }


    public void updateSysCategory(Category category){
        Category old = baseMapper.selectById(category.getId());

        if(Func.isNotEmpty(category.getPid())){
            Long pid = category.getPid();
            if(pid!=0){
                Category parent = baseMapper.selectById(pid);
                if(!Func.equals(parent.getHasChild(),"0")){
                    parent.setHasChild("0");
                    baseMapper.updateById(parent);
                }
            }
        }
        //设置自己
/*        String pstr = category.getPstr();
        pstr = pstr.replace(old.getPid()+"",category.getPid()+"");
        category.setPstr(pstr);*/
        this.updateById(category);

/*        //获取下面所有子集
        List<Category> allSubList = baseMapper.getAllSubList(category.getId());
        if(Func.isNotEmpty(allSubList)){
            for (Category sub:allSubList) {
                 pstr = sub.getPstr();
                pstr = pstr.replace(old.getPid()+"",category.getPid()+"");
                sub.setPstr(pstr);
                baseMapper.updateById(sub);
            }
        }*/

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public  void batchDelete(List<Long> idList){
        if(Func.isEmpty(idList)){
            return;
        }
        for (Long id:idList) {
            Category category = baseMapper.selectById(id);
            Long pid = category.getPid();
            List<Category> allSubList = baseMapper.getAllSubList(id);
            if(Func.isNotEmpty(allSubList)){
                for (Category sub:allSubList) {
                    baseMapper.deleteById(sub.getId());
                }
            }

            baseMapper.deleteById(id);
            //获取他的父级
            if(Func.isNotEmpty(pid)){
                allSubList = baseMapper.getAllSubList(pid);
                if(Func.isEmpty(allSubList)){
                    Category parent = baseMapper.selectById(pid);
                    if(Func.isNotEmpty(parent)){
                        parent.setHasChild("1");
                        baseMapper.updateById(parent);
                    }
                }
            }



        }
    }
}
