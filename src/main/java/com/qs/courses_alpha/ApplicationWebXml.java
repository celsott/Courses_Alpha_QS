package com.qs.courses_alpha;

import com.qs.courses_alpha.config.DefaultProfileUtil;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * This is a helper Java class that provides an alternative to creating a web.xml.
<<<<<<< HEAD
 * This will be invoked only when the application is deployed to a servlet container like Tomcat, JBoss etc.
=======
 * This will be invoked only when the application is deployed to a servlet container like Tomcat, Jboss etc.
>>>>>>> 437b3e0b4eb9ed92e1f0e38b48c64ad9efc2d8d7
 */
public class ApplicationWebXml extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        /**
         * set a default to use when no profile is configured.
         */
        DefaultProfileUtil.addDefaultProfile(application.application());
        return application.sources(CoursesAlphaQsApp.class);
    }
}
