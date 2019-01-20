package com.lcydream.project.server.cxf.service;

import com.lcydream.project.server.cxf.BaseService;
import com.lcydream.project.server.cxf.entity.User;
import com.lcydream.project.server.cxf.response.ResponseResult;

import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * UserService
 * 定义cxf的接口
 * @author Luo Chun Yun
 * @date 2018/8/25 20:23
 */
@WebService
@Path("/user/")
public interface UserService extends BaseService {

    @GET
    @Path("getUserList")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    ResponseResult getUserList();

    /**
     * 根据id查询用户信息
     * @param id 用户id
     * @return ResponseResult
     */
    @GET
    @Path(("{id}"))
    @Produces({MediaType.APPLICATION_JSON})
    ResponseResult getUserById(@PathParam("id") int id);

    @POST
    @Path(("add"))
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    ResponseResult addUser(User user);

    @PUT
    @Path(("update"))
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    ResponseResult updateUser(User user);

    /**
     * http://ip:port/users/1
     * @param id 用户id
     * @return ResponseResult
     */
    @DELETE
    @Path(("{id}"))
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    ResponseResult deleteUserById(@PathParam("id") int id);



}
