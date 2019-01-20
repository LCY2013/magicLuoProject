package com.lcydream.project.controller;

import com.alibaba.fastjson.JSON;
import com.lcydream.project.controller.support.Anonymous;
import com.lcydream.project.entity.Response;
import com.lcydream.project.enums.ResponseEnum;
import com.lcydream.project.enums.ServiceResponseEnum;
import com.lcydream.project.login.IUserService;
import com.lcydream.project.request.UserLoginRequest;
import com.lcydream.project.request.UserRegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class IndexController extends BaseController{

    @Autowired
    IUserService userService;

    @Autowired
    JmsTemplate jmsTemplate;

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    @Anonymous
    public String login() {
        return "login";
    }

    @PostMapping("/doLogin")
    @Anonymous
    public ResponseEntity<Response> doLogin(String userName, String passWord,
                                            HttpServletResponse response) {
        UserLoginRequest request = new UserLoginRequest();
        request.setName(userName);
        request.setPassword(passWord);
        Response responseCode = userService.login(request);
        if(ResponseEnum.SUCCESS.getCode().equals(responseCode.getCode())){
            Map<String,Object> responseMap=null;
            if(responseCode.getData() instanceof Map){
                responseMap = (Map)responseCode.getData();
                response.addHeader("Set-cookie","access_token="
                        +responseMap.get("token")+";path=/;HttpOnly");
            }
        }
        return ResponseEntity.ok(responseCode);
    }

    @GetMapping("/register")
    @Anonymous
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    @Anonymous
    public @ResponseBody
    Response register(String username, String password, String mobile,
                      String realname,String avatar,String sex){
        Response data=new Response();

        UserRegisterRequest request=new UserRegisterRequest();
        request.setMobile(mobile);
        request.setUsername(username);
        request.setPassword(password);
        request.setRealname(realname);
        request.setAvatar(avatar);
        request.setSex(sex);
        try {
            Response response = userService.register(request);
            if(response.getCode().equals(ServiceResponseEnum.SERVICE_SUCCESS.getCode())){
                //发送邮件  发送卡券
                jmsTemplate.send((session) ->
                    session.createTextMessage(JSON.toJSONString(request))
                );
            }
            data.setMessage(response.getMessage());
            data.setCode(response.getCode());
        }catch(Exception e) {
            data.setMessage(ResponseEnum.FAILED.getMessage());
            data.setCode(ResponseEnum.FAILED.getCode());
        }
        return data;
    }
}
