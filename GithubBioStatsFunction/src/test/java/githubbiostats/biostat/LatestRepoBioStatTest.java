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
import static org.junit.Assert.assertNull;
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
    public void bioStatNullEventServiceTest() {
        assertThrows(IllegalArgumentException.class, () -> new LatestRepoBioStat(null, username));
    }

    @Test
    public void bioStatEmptyUsernameTest() {
        assertThrows(IllegalArgumentException.class, () -> new LatestRepoBioStat(eventService, ""));
    }

    @Test
    public void bioStatNullUsernameTest() {
        assertThrows(IllegalArgumentException.class, () -> new LatestRepoBioStat(eventService, null));
    }

    @Test
    public void getBioStatTest() {
        when(pages.next()).thenReturn(events1);
        LatestRepoBioStat stat = new LatestRepoBioStat(eventService, username);
        assertEquals("My latest project is " + repoName, stat.getBioStat());
    }

    @Test
    public void getBioStatFailToGetStatTest() {
        when(pages.next()).thenReturn(events3);
        LatestRepoBioStat stat = new LatestRepoBioStat(eventService, username);
        assertEquals(null, stat.getBioStat());
    }

    @Test
    public void getStatTest() {
        when(pages.next()).thenReturn(events1);
        LatestRepoBioStat stat = new LatestRepoBioStat(eventService, username);
        assertEquals(repoName, stat.getStat());
    }

    @Test
    public void getStatNoPushEventTest() {
        when(pages.next()).thenReturn(events2);
        LatestRepoBioStat stat = new LatestRepoBioStat(eventService, username);
        assertNull(stat.getStat());
    }

    @Test
    public void getStatEmptyEventsTest() {
        when(pages.next()).thenReturn(events3);
        LatestRepoBioStat stat = new LatestRepoBioStat(eventService, username);
        assertNull(stat.getStat());
    }

    @Test
    public void getStatUserNotFoundTest() {
        String notFoundUser = "User Doesn't Exist";
        when(eventService.pageUserEvents(notFoundUser)).thenThrow(NoSuchPageException.class);
        LatestRepoBioStat stat = new LatestRepoBioStat(eventService, notFoundUser);
        assertThrows(NoSuchPageException.class, () -> stat.getStat());
    }

    @Test
    public void formatStatTest() {
        LatestRepoBioStat stat = new LatestRepoBioStat(eventService, username);
        assertEquals("My latest project is " + repoName, stat.formatStat(repoName));
    }

    @Test
    public void formatStatBlankTest() {
        LatestRepoBioStat stat = new LatestRepoBioStat(eventService, username);
        assertNull(stat.formatStat("  "));
    }

    @Test
    public void formatStatNullTest() {
        LatestRepoBioStat stat = new LatestRepoBioStat(eventService, username);
        assertNull(stat.formatStat(null));
    }

}
