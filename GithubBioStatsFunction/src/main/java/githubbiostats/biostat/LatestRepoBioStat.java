package githubbiostats.biostat;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.event.Event;
import org.eclipse.egit.github.core.service.EventService;

import java.util.Collection;
import java.util.Optional;

/**
 * Gets the Repo of the users last commit on Github
 */
@Slf4j
public class LatestRepoBioStat implements BioStat<String> {

    final EventService eventService;

    public LatestRepoBioStat(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public String getBioStat(String username) {
        PageIterator<Event> pageIterator = eventService.pageUserEvents(username);
        while (pageIterator.hasNext()) {
            Collection<Event> page = pageIterator.next();
            Optional<String> latestRepo = page.stream()
                    .filter(event -> event.getType().equals("PushEvent"))
                    .map(event -> event.getRepo().getName())
                    .map(name -> name.substring(name.lastIndexOf('/') + 1))
                    .findFirst();

            if(latestRepo.isPresent()) {
                return latestRepo.get();
            }
        }
        return null;
    }

    @Override
    public String formatStat(String stat) {
        if(stat == null || stat.isBlank()) {
            return "";
        }
        return "My latest project is " + stat;
    }

}
