package githubbiostats;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AppTest {

    @Test
    public void successfullyHandlesEvent() {
        App app = new App();
        assertEquals(app.handleEvent(null, null), "Succeed");
    }

}
