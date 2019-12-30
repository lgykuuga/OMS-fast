package com.lgy.framework.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

import com.lgy.framework.shiro.realm.UserRealm;
import com.lgy.common.utils.StringUtils;
import com.lgy.common.utils.bean.BeanUtils;
import com.lgy.system.domain.SysUser;

/**
 * shiro 工具类
 *
 * @author lgy
 */
public class ShiroUtils {

    public static ThreadLocal<SysUser> userThreadLocal = new ThreadLocal<SysUser>();

    public static void setUserThreadLocal(String userName) {
        SysUser sysUser = new SysUser();
        sysUser.setUserName(userName);
        userThreadLocal.set(sysUser);
    }
    public static SysUser getUserThreadLocal() {
        return userThreadLocal.get();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static void logout() {
        getSubject().logout();
    }

    public static SysUser getSysUser() {
        return  userThreadLocal.get();
//        SysUser user = null;
//        Object obj = getSubject().getPrincipal();
//        if (StringUtils.isNotNull(obj)) {
//            user = new SysUser();
//            BeanUtils.copyBeanProp(user, obj);
//        }
//        return user;
    }

    public static void setSysUser(SysUser user) {
        userThreadLocal.set(user);
        Subject subject = getSubject();
        PrincipalCollection principalCollection = subject.getPrincipals();
        String realmName = principalCollection.getRealmNames().iterator().next();
        PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
        // 重新加载Principal
        subject.runAs(newPrincipalCollection);
    }

    public static void clearCachedAuthorizationInfo() {
        RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        UserRealm realm = (UserRealm) rsm.getRealms().iterator().next();
        realm.clearCachedAuthorizationInfo();
    }

    public static Long getUserId() {
        return getSysUser().getUserId().longValue();
    }

    public static String getLoginName() {
        return getSysUser().getLoginName();
    }

    public static String getIp() {
        return getSubject().getSession().getHost();
    }

    public static String getSessionId() {
        return String.valueOf(getSubject().getSession().getId());
    }

    /**
     * 生成随机盐
     */
    public static String randomSalt() {
        // 一个Byte占两个字节，此处生成的3字节，字符串长度为6
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String hex = secureRandom.nextBytes(3).toHex();
        return hex;
    }
}
