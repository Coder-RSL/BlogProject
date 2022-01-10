package com.example.controller;

import com.example.service.TagService;
import com.example.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tags")
public class TagController {

    @Autowired
    private TagService tagService;


    @GetMapping("hot")
    public Result hot(){
        int limit= 6;
        Result hot = tagService.hot(limit);
        return  hot;
    }
}
