package engine.loggers;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LogHelper {

    static Logger log = LogManager.getLogger();
   // static String callerMethodName = Thread.currentThread().getStackTrace()[4].getMethodName();

    public static void logInfo (String Step)
    {
        log.info(Step + " -");
    }

    @Step("{Warning}")
    public static void logWarning (String Warning)
    {
        log.warn(Warning + " -");
    }

    @Step("{Error}")
    public static void logError (String Error , Exception e)
    {
        log.error(Error + " -");
        Assert.fail(Error,e);
    }

    @Step("{Error}")
    public static void logError (String Error)
    {
        log.error(Error + " -");
        Assert.fail(Error);
    }

}
