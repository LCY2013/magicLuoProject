package com.lcydream.project.dal.resultmap;

import com.lcydream.project.dal.dao.Author;
import lombok.Data;
@Data
public class BlogResultMap {

    private Integer bid;

    private String name;

    private Author author;
}
