package githubbiostats;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import com.google.inject.Injector;
import githubbiostats.module.GithubBioStatsModule;
import githubbiostats.module.GithubBioPublisherModule;
import lombok.extern.slf4j.Slf4j;

import static com.google.inject.Guice.createInjector;

@Slf4j
public class App {

    public String handleEvent(final ScheduledEvent event, final Context context) {
        log.info("Starting App ...");

        log.info("Creating objects ...");
        Injector injector = createInjector(
                new GithubBioPublisherModule(),
                new GithubBioStatsModule()
        );
        GithubBioPublisher publisher = injector.getInstance(GithubBioPublisher.class);
        GithubBioStats stats = injector.getInstance(GithubBioStats.class);

        log.info("Retrieving Stats ...");
        // TODO

        return "Succeed";
    }

}
