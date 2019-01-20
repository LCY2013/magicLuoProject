package com.lcydream.project.services.login;

import com.lcydream.project.add.IOrderService;
import com.lcydream.project.dal.entity.User;
import com.lcydream.project.entity.Request;
import com.lcydream.project.entity.Response;
import com.lcydream.project.enums.ResponseEnum;
import com.lcydream.project.login.IUserService;
import com.lcydream.project.request.UserLoginRequest;
import com.lcydream.project.request.UserRegisterRequest;
import com.lcydream.project.services.jwt.JwtService;
import com.lcydream.project.utils.JwtTokenUtils;
import com.lcydream.project.vlid.VlidParam;
import com.lcydream.project.vlid.VlidResult;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lcydream.project.dal.persistence.UserMapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * UserServiceImpl
 *
 * @author Luo Chun Yun
 * @date 2018/9/16 10:51
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    IOrderService orderService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    JwtService jwtService;

    /**
     * 日志组件
     */
    private static Logger logger = Logger.getLogger(UserServiceImpl.class);


    private final static String NAME = "root";
    private final static String PASSWORD = "123456";

    /**
     * 登录实现
     * @param request 登录参数
     * @return Response
     */
    @Override
    public Response login(UserLoginRequest request) {
        Response response = new Response();

        //校验参数
        VlidResult vlidResult = VlidParam.check(request);
        if(!vlidResult.isSuccess()){
            response.setMessage(vlidResult.getMassage());
            response.setCode(ResponseEnum.VLIDPARAMFAILED.getCode());
            return response;
        }
        User user =
                userMapper.selectByUserInfo(request.getName(), request.getPassword());
        if(user == null){
            response.setMessage("用户信息不存在，请先注册！");
            response.setCode(ResponseEnum.VLIDPARAMFAILED.getCode());
            return response;
        }
        //查看登录账号是否合法
        //if(NAME.equals(request.getName()) && PASSWORD.equals(request.getPassword())){
        if(user.getUsername().equals(request.getName())
                && user.getPassword().equals(request.getPassword())){
            //OrderRequest orderRequest = new OrderRequest();
            //orderRequest.setData("123");
            //orderService.addOrder(orderRequest);
            String jwtToken = jwtService.generatorJWT(user);
            Map<String, Object> dataMap = new HashMap<>(1);
            //TODO 加上JWT的Token
            dataMap.put("token",jwtToken);
            response.setCode(ResponseEnum.SUCCESS.getCode());
            response.setMessage(ResponseEnum.SUCCESS.getMessage());
            response.setData(dataMap);
            return response;
        }

        //账号不合法
        response.setCode(ResponseEnum.REQUESTFAILED.getCode());
        response.setMessage(ResponseEnum.REQUESTFAILED.getMessage());
        return response;
    }

    /**
     * 修改用户信息
     * @param request 请求参数
     * @return Response
     */
    @Override
    public Response debit(Request request) {
        Response response = new Response();
        response.setCode(ResponseEnum.SUCCESS.getCode());
        response.setMessage(ResponseEnum.SUCCESS.getMessage());
        return response;
    }

    /**
     * 注册新用户
     * @param userInfo 用户信息
     * @return 返回响应码
     */
    @Override
    public Response register(UserRegisterRequest userInfo) {
        Response response = new Response();
        response.setCode(ResponseEnum.FAILED.getCode());
        response.setMessage(ResponseEnum.FAILED.getMessage());
        /**
         * 判断是否为空
         */
        if(userInfo == null){
            return response;
        }
        User user = new User();
        user.setUsername(userInfo.getUsername());
        user.setAvatar(userInfo.getAvatar());
        user.setCreateTime(new Date());
        user.setMobile(userInfo.getMobile());
        user.setPassword(userInfo.getPassword());
        user.setRealname(userInfo.getRealname());
        user.setSex(userInfo.getSex());
        user.setStatus(userInfo.getStatus());
        int insert = userMapper.insertSelective(user);
        if(insert > 0){
            response.setCode(ResponseEnum.SUCCESS.getCode());
            response.setMessage(ResponseEnum.SUCCESS.getMessage());
            return response;
        }
        return response;
    }

    /**
     * 检查权限是否存在
     * @param request 请求实体
     * @return 返回响应信息
     */
    @Override
    public Response checkAuth(Request request) {
        //构建返回响应信息
        Response response = new Response();
        response.setCode(ResponseEnum.FAILED.getCode());
        response.setMessage(ResponseEnum.FAILED.getMessage());
        try {
            VlidResult vlidResult = VlidParam.check(request);
            if(!vlidResult.isSuccess()){
                response.setMessage(vlidResult.getMassage());
                return response;
            }
            Claims claims = jwtService.parseJWT(request.getToken());
            Date expiration = claims.getExpiration();
            Map<String,Object> dataMap = new HashMap<>(1);
            dataMap.put("id",claims.get("id"));
            if(expiration.getTime() - System.currentTimeMillis() < 30*1000){
                dataMap.put("flush","yes");
                dataMap.put("username",claims.get("username"));
                dataMap.put("password",claims.get("password"));
            }
            response.setData(dataMap);
            response.setCode(ResponseEnum.SUCCESS.getCode());
            response.setMessage(ResponseEnum.SUCCESS.getMessage());
        }catch (ExpiredJwtException e){
            logger.error("Expire :"+e);
            response.setCode(ResponseEnum.TOKEN_EXPIRE.getCode());
            response.setMessage(ResponseEnum.TOKEN_EXPIRE.getMessage());
        }catch (SignatureException e){
            logger.error("SignatureException :"+e);
            response.setCode(ResponseEnum.SIGNATURE_ERROR.getCode());
            response.setMessage(ResponseEnum.SIGNATURE_ERROR.getMessage());
        }catch (Exception e){
            return response;
        }finally {
            logger.info("checkAuth Response:["+response+"]!");
        }
        return response;
    }

}
