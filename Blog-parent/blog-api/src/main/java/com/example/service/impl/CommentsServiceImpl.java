package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.dao.mapper.ArticleMapper;
import com.example.dao.mapper.CommentsMapper;
import com.example.dao.pojo.Article;
import com.example.dao.pojo.Comment;
import com.example.dao.pojo.SysUser;
import com.example.service.ArticleService;
import com.example.service.CommentsService;
import com.example.service.SysUserService;
import com.example.utils.UserThreadLocal;
import com.example.vo.CommentVo;
import com.example.vo.Result;
import com.example.vo.UserVo;
import com.example.vo.params.CommentParam;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    private CommentsMapper commentsMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Result commentsByArticleId(Long articleId) {
        /*
        * 1. 根据文章id 查询 评论列表 从comment中查询
        * 2. 根据作者的id查询作者的信息
        * 3. 判断 如果 level =1  要去查询它有没有子评论
        * 4. 如果有 根据评论id 进行查询 （parent_id）
        * */

        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,articleId);
        queryWrapper.eq(Comment::getLevel,1);
        List<Comment> comments =commentsMapper.selectList(queryWrapper);


        List<CommentVo> commentVoList =copyList(comments);
        return Result.success(commentVoList);


    }

    @Override
    public Result comment(CommentParam commentParam) {
        SysUser sysUser = UserThreadLocal.get();
        Comment comment =new Comment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent =commentParam.getParent();
        if(parent == null || parent ==0){
            comment.setLevel(1);
        }else {
            comment.setLevel(2);
        }

        comment.setParentId(parent == null ? 0 :parent);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        this.commentsMapper.insert(comment);
        Long articleId = commentParam.getArticleId();
        Article article = articleMapper.selectById(commentParam.getArticleId());

        LambdaUpdateWrapper<Article> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId,articleId).set(Article::getCommentCounts,article.getCommentCounts()+1);
        int update = articleMapper.update(null, updateWrapper);

        return Result.success(null);


    }

    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVoList =new ArrayList<>();
        comments.forEach(comment -> {
            commentVoList.add(copy(comment));
        });

        return commentVoList;
    }

    private CommentVo copy(Comment comment)  {
        CommentVo commentVo =new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);
        commentVo.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        Long authorId = comment.getAuthorId();

        UserVo userVoById = this.sysUserService.findUserVoById(authorId);
        commentVo.setAuthor(userVoById);
        Integer level = comment.getLevel();
        if(1 == level){
            Long id =comment.getId();
            List<CommentVo> commentVoList = findCommentsByParentId(id);
            commentVo.setChildrens(commentVoList);
        }
        if(level>1){
            Long toUid = comment.getToUid();
            UserVo userVoById1 = this.sysUserService.findUserVoById(toUid);
            commentVo.setToUser(userVoById1);
        }

        return commentVo;


    }

    private List<CommentVo> findCommentsByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper  =new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId,id);
        queryWrapper.eq(Comment::getLevel,2);
        return copyList(commentsMapper.selectList(queryWrapper));

    }








}
