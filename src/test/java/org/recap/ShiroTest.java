package org.recap;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.subject.support.DefaultWebSubjectContext;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by peris on 1/5/17.
 */
public class ShiroTest extends BaseTestCase {

    @Test
    public void loginSingleUser() throws Exception {

        DefaultWebSubjectContext webSubjectContext = new DefaultWebSubjectContext();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("jhon:2", "123");
        webSubjectContext.setAuthenticationToken(usernamePasswordToken);
        Subject subject = securityManager.createSubject(webSubjectContext);
        assertNotNull(subject);


        Subject loggedInSubject = securityManager.login(subject, usernamePasswordToken);
        boolean loggedInSubjectAuthenticated = loggedInSubject.isAuthenticated();
        assertNotNull(loggedInSubject);
        assertTrue(loggedInSubjectAuthenticated);
        boolean permitted = loggedInSubject.isPermitted("RequestPlace");
        loggedInSubject.getSession().setTimeout(1000);

        Thread.sleep(2000);

        try {
            loggedInSubject.isPermitted("RequestPlace");
            fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loginConcurrentUser() throws Exception {
        DefaultWebSubjectContext webSubjectContext = new DefaultWebSubjectContext();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("jhon:2", "123");
        webSubjectContext.setAuthenticationToken(usernamePasswordToken);
        Subject subject = securityManager.createSubject(webSubjectContext);
        assertNotNull(subject);

//        DefaultWebSubjectContext webSubjectContext = new DefaultWebSubjectContext();
        UsernamePasswordToken usernamePasswordToken1 = new UsernamePasswordToken("danie:2", "123");
        webSubjectContext.setAuthenticationToken(usernamePasswordToken1);
        Subject subject1 = securityManager.createSubject(webSubjectContext);
        assertNotNull(subject1);


        Subject loggedInSubject1 = securityManager.login(subject, usernamePasswordToken);
        assertNotNull(loggedInSubject1);
        Subject loggedInSubject2 = securityManager.login(subject1, usernamePasswordToken1);
        assertNotNull(loggedInSubject2);

        boolean loggedInSubject1Authenticated = loggedInSubject1.isAuthenticated();
        assertTrue(loggedInSubject1Authenticated);

        boolean permitted = loggedInSubject1.isPermitted("RequestPlace");
        assertTrue(permitted);

        boolean loggedInSubject2Authenticated = loggedInSubject2.isAuthenticated();
        assertTrue(loggedInSubject2Authenticated);

        boolean permitted2 = loggedInSubject2.isPermitted("RequestPlace");
        assertFalse(permitted2);


    }
}
