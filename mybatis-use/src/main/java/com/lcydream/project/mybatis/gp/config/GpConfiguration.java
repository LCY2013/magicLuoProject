package com.lcydream.project.mybatis.gp.config;

import lombok.Data;

import java.io.IOException;

@Data
public class GpConfiguration {

    private String scanPath;

    private MapperRegistory mapperRegistory = new MapperRegistory();

    public GpConfiguration scanPath(String scanPath) {
        this.scanPath = scanPath;
        return this;
    }

    public void build() throws IOException {
        if (null == scanPath || scanPath.length() < 1) {
            throw new RuntimeException("scan path is required .");
        }
    }

    public static void main(String[] args) throws IOException {
        new GpConfiguration().scanPath("com/lcydream/project/mybatis/gp/config/mappers").build();
    }
}
