package controller;

import com.group.project.model.common.WebResult;
import com.group.project.service.UserInfoService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;


@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserInfoControllerTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private UserInfoService userInfoService;

    @Test
    public void login() {
        WebResult result = userInfoService.login(428486490);
        System.out.println(result.getData());
    }
}
