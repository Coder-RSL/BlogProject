package com.example.service.impl;

import com.example.dao.mapper.TagMapper;
import com.example.dao.pojo.Tag;
import com.example.service.TagService;
import com.example.vo.Result;
import com.example.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;


    @Override
    public List<TagVo> findTagsByArticleId(long articleId) {
        //mybatisplus无法进行多表查询
        List<Tag> tags = tagMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }

    public TagVo copy(Tag tag){
        TagVo tagVo =new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }
    public List<TagVo> copyList(List<Tag> tagList){
        List<TagVo> tagVoList =new ArrayList<>();
        for(Tag tag:tagList){
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    @Override
    public Result hot(int limit) {

       List<Long> tagIds = tagMapper.findHotsTagIds(limit);
       if(CollectionUtils.isEmpty(tagIds)){
            return  Result.success(Collections.emptyList());
       }
       List<Tag> tagList= tagMapper.findTagByTagIds(tagIds);
       return Result.success(tagList);
    }
}
