package com.group.project.service;

import com.group.project.model.common.WebResult;

public interface UserInfoService {
    WebResult login(Integer steamId);
}
