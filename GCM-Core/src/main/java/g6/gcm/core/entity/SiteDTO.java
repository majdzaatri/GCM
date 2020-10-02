package g6.gcm.core.entity;

import g6.gcm.core.factories.DTOsFactory;
import java.util.Objects;

public class SiteDTO extends CoordinatizedDTO {

  // Auto Generated in DB
  private int siteID;
  private String siteName;
  private int cityID;
  private int mapID;
  private String description;
  private String recommendedVisitDuration;
  private SiteClassificationDTO siteClassification = new SiteClassificationDTO();
  private Boolean accessible;
  private int tourID;


  /**
   * @return the siteID
   */
  public int getSiteID() {
    return siteID;
  }


  /**
   * @param siteID the siteID to set
   */
  public void setSiteID(int siteID) {
    this.siteID = siteID;
  }


  public Boolean getAccessible() {
    return accessible;
  }


  public void setAccessible(Boolean accessible) {
    this.accessible = accessible;
  }


  /**
   * @return the cityID
   */
  public int getCityID() {
    return cityID;
  }


  /**
   * @param cityID the cityID to set
   */
  public void setCityID(int cityID) {
    this.cityID = cityID;
  }

  public int getTourID() {
    return tourID;
  }

  public void setTourID(int tourID) {
    this.tourID = tourID;
  }

  public int getMapID() {
    return mapID;
  }

  public void setMapID(int mapID) {
    this.mapID = mapID;
  }


  /**
   * @return the recommendedVisitDuration
   */
  public String getRecommendedVisitDuration() {
    return recommendedVisitDuration;
  }


  /**
   * @param recommendedVisitDuration the recommendedVisitDuration to set
   */
  public void setRecommendedVisitDuration(String recommendedVisitDuration) {
    this.recommendedVisitDuration = recommendedVisitDuration;
  }


  public SiteClassificationDTO getSiteClassification() {
    return siteClassification;
  }


  public void setSiteClassification(SiteClassificationDTO classification) {
    this.siteClassification = classification;
  }


  public String getSiteName() {
    return siteName;
  }


  public void setSiteName(String siteName) {
    this.siteName = siteName;
  }


  public String getDescription() {
    return description;
  }


  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SiteDTO)) {
      return false;
    }
    SiteDTO siteDTO = (SiteDTO) o;
    return getSiteID() == siteDTO.getSiteID() &&
        getSiteName().equals(siteDTO.getSiteName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getSiteID(), getSiteName());
  }

  @Override
  public String toString() {
    return "SiteDTO{" +
        "siteID=" + siteID +
        ", siteName='" + siteName + '\'' +
        ", cityID=" + cityID +
        ", mapID=" + mapID +
        getCoordinates() +
        ", description='" + description + '\'' +
        ", recommendedVisitDuration='" + recommendedVisitDuration + '\'' +
        ", siteClassification=" + siteClassification +
        ", accessible=" + accessible +
        ", tourID=" + tourID +
        '}';
  }

  /**
   * In order to differentiate between different transferred objects in {@see ServerMessageHandler}
   * and {@see GCMMessageHandler}.
   */
  @Override
  protected void setDTOType() {
    this.type = DTOsFactory.SITE;
  }


  @Override
  public String render() {
    return getSiteName();
  }

}
