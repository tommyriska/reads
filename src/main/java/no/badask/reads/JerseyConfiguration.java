package no.badask.reads;

import no.badask.reads.resource.BookResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;

@Configuration
@ApplicationPath("/api")
public class JerseyConfiguration extends ResourceConfig {

    public JerseyConfiguration() {
        register(BookResource.class);
    }
}
