package com.example.service;

import com.example.dao.pojo.Tag;
import com.example.vo.Result;
import com.example.vo.TagVo;
import java.util.List;

public interface TagService {


    List<Tag> findAllTag() ;

    List<TagVo> findTagsByArticleId(long articleId);

    Result hot(int limit);
}
