package com.example.controller;

import com.example.dao.pojo.Category;
import com.example.service.CategoryService;
import com.example.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RestController
@RequestMapping("/categorys")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;



    @GetMapping({"","detail"})
    public Result findAll(){

       List<Category> categoryList =categoryService.findAllCategory();
       return Result.success(categoryList);
    }
}
