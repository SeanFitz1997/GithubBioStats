package githubbiostats.biostat;

/**
 * Used for retrieving and calculating a Github statistic and formatting its output to be place in the Github bio.
 * @param <T> The type of the statistic to be retrieved
 */
public interface BioStat<T> {

    public T getBioStat(String username);

    public String formatStat(T stat);

}
