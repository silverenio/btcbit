package base;

import listeners.CustomListener;
import org.testng.annotations.Listeners;

@Listeners({CustomListener.class})
public class BackEndBaseTest extends BaseTest {

    public BackEndBaseTest() {
        log.debug("BackEndBaseTest started");
    }
}
