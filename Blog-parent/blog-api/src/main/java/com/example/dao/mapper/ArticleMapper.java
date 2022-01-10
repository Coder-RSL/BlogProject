package com.example.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.dao.dos.Archives;
import com.example.dao.pojo.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {


    List<Archives> listArchives();
}
