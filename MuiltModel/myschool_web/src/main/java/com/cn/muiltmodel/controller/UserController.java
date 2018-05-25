package com.cn.muiltmodel.controller;

import com.cn.muiltmodel.controller.vo.UserVO;
import com.cn.muiltmodel.entity.User;
import com.cn.muiltmodel.entity.dto.ResultDTO;
import com.cn.muiltmodel.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping("/user")
//@Api() 用于类,表示标识这个类是swagger的资源 tags–表示说明  value–也是说明可以使用tags替代,但是tags如果有多个值，会生成多个list
//basePath  基本路径
@Api(value = "UserController", description = "用户控制类", basePath = "/user" )
//session作用域  key是data
@SessionAttributes(value = {"data"})
public class UserController {
    @Autowired
    private UserService userService;
//    @ApiOperation()用于方法;  表示一个http请求的操作 value用于方法描述  notes用于提示内容
//    httpMethod ：HTTP请求的动作名，可选值有：”GET”, “HEAD”, “POST”, “PUT”, “DELETE”, “OPT IO NS” and “PATCH”。
//    value ：对操作的简单说明
//    notes ：对操作的详细说明。
//    protocols ：协议有三个值  http, https, wss
    @ApiOperation(value = "/count", notes = "查询总记录数", httpMethod = "GET", protocols = "http", produces = "string")
//    code ：http状态码 默认为200
    @ResponseBody
    @ApiResponse(code = 200, message = "查询成功", response = String.class)
    @RequestMapping("/count")
    public Integer count() {
        List<User> list = userService.getAll();
        //返回页数
        return list.size()%5==0?list.size()/5:list.size()/5+1;
    }

    private Integer pageIndex = 1;
    @RequestMapping("pagination")
    @ApiOperation(value = "/pagination", notes = "分页以及条件查询", httpMethod = "POST", produces = "application/json")
    @ApiResponse(code = 200, message = "查询成功")
    @ResponseBody
    public Object pagenation(@RequestBody(required = false) UserVO userVO) {
        //设置分页初始值
        if (userVO.getPageIndex() != null) {
            pageIndex = Integer.valueOf(userVO.getPageIndex().toString());
        }
        PageHelper.startPage(pageIndex, 5);
        Map<String, Object> map = new HashMap<>();
        map.put("userName", userVO.getUserName());
        map.put("userCode", userVO.getUserCode());
//        执行结果
        List<User> list = userService.getByMap(map);
        PageInfo<User> info = new PageInfo<User>(list);
        return info;
    }
    //页码


    @RequestMapping("/delete")
    @ApiOperation(value = "delete", notes = "删除用户", httpMethod = "DELETE", produces = "application/json")
    @ApiResponse(message = "删除用户", code = 200, response = ResultDTO.class)
    @ResponseBody
    public ResultDTO delete(@RequestBody User user) {
        ResultDTO result = new ResultDTO();
        if (userService.delUser(user.getId())) {
            result.setStatus(200);
            result.setMsg("恭喜，删除成功");
        } else {
            result.setStatus(400);
            result.setMsg("恭喜，删除失败");
        }
        return result;
    }
    @RequestMapping("/find")
    @ResponseBody
    @ApiOperation(value = "findById", notes = "根据Id查询用户", httpMethod = "POST", produces = "application/json")
    @ApiResponse(code = 200, message = "查询用户")
    //@PathVariable
    public ResultDTO<User> findById(@RequestBody User u) {
        ResultDTO<User> result = new ResultDTO<>();
        Map<String, Object> map = new HashMap();
        map.put("id", u.getId());
        User user = userService.findByParam(map);
        if (user != null) {
            result.setData(user);
            result.setStatus(200);
            result.setMsg("查询信息成功");
        } else {
            result.setStatus(400);
            result.setMsg("查询信息失败");
        }
        return result;
    }
    @RequestMapping("/update")
    @ResponseBody
    @ApiOperation(value = "update", notes = "修改用户", produces = "application/json", httpMethod = "PUT")
    @ApiResponse(response = ResultDTO.class, message = "修改用户", code = 200)
    public ResultDTO update(@RequestBody User user) {
        ResultDTO result = new ResultDTO();
        if (userService.updUser(user)) {
            result.setStatus(200);
            result.setMsg("恭喜，修改成功");
        } else {
            result.setStatus(400);
            result.setMsg("修改失白");
        }
        return result;
    }

    @RequestMapping("/add")
    @ResponseBody
    @ApiOperation(value = "add", notes = "添加用户", produces = "application/json", httpMethod = "POST")
    @ApiResponse(response = ResultDTO.class, message = "添加用户", code = 200)
    public ResultDTO add(@RequestBody User user) {
        ResultDTO result = new ResultDTO();
        if (userService.addUser(user)) {
            result.setData(user);
            result.setStatus(200);
            result.setMsg("恭喜，添加成功");
        } else {
            result.setStatus(400);
            result.setMsg("添加失白");
        }
        return result;

    }

    @RequestMapping("/upload")
    @ApiIgnore
    @ResponseBody
    public ResultDTO uploadHeader(@RequestBody User user,@RequestParam(value = "img",required = false) MultipartFile[] multipartFiles){
        //准备上传路径
        ResultDTO dto=new ResultDTO();
        String path="E:"+ File.separator+"uploadFile"+ File.separator;
        //转换状态
        File pathFile=new File(path);
        if(!pathFile.exists()){
            pathFile.mkdirs();
        }
        //获取文件
        for(MultipartFile file:multipartFiles){
            //重命名（命名规则为uuid+6位随机数+当前时间）
            if(file.getSize()>500000){
                dto.setStatus(400);
                dto.setMsg("对不起，上传文件太大");
                return  dto;
            }
            //判断文件后缀
            String suffix= FilenameUtils.getExtension(file.getOriginalFilename());
            if(!suffix.equals(".png")||!suffix.equals(".jpg")||!suffix.equals(".jpeg")||!suffix.equals(".temp")||!suffix.equals(".gif")){
                dto.setStatus(500);
                dto.setMsg("对不起，文件格式不正确");
                return dto;
            }
            //重命名格式
            String newName=getFileName()+suffix;
            File newFile=new File(path+newName);
            if(!newFile.exists()){
                newFile.mkdirs();
            }
            //文件上传
            try {
                file.transferTo(newFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(userService.addUser(user)){

            }
        }
        return null;
    }

    /**
     * 获取文件名
     * @return
     */
    private  String getFileName(){
        //生成uuid
        String uuid= UUID.randomUUID().toString();
        //获取当前时间
        String time=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        //获取随机数
        String rand=(new Random().nextInt(899999)+100000)+"";
        return uuid+"-"+time+"-"+rand;
    }

}
