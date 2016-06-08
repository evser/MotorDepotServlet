package action;

import dao.TripDAO;
import entity.TripEntity;
import exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConfigurationManager;
import util.PageNamesConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by USER on 15.05.2016.
 */
public class ChangeTripStateAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        Integer tripId = Integer.valueOf(req.getParameter("tripId"));
        String chosenState = req.getParameter("chosenState");
        TripDAO daoTrip = new TripDAO();

        try {
            logger.info("changing trip " + tripId + " state to " + chosenState);
            daoTrip.changeTripState(tripId, chosenState.equals("true"));
        } catch (DAOException e) {
            logger.error("error during changing trip state", e);
            req.setAttribute("errorMessage", e.getMessage());
            return ConfigurationManager.getProperty(PageNamesConstants.ERROR);
        }

        logger.info("requesting all trips");
        List<TripEntity> allTrips = daoTrip.getAllTrips();
        req.setAttribute("trips", allTrips);
        return ConfigurationManager.getProperty(PageNamesConstants.TRIP_LIST);
    }
}