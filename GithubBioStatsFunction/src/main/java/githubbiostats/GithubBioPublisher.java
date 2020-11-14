package githubbiostats;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.RequestException;
import org.eclipse.egit.github.core.service.UserService;

import java.io.IOException;

import static org.apache.commons.lang.Validate.notNull;

/**
 * Updates a users Github bio with bio
 */
@Slf4j
public class GithubBioPublisher {

    final UserService userService;

    public GithubBioPublisher(UserService authenticatedUserService) {
        userService = authenticatedUserService;
    }

    public void updateBio(final String bio) throws IOException {
        notNull(bio, "'bio' must not be null");

        try {
            User user = userService.getUser();
            user.setBio(bio);
            userService.editUser(user);
        } catch (RequestException e) {
            log.error("Unable to update user's bio\n", e);
            throw new IllegalStateException("'userService' was not successfully authenticated");
        }
    }

}
