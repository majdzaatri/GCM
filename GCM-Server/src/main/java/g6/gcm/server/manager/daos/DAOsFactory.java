package g6.gcm.server.manager.daos;

/**
 * This class will act as a DAO objects factory. TODO: integrate Dependency Injection to minimize
 * the manually coded Singletons and static classes in project.
 */
public class DAOsFactory {

    /* TODO: Temporary, think of other design patterns */


    /* These are attribute of different Data Access Object. */
    private static ActivitiesDAO activitiesDAO = new ActivitiesDAO();
    private static CitiesDAO citiesDAO = new CitiesDAO();
    private static CreditCardsDAO creditCardsDAO = new CreditCardsDAO();
    private static CoordinatesDAO coordinatesDAO = new CoordinatesDAO();
    private static EmployeesDAO employeesDAO = new EmployeesDAO();
    private static MapsDAO mapsDAO = new MapsDAO();
    private static NotificationsDAO notificationsDAO = new NotificationsDAO();
    private static SitesDAO sitesDAO = new SitesDAO();
    private static PurchasesDAO purchasesDAO = new PurchasesDAO();
    private static TourDAO tourDAO = new TourDAO();
    private static UserDAO userDAO = new UserDAO();
    private static OneShotDealDAO oneShotDealDAO = new OneShotDealDAO();
    private static SiteClassificationsDAO siteClassificationsDAO = new SiteClassificationsDAO();
    private static PriceRequestDAO priceRequestDAO = new PriceRequestDAO();


    public static OneShotDealDAO getOneShotDealDAO() {
        return oneShotDealDAO;
    }

    /**
     * @return the activitiesDAO
     */
    public static ActivitiesDAO getActivitiesDAO() {
        return activitiesDAO;
    }

    /**
     * @return the citiesDAO
     */
    public static CitiesDAO getCitiesDAO() {
        return citiesDAO;
    }

    /**
     * @return the creditCardsDAO
     */
    public static CreditCardsDAO getCreditCardsDAO() {
        return creditCardsDAO;
    }


    public static CoordinatesDAO getCoordinatesDAO() {
        return coordinatesDAO;
    }

    /**
     * @return the employeesDAO
     */
    public static EmployeesDAO getEmployeesDAO() {
        return employeesDAO;
    }

    /**
     * @return the mapsDAO
     */
    public static MapsDAO getMapsDAO() {
        return mapsDAO;
    }

    /**
     * @return the notificationsDAO
     */
    public static NotificationsDAO getNotificationsDAO() {
        return notificationsDAO;
    }

    /**
     * @return the sitesDAO
     */
    public static SitesDAO getSitesDAO() {
        return sitesDAO;
    }

    /**
     * @return the purchasesDAO
     */
    public static PurchasesDAO getPurchasesDAO() {
        return purchasesDAO;
    }

    /**
     * @return the tourDAO
     */
    public static TourDAO getTourDAO() {
        return tourDAO;
    }

    /**
     * @return the userDAO
     */
    public static UserDAO getUserDAO() {
        return userDAO;
    }

    public static PriceRequestDAO getPriceRequestDAO() {
        return priceRequestDAO;
    }

    public static SiteClassificationsDAO getSiteClassificationsDAO() {
    return siteClassificationsDAO;
  }
}
