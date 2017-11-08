package com.group.project.service.impl;

import com.group.project.dao.UserInfoMapper;
import com.group.project.model.common.WebResult;
import com.group.project.service.UserInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {
    private static final Logger logger = Logger.getLogger(UserInfoServiceImpl.class);

    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public WebResult login(Integer steamId) {
//        UserModel model=userInfoMapper.findById(steamId);
        return WebResult.success();
    }
}
