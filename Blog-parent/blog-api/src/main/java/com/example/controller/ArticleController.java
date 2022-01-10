package com.example.controller;


import com.example.common.aop.LogAnnotation;
import com.example.dao.mapper.ArticleBodyMapper;
import com.example.service.ArticleService;
import com.example.vo.Result;
import com.example.vo.params.ArticleParam;
import com.example.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("articles")
public class ArticleController {

    private int i=0;

    @Autowired
    private ArticleService articleService;
@Autowired
private ArticleBodyMapper articleBodyMapper;


    @PostMapping
    //加上此注解代表要对此接口记录日志
    @LogAnnotation(module="文章",operater="获取文章列表")
    public Result listArticle(@RequestBody PageParams pageParams){
        return articleService.listArticle(pageParams);
    }
    @PostMapping("hot")
    public Result hotArticle(){
        int limit =5;
        return articleService.hotArticle(limit);
    }
    @PostMapping("new")
    public Result newArticle(){
        int limit =5;
        return articleService.newArticle(limit);
    }
    @PostMapping("listArchives")
    public Result listArchives(){
        return articleService.listArchives();
    }


    @PostMapping("view/{id}")

    public Result findArticleById(@PathVariable("id") Long articleId){
        return articleService.findArticleById(articleId);
    }

    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam){

        return articleService.publish(articleParam);

    }
}
