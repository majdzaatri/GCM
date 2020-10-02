package g6.gcm.server.manager.daos;

import g6.gcm.core.entity.SiteClassificationDTO;
import g6.gcm.core.factories.ClientsInquiries;
import g6.gcm.core.factories.ConsoleTextColorsFactory;
import g6.gcm.core.interfaces.AbstractDTO;
import g6.gcm.server.manager.GCMServer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SiteClassificationsDAO extends AbstractDAO {

    @Override
    public Object processQuery(AbstractDTO theDTO) {

        /* Prepare answer */
        Object answer = null;

        /* Cast for easier usage */
        ClientsInquiries theRequest = (ClientsInquiries) theDTO.getRequest();

        /* Closing leaks: if any of the following lines fails, the instances will be automatically close */
        try (
                /* Get a connection ready */
                Connection connection = GCMServer.getServer().dbConnection.getConnection();

                /* Prepare Statement */
                PreparedStatement statement = connection.prepareStatement(theRequest.getQuery())
        ) {

            /* Cast for easier usage */
            SiteClassificationDTO siteClassificationRequest = (SiteClassificationDTO) theDTO;

            /* To get the answer in */
            SiteClassificationDTO siteClassificationAnswer;

            /* Direct to the right executor method with a ready statement */
            switch (theRequest) {

                case ONE_CLASSIFICATION: {
                    break;
                }
                case ALL_SITES_CLASSIFICATIONS: {
                    answer = getAll(statement);
                    break;
                }
                default: {
                    GCMServer.getServer().say(
                            "Request " + theRequest + " was" + ConsoleTextColorsFactory.ANSI_RED + " NOT "
                                    + ConsoleTextColorsFactory.ANSI_RESET + "processed!");
                }
            }
        } catch (SQLException e) {
            GCMServer.getServer().say(
                    ConsoleTextColorsFactory.ANSI_RED + "EXCEPTION" + ConsoleTextColorsFactory.ANSI_RESET
                            + " IN " + this);
            GCMServer.getServer().oops(e);
            e.printStackTrace();
        }
        return answer;
    }

    @Override
    protected SiteClassificationDTO extractFromResultSet(ResultSet resultSet) {
        SiteClassificationDTO siteClassification = new SiteClassificationDTO();
        try {
            siteClassification.setCategory(resultSet.getString("Category"));
        } catch (SQLException e) {
            e.printStackTrace();
            siteClassification = null;
        } finally {
            return siteClassification;
        }
    }
}
