package com.group.project.controller;

import com.group.project.base.BaseController;
import com.group.project.model.common.WebResult;
import com.group.project.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

public class UserInfoController extends BaseController{

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    WebResult login(Integer id, HttpServletRequest request) {
        WebResult result;
        try {
            result=userInfoService.login(id);
        }catch (Exception e){
            logger.error("登录失败",e);
            return WebResult.fail();
        }
        return result;
    }
}
