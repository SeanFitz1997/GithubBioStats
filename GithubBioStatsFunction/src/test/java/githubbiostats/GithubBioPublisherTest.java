package githubbiostats;

import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.RequestException;
import org.eclipse.egit.github.core.service.UserService;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.IOException;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

public class GithubBioPublisherTest {

    @Mock UserService userService;
    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    User user = new User();

    @Test
    public void publishBioTest() throws IOException {
        when(userService.getUser()).thenReturn(user);
        GithubBioPublisher publisher = new GithubBioPublisher(userService);
        publisher.updateBio("Test Bio");
    }

    @Test
    public void publishBioUnAuthenticatedClientTest() throws IOException {
        when(userService.getUser()).thenThrow(RequestException.class);
        GithubBioPublisher publisher = new GithubBioPublisher(userService);
        assertThrows(IllegalStateException.class, () -> publisher.updateBio("Test Bio"));
    }

    @Test
    public void publishBioNullBioTest() throws IOException {
        when(userService.getUser()).thenReturn(user);
        GithubBioPublisher publisher = new GithubBioPublisher(userService);
        assertThrows(IllegalArgumentException.class, () -> publisher.updateBio(null));
    }

}
