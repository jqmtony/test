package com.ssm.demo.authority;

import javax.naming.NamingException;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.ldap.JndiLdapContextFactory;
import org.apache.shiro.realm.ldap.JndiLdapRealm;
import org.apache.shiro.realm.ldap.LdapContextFactory;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ssm.demo.entity.User;
import com.ssm.demo.service.IUserService;

/**
 * 验证IDCode,并查询LClaim的数据库,对登录用户授权
 * 
 * @author gaowei16
 *
 */
public class LdapRealm extends JndiLdapRealm {

  private static final Logger LOGGER = LoggerFactory.getLogger(LdapRealm.class);
  // 查询的属性为邮件地址,显示名称
  private static String[] returnedAtts = { "mail", "msExchExtensionAttribute16" };

  private IUserService userService;

  /**
   * 当前是否为开发环境
   */
  private boolean isDevEnv;

  @Override
  protected AuthenticationInfo queryForAuthenticationInfo(AuthenticationToken token,
      LdapContextFactory ldapContextFactory) throws NamingException {
    // 获取登录使用的itCode
    String userName = (String) token.getPrincipal();
    LOGGER.info("尝试登录,ITCode:{}", userName);
    User userSel = new User();
    userSel.setUserName(userName);
    User user = userService.selectByUser(userSel);
    if (user == null) { // 如果ITCode未关联CLaim则直接抛出异常
      LOGGER.info("帐号未关联到LClaim系统,ITCode:{}", userName);
      return null;
    }
    if (isDevEnv) { // 如果是研发环境,则直接创建对象
      return new SimpleAuthenticationInfo(user, user.getAge(), user.getPassword());
    }
    return super.queryForAuthenticationInfo(token, ldapContextFactory);
  }

//  @Override
//  protected AuthenticationInfo createAuthenticationInfo(AuthenticationToken token, Object ldapPrincipal,
//      Object ldapCredentials, LdapContext ldapContext) throws NamingException {
//    SysUser user = createUser(token, ldapContext);
//    userService.updateByITCode(user);
//    // 认证信息
//    return new SimpleAuthenticationInfo(user, user.getMail(), user.getITcode());
//  }
//
//  /**
//   * 查询LDAP服务创建User对象
//   * 
//   * @param token
//   * @param ldapContext
//   * @return
//   * @throws NamingException
//   */
//  private SysUser createUser(AuthenticationToken token, LdapContext ldapContext) throws NamingException {
//    // 获取登录使用的itCode
//    String itCode = (String) token.getPrincipal();
//
//    SearchControls constraints = new SearchControls();
//    constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
//    constraints.setReturningAttributes(returnedAtts);
//    NamingEnumeration<SearchResult> en = ldapContext.search(baseDN, "(SAMAccountName=" + itCode + ")", constraints);
//    SysUser user = new SysUser();
//    if (en.hasMoreElements()) {
//      SearchResult result = en.nextElement();
//      Attributes attributes = result.getAttributes();
//      user.setITcode(itCode);
//      user.setDisplayName(attributes.get("msExchExtensionAttribute16").get().toString());
//      user.setMail(attributes.get("mail").get().toString());
//    }
//    return user;
//  }

  @Override
  protected AuthorizationInfo queryForAuthorizationInfo(PrincipalCollection principals,
      LdapContextFactory ldapContextFactory) throws NamingException {
    // TODO 目前登录用户具有所有的权限
    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    info.addStringPermission("*");
    return info;
  }

  public void setUrl(String url) {
    ((JndiLdapContextFactory) getContextFactory()).setUrl(url);
  }

  public void setUserService(IUserService userService) {
    this.userService = userService;
  }

  public void setDevEnv(boolean isDevEnv) {
    this.isDevEnv = isDevEnv;
  }
}
