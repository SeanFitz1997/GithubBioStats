package githubbiostats.module;

import com.google.common.collect.ImmutableList;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import githubbiostats.GithubBioStats;
import githubbiostats.biostat.BioStat;
import githubbiostats.biostat.LatestRepoBioStat;
import org.eclipse.egit.github.core.service.EventService;

import java.util.List;

public class GithubBioStatsModule extends AbstractModule {

    @Override
    protected void configure() {}

    @Provides
    public GithubBioStats selectedStats() {
        String username = "SeanFitz1997"; // TODO: make env var
        EventService eventService = new EventService();

        List<BioStat> stats = ImmutableList.of(
                new LatestRepoBioStat(eventService, username)
        );

        return new GithubBioStats(stats);
    }

}
