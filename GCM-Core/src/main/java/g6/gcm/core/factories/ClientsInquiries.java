package g6.gcm.core.factories;

import g6.gcm.core.interfaces.Request;

public enum ClientsInquiries implements Request {

  // TODO: Needs revision and rethinking queries
  // TODO: Override "getQueryByCity" - ByCOllection? - method where it might be
  // relevant
  // TODO: Add method to retrieve some columns --> override where relevant (ONE /
  // ALL)
  // TODO: Add "SOME_***" enums?

  USER {
    @Override
    public String getQuery() {
      return "SELECT * FROM costumers WHERE id = ?";
    }
  },
  ADD_NOTIFICATION {
    @Override
    public String getQuery() {
      return "INSERT INTO notifications (Sender, Receiver, NotificationDate, NotificationType, Notification) VALUES(?, ?, ?, ?, ?)";
    }
  },
  DELETE_PRICE_REQUEST  {
    @Override
    public String getQuery() {
      return "DELETE FROM citys_prices WHERE requestID = ?";
    }
  },
  GET_NOTIFICATIONS {
    @Override
    public String getQuery() {
      return "SELECT * FROM notifications where Receiver = ?";
    }

  },
  UPDATE_NOTIFICATION_READ {
    @Override
    public String getQuery() {
      return "UPDATE notifications SET notifications.Read = '1' WHERE NotificationID = ?";
    }
  },
  ALL_ONLINE_USERS {
    @Override
    public String getQuery() {
      return "SELECT * FROM users where Online = '1'";
    }
  },
  ALL_USERS {
    @Override
    public String getQuery() {
      return "SELECT * FROM users";
    }
  },
  REPORT {
    @Override
    public String getQuery() {
      return "SELECT * FROM activitylog WHERE ActivityDate >= ? AND ActivityDate <= ?";
    }
  },
  REPORT_MAPS {
    @Override
    public String getQuery() {
      return "SELECT CollectionsMapsNumber FROM cities WHERE CityName = ?";
    }
  },
  ALL_SUBSCRIPTIONS {
    @Override
    public String getQuery() {
      return "SELECT * FROM purchases WHERE ExpirationDate IS NOT NULL";
    }
  },
  AUTHENTICATE_LOGIN {
    @Override
    public String getQuery() {
      return "SELECT * FROM users WHERE Email=? AND Password=?";
    }
  }, UPDATE_ONLINE_STATUS {
    @Override
    public String getQuery() {
      return "UPDATE `gcm-db`.`users` SET `Online` = ? WHERE (`Email` = ?)";

    }
  },
  SIGN_UP {
    @Override
    public String getQuery() {
      return "INSERT INTO users VALUES('?', '?', '?', '?', '?', '?', '0', '0')";
    }
  },
  ONE_COSTUMER {
    @Override
    public String getQuery() {
      return "SELECT * FROM costumers WHERE id = ?";
    }

  },

  ALL_COSTUMERS {
    @Override
    public String getQuery() {
      return "SELECT * FROM costumers";
    }

  },

  ONE_CITY {
    @Override
    public String getQuery() {
      return "SELECT * FROM cities WHERE CityID = ?";
    }

  }, CITY_BY_NAME {
    @Override
    public String getQuery() {
      return "SELECT * FROM cities WHERE CityName = ?";
    }

  },

  ALL_CITIES {
    @Override
    public String getQuery() {
      return "SELECT * FROM cities WHERE CityID != 0";
    }

  },

  ONE_MAP {
    @Override
    public String getQuery() {
      return "SELECT * FROM maps WHERE MapID = ?";
    }
  },

  ALL_MAPS {
    @Override
    public String getQuery() {
      return "SELECT * FROM maps";
    }

  },
  ALL_MAPS_REQUEST {
    @Override
    public String getQuery() {
      return "SELECT * FROM maps WHERE MapsState = 'PENDING_APPROVAL'";
    }

  },

  ONE_CONTENT_EDITOR {
    @Override
    public String getQuery() {
      return "SELECT * FROM employees WHERE EmployeeID = ? AND EmployeesRole = CONTENT_EDITOR";
    }

  },

  ALL_CONTENT_EDITORS {
    @Override
    public String getQuery() {
      return "SELECT * FROM company_editors";
    }

  },

  ONE_CONTENT_MANAGER {
    @Override
    public String getQuery() {
      return "SELECT * FROM content_managers WHERE ID = ? AND Role = CONTENT_MANAGER";
    }

  },

  ALL_CONTENT_MANAGERS {
    @Override
    public String getQuery() {
      return "SELECT * FROM content_managers  WHERE ID = ? AND Role = CONTENT_MANAGER";
    }

  },

  ONE_COMPANY_MANAGER {
    @Override
    public String getQuery() {
      return "SELECT * FROM company_managers WHERE ID = ? AND Role = ONE_COMPANY_MANAGER";
    }

  },

  ONE_MAP_COLLECTION {
    @Override
    public String getQuery() {
      return "SELECT * FROM map_collections";
    }

  },

  ALL_ONE_SHOT_DEALS {
    @Override
    public String getQuery() {
      return "SELECT * FROM purchases WHERE ExpirationDate IS NULL";
    }

  },

  ONE_SITE {
    @Override
    public String getQuery() {
      return "SELECT * FROM sites WHERE siteID = ?";
    }

  },

  ALL_SITES {
    @Override
    public String getQuery() {
      return "SELECT * FROM sites";
    }

  },

  ONE_SUBSCRIPTION {
    @Override
    public String getQuery() {
      return "SELECT * FROM purchases WHERE PurchaseID = ?";
    }

  },


  ONE_SUBSCRIPTION_RENEWAL {
    @Override
    public String getQuery() {
      return "SELECT * FROM purchases_renewals"/*TODO: change it or delete*/;
    }

  },

  ALL_SUBSCRIPTION_RENEWALS {
    @Override
    public String getQuery() {
      return "SELECT * FROM purchases_renewals";
    }

  },

  ONE_TOUR {
    @Override
    public String getQuery() {
      return "SELECT * FROM tours WHERE ID = ?";
    }

  },

  ALL_TOURS {
    @Override
    public String getQuery() {
      return "SELECT * FROM tours";
    }

  },
  ONE_NOTIFICATION {
    @Override
    public String getQuery() {
      return "SELECT * FROM notifications WHERE notificationID = ?";
    }

  },
  ALL_NOTIFICATIONS {
    @Override
    public String getQuery() {
      return "SELECT * FROM notifications";
    }

  },
  INSERT {
    /*
     * (non-Javadoc)
     *
     * @see server.java.manager.RequestsFactory#getQuery()
     */
    @Override
    public String getQuery() {
      return "INSERT INTO `gcm-db`.`maps` (`ID`, `Status`, `Version`, `CityName`) VALUES (?, ?, ?, ?)";
    }
  },
  EMPLOYEE {
    @Override
    public String getQuery() {
      return "SELECT * FROM employees WHERE EmployeesEmail = ?";
    }
  }, ONE_COORDINATES {
    @Override
    public String getQuery() {
      return "SELECT * FROM coordinates WHERE CoordinatesID = ?";
    }
  }, ALL_SITES_NAMES_OF_MAP {
    @Override
    public String getQuery() {
      return "SELECT s.SiteName FROM `gcm-db`.city_map_site AS sim, sites AS s WHERE sim.MapID = ? AND sim.SiteID = s.SiteID";
    }
  }, UPDATE_CITY_MAPS {
    @Override
    public String getQuery() {
      return "UPDATE `gcm-db`.`maps` SET `CityID` = '?' WHERE (`MapID` = '?');";
    }
  }, ALL_SITES_OF_MAP {
    @Override
    public String getQuery() {
      return "SELECT s.SiteID, s.SiteName, s.CityID, s.Description, s.RecommendedVisitDuration,"
          + " s.CoordinatesID, s.Category, s.Accessible FROM sites AS s INNER JOIN `gcm-db`.city_map_site AS m"
          + " ON s.SiteID = m.SiteID AND m.MapID = ?";
    }
    /*
    public String getQuery() {
      return "SELECT s.SiteID, s.SiteName, s.CityID, s.Description, s.RecommendedVisitDuration,"
          + " s.CoordinatesID, s.Category, s.Accessible FROM sites AS s INNER JOIN maps_sites AS m"
          + " ON s.SiteID = m.SiteID AND m.MapID = ?";
    }*/
  },
  UPDATE_USER {
    @Override
    public String getQuery() {
      return "UPDATE users SET Password = ?, FirstName = ?, LastName = ?, Username = ?, PhoneNumber = ? WHERE Email = ?";
    }

  }, UPDATE_USER_PASSWORD {
    @Override
    public String getQuery() {
      return "UPDATE users SET Password = ? WHERE Email = ?";
    }
  }, ALL_SITES_OF_CITY {
    @Override
    public String getQuery() {
      return "SELECT * FROM sites WHERE CityID = ?";
    }
  },
  ACTIVITIES_MAPREQUEST {
    @Override
    public String getQuery() {
      return "SELECT * FROM activitylog WHERE ActivityType = 'MAP_REQUEST'";
    }
  },
  REPORT_ONE {
    @Override
    public String getQuery() {
      return "SELECT * FROM activitylog WHERE date > ? AND date < ? AND activityType = ?";
    }
  },
  REPORT_BY_USER {
    @Override
    public String getQuery() {
      return "SELECT * FROM activitylog WHERE ActivityDate > ? AND ActivityDate < ? AND Email = ?";
    }
  },
  REPORT_2 {
    @Override
    public String getQuery() {
      return "SELECT COUNT(ActivityType) FROM activitylog WHERE ActivityDate > ? AND ActivityDate < ? AND ActivityType = ? And DoneTo = ?";
    }
  },

  LOGOUT {
    @Override
    public String getQuery() {
      return "UPDATE users SET Online = 0 WHERE Email = ?";
    }

  }, ALL_MAPS_OF_CITY {
    @Override
    public String getQuery() {
      return "SELECT * FROM maps WHERE CityID = ? OR CityID = 0";
    }

  }, SIGN_UP_USER {
    @Override
    public String getQuery() {
      return "INSERT INTO `gcm-db`.`users` (`Email`, `Password`, `FirstName`, `LastName`, `Username`, `PhoneNumber`) VALUES (?,?,?,?,?,?)";
    }
  }, INSERT_CREDIT_CARD {
    @Override
    public String getQuery() {
      return "INSERT INTO `gcm-db`.`creditcards` (`email`, `cardholdersName`, `cardholdersID`, `creditCardNumber`, `creditCardExpirationDate`, `creditCardCSV`) VALUES (?, ?, ?, ?, ?, ?)";
    }
  }, COUNT_MAPS_FOR_CITY {
    @Override
    public String getQuery() {
      return "SELECT COUNT(*) FROM maps WHERE CityID = ?";
    }
  }, COUNT_SITES_FOR_CITY {
    @Override
    public String getQuery() {
      return "SELECT COUNT(*) FROM sites WHERE CityID= ?";
    }
  }, LOG_INTO_ACTIVITY {
    @Override
    public String getQuery() {
      return "INSERT INTO `gcm-db`.`activitylog` (`Email`, `ActivityType`, `ActivityDate`, `DoneTo`) VALUES (?,?,?,?)";
    }
  }, ALL_TOURS_OF_MAP {
    @Override
    public String getQuery() {
      return "SELECT * FROM tours WHERE MapID = ?";
    }
  }, ALL_SITES_NAMES_OF_TOUR {
    @Override
    public String getQuery() {
      return "SELECT SiteName FROM tours_sites AS ts, sites AS s WHERE ts.TourID = ? AND ts.SiteID = s.SiteID";
    }
  }, ALL_SITES_NAMES_OF_CITY {
    @Override
    public String getQuery() {
      return "SELECT SiteName FROM sites WHERE CityID=?";
    }
  }, ALL_SITES_OF_TOUR_oldddddddddd {
    @Override
    public String getQuery() {// TODO delete?
      return "SELECT SiteName FROM sites AS s, tours_sites AS ts WHERE ts.SiteID = s.SiteID AND ts.TourID = ?";
    }
  }, CITY_NUMBER_OF_SITES {
    @Override
    public String getQuery() {
      return "SELECT COUNT(sites.SiteID) from sites WHERE CityID = ?";
    }
  }, SUBSCRIBE_TO_CITY {
    @Override
    public String getQuery() {
      return "INSERT INTO `gcm-db`.`purchases` (`CustomerEmail`, `PurchaseDate`, `CityID`, `ExpirationDate`) VALUES (?, CURDATE(), ?, ADDDATE(CURDATE(), INTERVAL 6 MONTH));";
    }

  }, ONE_SHOT_DEAL_PURCHASE {
    @Override
    public String getQuery() {
      return "INSERT INTO `gcm-db`.`purchases` (`CustomerEmail`, `PurchaseDate`, `CityID`) VALUES (?, CURDATE(), ?);";
    }
  }, EXTEND_SUBSCRIPTION {
    @Override
    public String getQuery() {
      return "UPDATE `gcm-db`.`purchases` SET `WasExtended` = TRUE, `ExpirationDate` = ADDDATE(`purchases`.`ExpirationDate`, INTERVAL 6 MONTH) WHERE (`PurchaseID` = ?);";
    }
  }, EXTEND_SUBSCRIPTION_BY_USER {
    @Override
    public String getQuery() {
      return "UPDATE `gcm-db`.`purchases` SET `WasExtended` = TRUE, `ExpirationDate` = ADDDATE(`purchases`.`ExpirationDate`, INTERVAL 6 MONTH) WHERE (`CustomerEmail` = ? AND CityID = ?);";
    }
  }, ALL_SUBSCRIPTIONS_OF_USER {
    @Override
    public String getQuery() {
      return "SELECT * FROM purchases WHERE CustomerEmail = ? AND ExpirationDate IS NOT NULL";
    }
  }, ALL_ONE_SHOT_DEALS_OF_USER {
    @Override
    public String getQuery() {
      return "SELECT * FROM purchases WHERE CustomerEmail = ? AND ExpirationDate IS NULL";
    }
  }, UPDATE_MAP_STATUS {
    @Override
    public String getQuery() {
      return "UPDATE maps SET MapsState = ? WHERE MapID = ?";
    }
  }, UPDATE_MAP {
    @Override
    public String getQuery() {
      return "UPDATE maps SET CityID = ? WHERE MapID = ?";
    }
  }, UPDATE_CITY {
    @Override
    public String getQuery() {
      return "UPDATE cities SET CityName = ?,  Description = ? WHERE CityID = ?";
    }
  }, UPDATE_COORDINATES {
    @Override
    public String getQuery() {
      return "UPDATE coordinates SET Latitude = ?, Longitude = ? WHERE CoordinatesID = ?";
    }
  }, EXTENT {
    @Override
    public String getQuery() {
      return "INSERT INTO `gcm-db`.`citysitesgenerator` (`CityID`, `MinLatitude`, `MinLongitude`, `MaxLatitude`, `MaxLongitude`) VALUES (?, ?, ?, ?, ?);";
    }
  }, SIGN_OUT_USER {
    @Override
    public String getQuery() {
      return "UPDATE `gcm-db`.`users` SET `Online` = ? WHERE (`Email` = ?)";
    }
  }, ONE_CLASSIFICATION {
    @Override
    public String getQuery() {
      return null;
    }
  }, ALL_SITES_CLASSIFICATIONS {
    @Override
    public String getQuery() {
      return "SELECT * FROM categories;";
    }
  }, ALL_CITIES_SUBSCRIPTIONS_OF_USER {
    @Override
    public String getQuery() {
      return "SELECT CityID FROM purchases WHERE CustomerEmail =?";
    }
  }, SUBSCRIBED_CITIES_NAMES {
    @Override
    public String getQuery() {
      return "SELECT CityName FROM cities ";
    }
  }, CITY_PRICE_BY_REQUEST {
    @Override
    public String getQuery() {
      return "SELECT * FROM cities WHERE cityName = ?";
    }
  }, CITY_PRICE {
    @Override
    public String getQuery() {
      return "SELECT * From citys_prices WHERE cityName = ?";
    }
  }, CANCEL_REQUEST {
    @Override
    public String getQuery() {
      return "DELETE FROM `gcm-db`.`citys_prices` WHERE cityName = ?;";
    }
  }, ADD_MAPS_PRICE_REQUEST {
    @Override
    public String getQuery() {
      return "INSERT INTO `gcm-db`.`citys_prices` (`cityName`, `subscriptionPrice`, `requestStatus`, `oneShotDealPrice`, `requestDate`) VALUES (?,?,?,?,?)";
    }
  }, GET_ALL_PRICES_REQUEST {
    @Override
    public String getQuery() {
      return "SELECT * FROM `gcm-db`.citys_prices";
    }
  }, DELETE_NOTIFICATION_BY_CITY_ID {
    @Override
    public String getQuery() {
      return "DELETE FROM notifications WHERE Updatee = ?";
    }
  }, ONE_CREDIT_CARD {
    @Override
    public String getQuery() {
      return "SELECT * FROM creditcards WHERE AccountsEmail = ?";
    }
  }, DELETE_CREDIT_CARD {
    @Override
    public String getQuery() {
      return "DELETE FROM creditcards WHERE creditCardNumber = ?";
    }
  }, CREATE_CITY {
    @Override
    public String getQuery() {
      return
          "INSERT INTO `gcm-db`.`cities` (`CityName`, `Description`) VALUES (?, ?);";
    }
  }, CREATE_SITE {
    @Override
    public String getQuery() {
      return
          "INSERT INTO `gcm-db`.`sites` (`SiteName`, `CityID`, `Description`, `RecommendedVisitDuration`, "
              + "`Category`, `Accessible`) VALUES (?,?,?,?,?,?);";
    }
  }, DELETE_SITE {
    @Override
    public String getQuery() {
      return "DELETE FROM `gcm-db`.`sites` WHERE (`SiteID` = '?');";
    }
  }, SAVE_MAP {
    @Override
    public String getQuery() {
      return "UPDATE `gcm-db`.`maps` SET `MapsState` = 'PENDING' WHERE (`MapID` = '?');";
    }
  }, CREATE_MAP {
    @Override
    public String getQuery() {
      return "INSERT INTO `gcm-db`.`maps` (`MapID`, `CityID`) VALUES (?, ?);";
    }
  };

  // Methods that MUST be OVERRIDEN.

  /**
   * Can be called only from USER instance.
   *
   * @return the query
   * @throws SQLException
   */
  // Optional methods that could be overridden.

  /**
   * This is the standard query.
   *
   * @return the query
   */
  public abstract String getQuery();


}
