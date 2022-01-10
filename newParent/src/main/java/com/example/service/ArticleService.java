package com.example.service;

import com.example.vo.Result;
import com.example.vo.params.PageParams;

public interface ArticleService {
    Result listArticle(PageParams pageParams);

    Result hotArticle(int limit);

    Result newArticle(int limit);

    Result listArchives();
}
