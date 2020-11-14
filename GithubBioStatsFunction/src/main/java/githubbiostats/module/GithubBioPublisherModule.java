package githubbiostats.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import githubbiostats.GithubBioPublisher;
import org.eclipse.egit.github.core.service.UserService;

public class GithubBioPublisherModule extends AbstractModule {

    @Override
    protected void configure() {}

    @Provides
    protected GithubBioPublisher provideGithubBioPublisher() {
        UserService userService = new UserService();
        // TODO: need to authenticate user service here
        return new GithubBioPublisher(userService);
    }

}
