package githubbiostats.biostat;

import org.eclipse.egit.github.core.client.NoSuchPageException;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.event.Event;
import org.eclipse.egit.github.core.event.EventRepository;
import org.eclipse.egit.github.core.service.EventService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

public class LatestRepoBioStatTest {

    @Mock EventService eventService;
    @Mock PageIterator<Event> pages;
    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    String username = "TestUser123";
    String repoName = "TestRepo123";
    EventRepository repo = new EventRepository()
            .setName(String.join("/", username, repoName));
    Event commentEvent = new Event()
            .setType("CommitCommentEvent");
    Event pushEvent = new Event()
            .setType("PushEvent")
            .setRepo(repo);

    Collection<Event> events1 = List.of(commentEvent, pushEvent);
    Collection<Event> events2 = List.of(commentEvent, commentEvent);
    Collection<Event> events3 = List.of();

    @Before
    public void setUp() {
        when(eventService.pageUserEvents(username)).thenReturn(pages);
        when(pages.hasNext()).thenReturn(true).thenReturn(false);
    }

    @Test
    public void getBioStatTest() {
        when(pages.next()).thenReturn(events1);
        LatestRepoBioStat stat = new LatestRepoBioStat(eventService);
        String latestRepo = stat.getBioStat(username);

        assertEquals(repoName, latestRepo);
    }

    @Test
    public void getBioStatsNoPushEventTest() {
        when(pages.next()).thenReturn(events2);
        LatestRepoBioStat stat = new LatestRepoBioStat(eventService);
        String latestRepo = stat.getBioStat(username);
        assertEquals(null, latestRepo);
    }

    @Test
    public void getBioStatsEmptyEventsTest() {
        when(pages.next()).thenReturn(events3);
        LatestRepoBioStat stat = new LatestRepoBioStat(eventService);
        String latestRepo = stat.getBioStat(username);
        assertEquals(null, latestRepo);
    }

    @Test
    public void getBioStatsUserNotFoundTest() {
        String notFoundUser = "User Doesn't Exist";
        when(eventService.pageUserEvents(notFoundUser)).thenThrow(NoSuchPageException.class);

        LatestRepoBioStat stat = new LatestRepoBioStat(eventService);
        assertThrows(NoSuchPageException.class, () -> stat.getBioStat(notFoundUser));
    }

    @Test
    public void formatStatTest() {
        LatestRepoBioStat stat = new LatestRepoBioStat(eventService);
        assertEquals("My latest project is " + repoName, stat.formatStat(repoName));
    }

    @Test
    public void formatStatBlankTest() {
        LatestRepoBioStat stat = new LatestRepoBioStat(eventService);
        assertEquals("", stat.formatStat("  "));
    }

    @Test
    public void formatStatNullTest() {
        LatestRepoBioStat stat = new LatestRepoBioStat(eventService);
        assertEquals("", stat.formatStat(null));
    }

}
