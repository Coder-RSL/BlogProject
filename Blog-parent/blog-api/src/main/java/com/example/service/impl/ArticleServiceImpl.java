package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dao.dos.Archives;
import com.example.dao.mapper.ArticleMapper;
import com.example.dao.mapper.ArticleBodyMapper;
import com.example.dao.mapper.ArticleTagMapper;
import com.example.dao.pojo.Article;
import com.example.dao.pojo.ArticleBody;
import com.example.dao.pojo.ArticleTag;
import com.example.dao.pojo.SysUser;
import com.example.service.*;
import com.example.utils.UserThreadLocal;
import com.example.vo.ArticleBodyVo;
import com.example.vo.ArticleVo;
import com.example.vo.Result;
import com.example.vo.TagVo;
import com.example.vo.params.ArticleParam;
import com.example.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired(required = false)
    private ArticleMapper articleMapper;
    @Autowired
    private TagService tagService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private ThreadService threadService;

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
            articleVos.add(copy(record,true,true,true,true));

        }
        return articleVos;
    }

    private ArticleVo copy(Article article,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory){
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
       if(isBody){
           Long bodyId =article.getBodyId();
           articleVo.setBody(findArticleBodyById(bodyId));

       }
       if(isCategory){
           Long categoryId =article.getCategoryId();
           articleVo.setCategory(categoryService.findCategoryById(categoryId));
       }
       articleVo.setId(article.getId().toString());

        return articleVo;
    }

    @Override
    public ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        System.out.println("articleBody*"+articleBody);

        ArticleBodyVo articleBodyVo =new ArticleBodyVo();



        articleBodyVo.setContent(articleBody.getContent());


        System.out.println("articleBodyVo"+articleBodyVo);

        return articleBodyVo;
    }

    @Override
    public Result publish(ArticleParam articleParam) {
        /*1. 发布文章 目的 构建Article对象
        * 2. 作者id 当前登录用户
        * 3. 标签 加入当关联列表当中
        * 4. body 内容存储
        * */

        System.out.println("*******");

        SysUser sysUser = UserThreadLocal.get();

        System.out.println("UserThread"+Thread.currentThread());
        System.out.println(sysUser);

        Article article =new Article();


         article.setAuthorId(sysUser.getId());
        //插入后生成一个文章id



        article.setTitle(articleParam.getTitle());
        article.setWeight(0);
        article.setSummary(articleParam.getSummary());
        article.setCommentCounts(0);
        article.setViewCounts(0);
        article.setCreateDate(System.currentTimeMillis());
        article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));

        this.articleMapper.insert(article);
        List<TagVo> tagVoList =articleParam.getTags();
        if(tagVoList!=null ){
            tagVoList.forEach(tagVo -> {
                Long articleId = article.getId();
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(Long.parseLong(tagVo.getId()));
                articleTag.setArticleId(articleId);
                articleTagMapper.insert(articleTag);
            });
        }




//body

        ArticleBody articleBody =new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBodyMapper.insert(articleBody);
        article.setBodyId(articleBody.getId());


        articleMapper.updateById(article);
        Map<String,String> map = new HashMap<>();
        map.put("id",article.getId().toString());
        return Result.success(map);
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
        System.out.println(archives1.getYear());
        return Result.success(archives);
    }

    @Override
    public Result findArticleById(Long articleId) {

        /*
        * 1. 查询文章信息
        * 2. 根据bodyid 和 categoryid去查询关联
        * 3.
        * */

        Article article =this.articleMapper.selectById(articleId);
        ArticleVo articleVo = copy(article, true, true,true,true);

        threadService.updateArticleviewCount(articleMapper,article);

        return Result.success(articleVo);
    }

    @Autowired
    private ArticleBodyMapper articleBodyMapper;


}
