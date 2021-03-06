package action;

import exception.ActionExecutionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.PageNameConstant;
import util.PagesBundleManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action, responsible for providing signup form
 * Created by USER on 15.05.2016.
 */
public class SignupFormAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {
        logger.info("requesting signup form");
        return PagesBundleManager.getProperty(PageNameConstant.SIGNUP_FORM);
    }
}
