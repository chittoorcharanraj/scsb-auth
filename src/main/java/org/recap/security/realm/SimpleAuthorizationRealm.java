package org.recap.security.realm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.recap.model.UserForm;
import org.recap.security.AuthenticationService;
import org.recap.security.AuthorizationService;
import org.recap.security.AuthorizationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by dharmendrag on 29/11/16.
 */
@Component
public class SimpleAuthorizationRealm extends AuthorizingRealm{

    private AuthenticationService authenticationService;
    private AuthorizationService authorizationService;


    /**
     * Instantiates a new SimpleAuthorizationRealm.
     */
    public SimpleAuthorizationRealm(AuthorizationServiceImpl authorizationService, AuthenticationService authenticationService){
        setName("simpleAuthRealm");
        setCredentialsMatcher(new SimpleCredentialsMatcher());
        setCachingEnabled(true);
        this.authenticationService = authenticationService;
        this.authorizationService = authorizationService;
   }



    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Integer loginId=(Integer)principals.fromRealm(getName()).iterator().next();
        SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
        return authorizationService.doAuthorizationInfo(authorizationInfo,loginId);

    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) {
        if (authToken != null) {
            UsernamePasswordToken token = (UsernamePasswordToken) authToken;
            UserForm userForm = authenticationService.doAuthentication(token);
            if (userForm != null && userForm.isPasswordMatcher()) {
                return new SimpleAuthenticationInfo(userForm.getUserId(), token.getPassword(), getName());
            }
        }
        return null;
    }
}
