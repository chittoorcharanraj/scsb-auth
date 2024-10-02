package org.recap.UT.controller;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.SubjectThreadState;
import org.apache.shiro.util.ThreadContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.recap.controller.AuthorizationController;
import org.recap.security.UserManagementService;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class AuthorizationControllerTest {

    private AuthorizationController mockAuthorizationController;
    private Subject subject;
    private UserManagementService userManagementService;

    @Before
    public void setUp() {
        mockAuthorizationController = mock(AuthorizationController.class);
        userManagementService = mock(UserManagementService.class);
        subject = mock(Subject.class);
        SecurityManager securityManager = new DefaultSecurityManager();
        ThreadContext.bind(securityManager);
        new SubjectThreadState(subject).bind();
    }


    @Test
    public void roles() {
        boolean result = true;
        UsernamePasswordToken usernamePasswordToken = mock(UsernamePasswordToken.class);
        Mockito.doNothing().when(subject).login(usernamePasswordToken);
        Mockito.when(subject.getPrincipal()).thenReturn(9);
        Mockito.when(mockAuthorizationController.roles(usernamePasswordToken))
                .thenCallRealMethod();
        try {
            result = mockAuthorizationController.roles(usernamePasswordToken);
            assertFalse(result);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
