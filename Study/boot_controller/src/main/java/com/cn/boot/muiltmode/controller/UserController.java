package com.cn.boot.muiltmode.controller;

import com.cn.boot.muiltmode.dto.ResultDto;
import com.cn.boot.muiltmode.entity.User;
import com.cn.boot.muiltmode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

//如果想返回一个页面就必须用controller，不能用restcontroller
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //    ,@Cacheable注解代表从缓存中查询指定的key,如果有,从缓存中取,不再执行方法.如果没有则执行方法,并且将方法的返回值和指定的key关联起来,放入到缓存中.
//   @CacheEvict则是从缓存中清除指定的key对应的数据
    @Cacheable( value = "rediss" ,key="'getall'")
    @RequestMapping(value = "/getall")
    public Object getAll(Model model) {
        List<User> lists=userService.getAll();
        model.addAttribute("lists",lists);
        return "index1";
    }

    @RequestMapping("/toAdd")
    public String toAdd(){ return  "add";}

    @RequestMapping(value = {"/add"})
    public Object insUser(User user,Model model) {
        Boolean bool=userService.addUser(user);
        if(bool){
            model.addAttribute("msg","恭喜添加"+user.getUserName()+"成功");
            return "forward:/user/getall";
        }else{
            model.addAttribute("msg","添加失败");
            return "add";
        }
    }

    @CacheEvict(value = "rediss",key = "'find'+#id")
    @RequestMapping(value = {"/del/{id}"})
    public Object delUser(Model model, @PathVariable("id")Integer id) {
        Boolean result=userService.delUser(id);
        if(result){
            model.addAttribute("msg","恭喜删除成功");
        }else{
            model.addAttribute("msg","删除成功");
        }
        return "forward:/user/getall";
    }
    @Cacheable(value = "rediss",key = "'find'+#id")
    @RequestMapping(value = {"/upd"})
    public Object updUser(Model model,User user) {
        Boolean bool=userService.updUser(user);
        if(bool){
            model.addAttribute("msg","恭喜修改"+user.getUserName()+"成功");
            return "forward:/user/getall";
        }else{
            model.addAttribute("msg","修改失败");
            return "show";
        }
    }
    @Cacheable(value = "rediss",key = "'find'+#id")
    @RequestMapping(value = "/find/{id}")
    public Object findByCode(Model model,@PathVariable("id") Integer id) {
        User lists= userService.findByCode(id);
        model.addAttribute("list",lists);
        return "show";
    }

//返回值和静态页面的名字一致
    @RequestMapping(value = "/thy",method = RequestMethod.GET)
    public String hello(Model model){
        model.addAttribute("name","甄三比");
        model.addAttribute("alls",userService.getAll());
        return "aadsf";
    }


}
