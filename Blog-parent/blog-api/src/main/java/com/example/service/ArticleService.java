package com.example.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.vo.ArticleBodyVo;
import com.example.vo.ArticleVo;
import com.example.vo.params.ArticleParam;
import com.example.vo.params.PageParams;
import com.example.vo.Result;

public interface ArticleService{
    Result listArticle(PageParams pageParams);

    Result hotArticle(int limit);

    Result newArticle(int limit);

    Result listArchives();

    Result findArticleById(Long articleId);

    ArticleBodyVo findArticleBodyById(Long articleId);

    //文章服务
    Result publish(ArticleParam articleParam);

}
