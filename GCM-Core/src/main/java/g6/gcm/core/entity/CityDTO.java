package g6.gcm.core.entity;

import g6.gcm.core.factories.DTOsFactory;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class CityDTO extends CoordinatizedDTO {

  private int CityID;
  private String cityName = "";
  private double collectionVersion;
  private int collectionsMapsNumber;
  private int collectionsDownloadsNumber;
  private int collectionsPriceID;
  private int collectionsNumberOfSites;
  private String cityDescription;
  private double subscriptionPrice;
  private double oneShotDealPrice;
  private Date requestDate = Date.valueOf(LocalDate.now());
  private List<String> sitesList;
  private List<Integer> mapsList;

  public List<Integer> getMapsList() {
    return mapsList;
  }

  public void setMapsList(List<Integer> mapsList) {
    this.mapsList = mapsList;
  }

  public Date getRequestDate() {
    return requestDate;
  }

  public void setRequestDate(Date requestDate) {
    this.requestDate = requestDate;
  }

  public double getSubscriptionPrice() {
    return subscriptionPrice;
  }

  public void setSubscriptionPrice(double subscriptionPrice) {
    this.subscriptionPrice = subscriptionPrice;
  }

  public double getOneShotDealPrice() {
    return oneShotDealPrice;
  }

  public void setOneShotDealPrice(double oneShotDealPrice) {
    this.oneShotDealPrice = oneShotDealPrice;
  }

  public String getCityDescription() {
    return cityDescription;
  }

  public void setCityDescription(String cityDescription) {
    this.cityDescription = cityDescription;
  }

  public int getCollectionsNumberOfSites() {
    return collectionsNumberOfSites;
  }

  public void setCollectionsNumberOfSites(int collectionsNumberOfSites) {
    this.collectionsNumberOfSites = collectionsNumberOfSites;
  }

  // TODO: what about daily downloads/views?
//hello

  public int getCityID() {
    return CityID;
  }

  public void setCityID(int cityID) {
    CityID = cityID;
  }


  /**
   * @return the cityName
   */
  public String getCityName() {
    return cityName;
  }

  /**
   * @param cityName the cityName to set
   */
  public void setCityName(String cityName) {
    this.cityName = cityName;
  }


  /**
   * @return the collectionVersion
   */
  public double getCollectionVersion() {
    return collectionVersion;
  }

  /**
   * @param version the collectionVersion to set
   */
  public void setCollectionVersion(double version) {
    this.collectionVersion = version;
  }

  /**
   * @return the collectionsMapsNumber
   */
  public int getCollectionsMapsNumber() {
    return collectionsMapsNumber;
  }

  /**
   * @param collectionsMapsNumber the collectionsMapsNumber to set
   */
  public void setCollectionsMapsNumber(int collectionsMapsNumber) {
    this.collectionsMapsNumber = collectionsMapsNumber;
  }

  /**
   * @return the collectionsDownloadsNumber
   */
  public int getCollectionsDownloadsNumber() {
    return collectionsDownloadsNumber;
  }

  /**
   * @param collectionsDownloadsNumber the collectionsDownloadsNumber to set
   */
  public void setCollectionsDownloadsNumber(int collectionsDownloadsNumber) {
    this.collectionsDownloadsNumber = collectionsDownloadsNumber;
  }

  /**
   * @return the collectionsPriceID
   */
  public int getCollectionsPriceID() {
    return collectionsPriceID;
  }

  /**
   * @param collectionsPriceID the collectionsPriceID to set
   */
  public void setCollectionsPriceID(int collectionsPriceID) {
    this.collectionsPriceID = collectionsPriceID;
  }

  /**
   * @return the city's sites list
   */
  public List<String> getSitesList() {
    return sitesList;
  }

  /**
   * @param sitesList the sitesList to set
   */

  public void setSitesList(List<String> sitesList) {
    this.sitesList = sitesList;
  }

  /**
   * In order to differentiate between different transferred objects in {@see ServerMessageHandler}
   * and {@see GCMMessageHandler}.
   */
  @Override
  protected void setDTOType() {
    this.type = DTOsFactory.CITY;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CityDTO)) {
      return false;
    }
    CityDTO cityDTO = (CityDTO) o;
    return getCityID() == cityDTO.getCityID() &&
        Double.compare(cityDTO.getCollectionVersion(), getCollectionVersion()) == 0 &&
        getCollectionsMapsNumber() == cityDTO.getCollectionsMapsNumber() &&
        getCollectionsDownloadsNumber() == cityDTO.getCollectionsDownloadsNumber() &&
        getCollectionsPriceID() == cityDTO.getCollectionsPriceID() &&
        getCollectionsNumberOfSites() == cityDTO.getCollectionsNumberOfSites() &&
        Double.compare(cityDTO.getSubscriptionPrice(), getSubscriptionPrice()) == 0 &&
        Double.compare(cityDTO.getOneShotDealPrice(), getOneShotDealPrice()) == 0 &&
        Objects.equals(getCityDescription(), cityDTO.getCityDescription()) &&
        Objects.equals(getRequestDate(), cityDTO.getRequestDate()) &&
        Objects.equals(getSitesList(), cityDTO.getSitesList()) &&
        Objects.equals(getMapsList(), cityDTO.getMapsList());
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(getCityID(), getCityName(), getCollectionVersion(), getCollectionsMapsNumber(),
            getCollectionsDownloadsNumber(), getCollectionsPriceID(), getCollectionsNumberOfSites(),
            getCityDescription(), getSubscriptionPrice(), getOneShotDealPrice(), getRequestDate(),
            getSitesList(), getMapsList());
  }

  @Override
  public String toString() {
    return "CityDTO{" +
        "CityID=" + CityID +
        ", cityName='" + cityName + '\'' +
        ", collectionVersion=" + collectionVersion +
        ", collectionsMapsNumber=" + collectionsMapsNumber +
        ", collectionsDownloadsNumber=" + collectionsDownloadsNumber +
        ", collectionsPriceID=" + collectionsPriceID +
        ", subscriptionPrice=" + subscriptionPrice +
        ", oneShotDealPrice=" + oneShotDealPrice +
        ", requestDate=" + requestDate +
        '}';
  }


  @Override
  public String render() {
    return this.getCityName();
  }

}
