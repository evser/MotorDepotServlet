package listener;

import dao.util.pool.ConnectionPool;
import exception.DatabaseConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.DatabaseConfigurationBundleManager;
import util.DatabaseConfigurationParameterName;
import util.RequestParameterName;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by USER on 16.06.2016.
 */
public class MotorDepotServletContextListener implements ServletContextListener {

    private static final Logger logger = LogManager.getLogger();
    private static final String INITIALIZING_POOL_ERROR = "Initializing pool error";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        if(!Boolean.parseBoolean(
                DatabaseConfigurationBundleManager.getProperty(DatabaseConfigurationParameterName.USE_JPA))) {
            try {
                ConnectionPool.getInstance().init();
            } catch (DatabaseConnectionException e) {
                logger.error(e);
                servletContextEvent.getServletContext().setAttribute(RequestParameterName.ERROR_MESSAGE,
                        INITIALIZING_POOL_ERROR);
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if(!Boolean.parseBoolean(
                DatabaseConfigurationBundleManager.getProperty(DatabaseConfigurationParameterName.USE_JPA))) {
            ConnectionPool.getInstance().destroy();
        }
    }
}
