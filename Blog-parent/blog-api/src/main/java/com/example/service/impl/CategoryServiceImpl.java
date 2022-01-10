package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.dao.mapper.CategoryMapper;
import com.example.dao.pojo.Category;
import com.example.service.CategoryService;
import com.example.vo.CategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public CategoryVo findCategoryById(Long categoryId) {

        Category category = categoryMapper.selectById(categoryId);
        System.out.println(categoryId);
        CategoryVo categoryVo =new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }

    @Override
    public List<Category> findAllCategory() {
        List<Category> categories = categoryMapper.selectList(new LambdaQueryWrapper<>());
        return categories;
    }
}
