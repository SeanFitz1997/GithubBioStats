package githubbiostats.module;

import com.google.inject.Injector;
import githubbiostats.GithubBioPublisher;
import org.junit.Before;
import org.junit.Test;

import static com.google.inject.Guice.createInjector;
import static org.junit.Assert.assertNotNull;

public class GithubBioPublisherModuleTest {

    Injector injector;

    @Before
    public void setup() {
        injector = createInjector(new GithubBioPublisherModule());
    }

    @Test
    public void injectTest() {
        GithubBioPublisher publisher = injector.getInstance(GithubBioPublisher.class);
        assertNotNull(publisher);
    }

}
