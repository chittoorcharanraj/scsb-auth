package org.recap.UT.config;

import brave.sampler.Sampler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.recap.config.SamplerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SamplerConfig.class)
public class SamplerConfigTest {

    @Autowired
    private Sampler sampler;

    @Test
    public void testDefaultSamplerBean() {
        assertNotNull(sampler);
        assertSame(Sampler.ALWAYS_SAMPLE, sampler);
    }
}
