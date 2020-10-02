//package g6.gcm.server.manager.daos;
//
//import g6.gcm.core.entity.CityDTO;
//import g6.gcm.core.factories.ClientsInquiries;
//import g6.gcm.core.interfaces.AbstractDTO;
//import g6.gcm.server.manager.GCMServer;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class CityDAO extends AbstractDAO {
//
//	/*
//	 * (non-Javadoc)
//	 *
//	 * @see
//	 * core.java.entity.dao.interfaces.AbstractDAO#processQuery(core.java.entity.
//	 * AbstractDTO)
//	 */
//	@Override
//	public Object processQuery(AbstractDTO request) {
//
//		/* Get a connection ready */
//		Connection connection = GCMServer.getServer().dbConnection.getConnection();
//
//		/* Prepare Statement */
//		PreparedStatement statement;
//
//		/* Prepare Object */
//		Object answer;
//
//        ClientsInquiries theRequest = (ClientsInquiries) request.getRequest();
//
//		try {
//			/* Direct to the right executer method with a ready statement */
//			switch (theRequest) {
//
//				case ONE_CITY: {
//					statement = connection.prepareStatement(theRequest.getQuery());
//					answer = getOne(statement);
//					break;
//				}
//
//				case ALL_CITIES: {
//					statement = connection.prepareStatement(theRequest.getQuery());
//					answer = getAll(statement);
//					break;
//				}
//
//				default: {
//					answer = null;
//				}
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//			answer = null;
//		}
//		return answer;
//	}
//
//	@Override
//	protected CityDTO extractFromResultSet(ResultSet resultSet) {
//
//		CityDTO city = new CityDTO();
//
//		try {
//			city.setCityName((resultSet.getString("Name")));
//			city.setNumberOfMaps(resultSet.getInt("NumberOfMaps"));
//			city.setMapsCollectionVersion(resultSet.getString("mapsCollectionVersion"));
//			city.setNumberOfDownloads(resultSet.getInt("numberOfDownloads"));
//			city.setPrice(resultSet.getInt("price"));
//			city.setNumberOfRenewedSubscription(resultSet.getInt("NumberOfRenewedSubscription"));
//			city.setNumberOfViews(resultSet.getInt("NumberOfViews"));
//		} catch (SQLException e) {
//			e.printStackTrace();
//			city = null;
//		} finally {
//			return city;
//		}
//	}
//
//}
