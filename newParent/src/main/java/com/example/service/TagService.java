package com.example.service;

import com.example.vo.Result;
import com.example.vo.TagVo;

import java.util.List;

public interface TagService {
    List<TagVo> findTagsByArticleId(long articleId);

    Result hot(int limit);
}
