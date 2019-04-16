package cn.doublehh.adutils;

import cn.doublehh.adutils.enums.ObjectClassEnum;
import cn.doublehh.adutils.model.ADUser;
import cn.doublehh.adutils.model.OU;
import cn.doublehh.adutils.service.ADUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.directory.DirContext;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AdUtilsApplicationTests {

    @Autowired
    private ADUserService adUserService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void addAdUser() {
        ADUser adUser = new ADUser();
        adUser.setObjectClass(ObjectClassEnum.USER.getObjectClass());
        adUser.setDistinguishedName("CN=会议室103,OU=会议室,OU=浙江万里学院,DC=NBSANKE,DC=TOP");
//        adUser.setSn("陈");
//        adUser.setGivenName("玮叶");
        adUser.setDisplayName("会议室103");
        adUser.setUserPrincipalName("room103@nbsanke.top");
        adUser.setSAMAccountName("room103");
//        adUser.setUnicodePwd("1120023921Hh");
        adUserService.add(adUser);
    }

    @Test
    public void delete() {
        adUserService.delete("CN=陈玮叶,OU=图书馆,OU=浙江万里学院,DC=NBSANKE,DC=TOP");
    }

    @Test
    public void update() {
        adUserService.update("CN=陈玮叶,OU=图书馆,OU=浙江万里学院,DC=NBSANKE,DC=TOP", "displayName", "陈玮叶", DirContext.REPLACE_ATTRIBUTE);
        adUserService.update("CN=陈玮叶,OU=图书馆,OU=浙江万里学院,DC=NBSANKE,DC=TOP", "mail", "2015014093@nbsanke.top", DirContext.ADD_ATTRIBUTE);
    }

    @Test
    public void searchADUser() {
        List<ADUser> adUsers = adUserService.searchADUser("OU=图书馆,OU=浙江万里学院,DC=NBSANKE,DC=TOP", ADUser.class);
        System.out.println(adUsers);
    }

    @Test
    public void searchByUserName() {
        ADUser adUser = adUserService.searchByUserName("OU=图书馆,OU=浙江万里学院,DC=NBSANKE,DC=TOP", "2015014074", ADUser.class);
        System.out.println(adUser);
    }

    @Test
    public void getStruct() {
        OU ou = new OU();
        ou.setName("浙江万里学院");
        ou.setOu("浙江万里学院");
        adUserService.getStruct("DC=NBSANKE,DC=TOP", ou);
        System.out.println(ou);
    }

    @Test
    public void authenricate() {
        ADUser authenricate = adUserService.authenricate("DC=NBSANKE,DC=TOP", "2015014093", "1120023921Hh", ADUser.class);
        System.out.println(authenricate);
    }
}
