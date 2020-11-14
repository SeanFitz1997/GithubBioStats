package githubbiostats;

import githubbiostats.biostat.BioStat;

import java.util.List;

/**
 * Used to retrieve a collection of {@link BioStat} and aggregate their outputs.
 * BioStats will be retrieved in parallel.
 */
public class GithubBioStats {

    private final List bioStats;

    public GithubBioStats(List<BioStat> bioStats) {
        // TODO: Validation
        this.bioStats = bioStats;
    }

}
