package com.lcydream.project.business.controller;

import com.lcydream.project.add.IOrderService;
import com.lcydream.project.business.controller.support.ResponseData;
import com.lcydream.project.business.controller.support.ResponseEnum;
import com.lcydream.project.entity.Response;
import com.lcydream.project.login.IUserService;
import com.lcydream.project.request.OrderRequest;
import com.lcydream.project.request.UserLoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 */
@Controller
@RequestMapping("/index/")
public class IndexController extends BaseController{

    @Autowired
    IOrderService orderServices;

    @Autowired
    IUserService userService;


    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(HttpServletRequest request){
        if(request.getSession().getAttribute("user")==null){
            return "/login";
        }
        return "/index";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){


        return "/login";
    }

    @RequestMapping(value="/submitLogin",method=RequestMethod.POST)
    @ResponseBody
    public ResponseData submitLogin(HttpServletRequest request, String loginname, String password){
        UserLoginRequest userLoginRequest= new UserLoginRequest();
        userLoginRequest.setName(loginname);
        userLoginRequest.setPassword(password);
        Response response = userService.login(userLoginRequest);
        if(ResponseEnum.SUCCESS.getCode().equals(response.getCode())){
            request.getSession().setAttribute("user","root");
            return setEnumResult(ResponseEnum.SUCCESS, "/");
        }
        ResponseData data=new ResponseData();
        data.setMessage(response.getMessage());
        data.setStatus(ResponseEnum.FAILED.getCode());
        return data;
    }

    /**
     * 退出
     * @return
     */
    @RequestMapping(value="/logout",method =RequestMethod.GET)
    public String logout(HttpServletRequest request){
        try {
            request.getSession().removeAttribute("user");
        } catch (Exception e) {
            LOG.error("errorMessage:" + e.getMessage());
        }
        return redirectTo("/index/login.shtml");
    }
}
