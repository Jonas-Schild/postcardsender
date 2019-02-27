package ch.schildj.config;

import ch.schildj.postcardsender.config.ProcessConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;


@TestConfiguration
@EnableAutoConfiguration
@Import(ProcessConfiguration.class)
//  @TestPropertySource(locations = "classpath:ch/schildj/postcardsender/config/process/application-unittest.properties")
@TestPropertySource(locations = "classpath:/config/process/application-unittest.properties", properties = "api.post.request.url=https://testeintrag")
public class TestProcessConfiguration {
}
