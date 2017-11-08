package com.group.project.base;


import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
    protected static Logger logger = Logger.getLogger(BaseController.class);

    protected Object getUser(HttpServletRequest request) {
        return request.getSession().getAttribute("user");
    }
}
