package base;

import listeners.CustomListener;
import org.apache.log4j.Logger;
import org.testng.annotations.Listeners;

@Listeners({CustomListener.class})
public class BaseTest {

    public Logger log = Logger.getLogger(this.getClass().getName());
}
