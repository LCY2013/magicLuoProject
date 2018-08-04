package com.lcydream.project.mvc;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * MagicLuoController
 *
 * @author Luo Chun Yun
 * @date 2018/7/15 16:24
 */
@Controller
public class MagicLuoController {

	/**
	 * /hello/{name}/hello.json
	 * /hello/11/hello.json
	 * @return
	 */
	@RequestMapping(value = "/hello/{name}/hello",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Object hello(@RequestParam("age")int age,
	                          @PathVariable("name")String name,
	                          //自动赋值gn
	                          HttpServletRequest request,
	                          HttpServletResponse response){
        System.out.println("name:"+name);
        System.out.println("age:"+age);
		return JSON.toJSONString(name);
	}

	@RequestMapping(value = "/hello",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Object hello(){
		return "11";
	}
}
