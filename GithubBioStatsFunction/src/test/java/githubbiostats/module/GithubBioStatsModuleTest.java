package githubbiostats.module;

import com.google.inject.Injector;
import githubbiostats.GithubBioStats;
import org.junit.Before;
import org.junit.Test;

import static com.google.inject.Guice.createInjector;
import static org.junit.Assert.assertNotNull;

public class GithubBioStatsModuleTest {

    Injector injector;

    @Before
    public void setUp() {
        this.injector = createInjector(new GithubBioStatsModule());
    }

    @Test
    public void injectTest() {
        GithubBioStats stats = injector.getInstance(GithubBioStats.class);
        assertNotNull(stats);
    }

}
