package githubbiostats;

import org.junit.Test;

public class AppTest {

    @Test
    public void successfullyHandlesEvent() {
        App app = new App();
        app.handleEvent(null, null);
    }

}
