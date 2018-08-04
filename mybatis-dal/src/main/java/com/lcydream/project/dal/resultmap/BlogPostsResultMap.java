package com.lcydream.project.dal.resultmap;

import com.lcydream.project.dal.dao.Posts;
import lombok.Data;

import java.util.List;
@Data
public class BlogPostsResultMap{

    private Integer bid;

    private String name;

    private Integer authorId;

    private List<Posts> posts;

}
