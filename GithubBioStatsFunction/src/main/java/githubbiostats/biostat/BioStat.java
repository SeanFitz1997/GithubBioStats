package githubbiostats.biostat;

/**
 * Retrieves and calculates a Github statistic and returns it as a string in a format defined by the implementation.
 * @param <T> The type of the statistic to be retrieved
 */
public abstract class BioStat<T> {

    public String getBioStat() {
        T stat = getStat();
        return formatStat(stat);
    }

    public abstract T getStat();

    public abstract String formatStat(T stat);

}
