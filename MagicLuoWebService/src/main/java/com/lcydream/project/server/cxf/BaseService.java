package com.lcydream.project.server.cxf;

import com.lcydream.project.server.cxf.response.ResponseResult;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * BaseService
 *
 * @author Luo Chun Yun
 * @date 2018/8/25 20:30
 */
public interface BaseService {

    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    ResponseResult index();
}
