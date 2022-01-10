package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dao.dos.Archives;
import com.example.dao.mapper.ArticleMapper;
import com.example.dao.pojo.Article;
import com.example.service.ArticleService;
import com.example.service.SysUserService;
import com.example.service.TagService;
import com.example.vo.ArticleVo;
import com.example.vo.Result;
import com.example.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired(required = false)
    private ArticleMapper articleMapper;
    @Autowired
    private TagService tagService;
    @Autowired
    private SysUserService sysUserService;

    @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> records = articlePage.getRecords();
        List<ArticleVo> articleList =copyList(records);
        return Result.success(articleList);

    }

    private List<ArticleVo> copyList(List<Article> records) {
        List<ArticleVo> articleVos = new ArrayList<>();

        for(Article record :records){
            articleVos.add(copy(record,true,true));

        }
        return articleVos;
    }

    private ArticleVo copy(Article article,boolean isTag,boolean isAuthor){
        ArticleVo articleVo =new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);

        articleVo.setCreateDate((new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm")));
       if(isTag){
           Long id = article.getId();
           articleVo.setTags(tagService.findTagsByArticleId(id));
       }if(isAuthor){
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }

        return articleVo;
    }

    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);

        //select id,title from article order by view_counts desc limit 5

        List<Article> articleList = articleMapper.selectList(queryWrapper);

        return Result.success(articleList);
    }

    @Override
    public Result newArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);

        //select id,title from article order by view_counts desc limit 5

        List<Article> articleList = articleMapper.selectList(queryWrapper);

        return Result.success(articleList);

    }

    @Override
    public Result listArchives() {
       List<Archives> archives= articleMapper.listArchives();
        Archives archives1 = archives.get(0);
        System.out.println("sdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+archives1.getYear());
        return Result.success(archives);
    }
}
