package githubbiostats;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

    public void handleEvent(final ScheduledEvent event, final Context context) {
        log.info("Running App");
    }

}
