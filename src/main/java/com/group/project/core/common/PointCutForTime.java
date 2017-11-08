package com.group.project.core.common;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 *  输出接口调用效率
 */
public class PointCutForTime {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Object around(ProceedingJoinPoint point) throws Throwable {
        Date date = new Date();
        Signature signature = point.getSignature();
        String className = signature.getDeclaringTypeName();
        String mothodName = signature.getName();
        Object obj = point.proceed();
        Date date2 = new Date();
        this.logger.info("========[" + className + "]的方法[" + mothodName + "]调用时间(毫秒)：" + (date2.getTime() - date.getTime()) + "========");
        return obj;
    }
}
