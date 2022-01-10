package com.example.service;

import com.example.dao.pojo.Category;
import com.example.vo.CategoryVo;

import java.util.List;

public interface CategoryService {
    CategoryVo findCategoryById(Long categoryId);


    List<Category> findAllCategory();
}
