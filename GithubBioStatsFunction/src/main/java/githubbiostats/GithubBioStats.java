package githubbiostats;

import githubbiostats.biostat.BioStat;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang.Validate.notNull;
import static org.apache.commons.lang.Validate.noNullElements;

/**
 * Used to retrieve a collection of {@link BioStat} and aggregate their outputs.
 */
@Slf4j
public class GithubBioStats {

    private final List<BioStat> bioStats;

    public GithubBioStats(List<BioStat> bioStats) {
        notNull(bioStats, "'bioStats' must not be null");
        noNullElements(bioStats, "'bioStats' must not contain any null elements");
        this.bioStats = bioStats;
    }

    public String generateBio() {
        log.info("Retrieving stats ...");
        List<String> stats = this.retrieveStats();
        return String.join(" | ", stats);
        // TODO: Cut and warn if too long
    }

    private List retrieveStats() {
        return bioStats.stream()
                .peek(stat -> log.info("Retrieving {} ...", stat.getClass().getSimpleName()))
                .map(BioStat::getBioStat)
                .filter(stat -> stat != null)
                .collect(Collectors.toList());
    }

}
