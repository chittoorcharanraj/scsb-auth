package org.recap.UT;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.recap.Main;
import org.springframework.boot.SpringApplication;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class MainUT {

    @Test
    public void testMainMethod() {
        SpringApplication mockSpringApplication = Mockito.mock(SpringApplication.class);
        ArgumentCaptor<Class<?>> classCaptor = ArgumentCaptor.forClass(Class.class);
        ArgumentCaptor<String[]> argsCaptor = ArgumentCaptor.forClass(String[].class);
        Main.main(new String[]{});
    }
}
