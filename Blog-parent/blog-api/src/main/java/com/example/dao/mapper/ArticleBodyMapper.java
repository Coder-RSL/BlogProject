package com.example.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.dao.pojo.ArticleBody;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleBodyMapper extends BaseMapper<ArticleBody> {



    ArticleBody selectById(Long bodyId);

    void insertArticleBody(Long id, String content, String contentHtml, Long articleId);
}
