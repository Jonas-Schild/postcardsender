package ch.schildj.postcardsender;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.context.request.RequestContextListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Properties;

/*
 * Main Class
 */

@SpringBootApplication
@ComponentScan(basePackages = {"ch.schildj.postcardsender.config"})
public class PostcardsenderApplication extends SpringBootServletInitializer {

    private static final String ENVIRONMENT_VAR = "ch.schildj.environment";
    private static final String CONTAINER_VAR = "ch.schildj.container";
    private static final String INSTANCE_VAR = "ch.schildj.instance";
    private static final String HOST_VAR = "ch.schildj.host";
    private static final String LOGGING_DIR_VAR = "ch.schildj.logging.dir";

    private static final String CRED_CLIENT_ID = "api.post.client.id";
    private static final String CRED_CLIENT_SECRET = "api.post.client.secret";

    private static final String SINGLE = "SINGLE";
    private static final String BOOT_CONTAINER = "boot";
    private static final String DEV_ENV = "dev";
    private static final String LOG_DIR = "./logs";
    private static final String CONF_DIR = ".";
    private static final String LOCALHOST = "localhost";
    private static final String EMPTY = " ";



    public static void main(String[] args) throws Exception {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(PostcardsenderApplication.class);
        builder.profiles(getEnvironmentProfile(), getContainerProfile())
                .bannerMode(Banner.Mode.CONSOLE)
                .properties(setProperties())
                .logStartupInfo(true)
                .run(args);
    }

    // servlet and filters registration
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.addListener(RequestContextListener.class);
        servletContext.addListener(HttpSessionEventPublisher.class);
    }

    private static Properties setProperties() {
        Properties prop = new Properties();

        prop.setProperty("org.springframework.boot.logging.LoggingSystem", "LogbackLoggingSystem");

        // externalConfigDir
        String configDir = System.getProperty("ch.schildj.config.dir", CONF_DIR);
        prop.setProperty("spring.config.additional-location", "file:" + configDir + "/config.properties");

        setPropertyDefaultIfMissing(prop, ENVIRONMENT_VAR, DEV_ENV);
        setPropertyDefaultIfMissing(prop, INSTANCE_VAR, SINGLE);
        setPropertyDefaultIfMissing(prop, CONTAINER_VAR, BOOT_CONTAINER);
        setPropertyDefaultIfMissing(prop, LOGGING_DIR_VAR, LOG_DIR);
        setPropertyDefaultIfMissing(prop, HOST_VAR, LOCALHOST);
        setPropertyDefaultIfMissing(prop, CRED_CLIENT_ID, EMPTY);
        setPropertyDefaultIfMissing(prop, CRED_CLIENT_SECRET, EMPTY);

        return prop;
    }

    private static void setPropertyDefaultIfMissing(Properties prop, String varKey, String defaultValue) {
        String env = System.getProperty(varKey);
        if (env == null) {
            prop.setProperty(varKey, defaultValue);
        }
    }


    private static String getEnvironmentProfile() {
        return System.getProperty(ENVIRONMENT_VAR, "dev");
    }

    private static String getContainerProfile() {
        return "container_" + System.getProperty(CONTAINER_VAR, "boot");
    }


}
