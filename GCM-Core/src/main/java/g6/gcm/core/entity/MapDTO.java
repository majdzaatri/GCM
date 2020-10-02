package g6.gcm.core.entity;

import com.sothawo.mapjfx.Coordinate;
import g6.gcm.core.factories.AccessabilityState;
import g6.gcm.core.factories.DTOsFactory;
import g6.gcm.core.interfaces.AbstractDTO;
import java.util.List;
import java.util.Objects;

public class MapDTO extends AbstractDTO {

  private int mapID;
  private int cityID;
  private double version;
  private AccessabilityState status;

  private List<String> siteList;
  private List<String> toursList;

  public int getMapID() {
    return mapID;
  }

  public void setMapID(int mapID) {
    this.mapID = mapID;
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

  /**
   * @return the siteList
   */
  public List<String> getSiteList() {
    return siteList;
  }

  /**
   * @param siteList the siteList to set
   */
  public void setSiteList(List<String> siteList) {
    this.siteList = siteList;
  }

  /**
   * @return the toursList
   */
  public List<String> getToursList() {
    return toursList;
  }

  /**
   * @param toursList the toursList to set
   */
  public void setToursList(List<String> toursList) {
    this.toursList = toursList;
  }

  /**
   * @return the version
   */
  public double getVersion() {
    return version;
  }

  /**
   * @param version the version to set
   */
  public void setVersion(double version) {
    this.version = version;
  }

  /**
   * @return the status
   */
  public AccessabilityState getStatus() {
    return status;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(AccessabilityState status) {
    this.status = status;
  }

  /**
   * In order to differentiate between different transferred objects in {@see ServerMessageHandler}
   * and {@see GCMMessageHandler}.
   */
  @Override
  protected void setDTOType() {
    this.type = DTOsFactory.MAP;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof MapDTO)) {
      return false;
    }
    MapDTO mapDTO = (MapDTO) o;
    return getMapID() == mapDTO.getMapID() &&
        getCityID() == mapDTO.getCityID() &&
        Double.compare(mapDTO.getVersion(), getVersion()) == 0 &&
        getStatus() == mapDTO.getStatus() &&
        Objects.equals(getSiteList(), mapDTO.getSiteList()) &&
        Objects.equals(getToursList(), mapDTO.getToursList());
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(getMapID(), getCityID(), getVersion(), getStatus(), getSiteList(), getToursList());
  }

  @Override
  public String render() {
    return String.valueOf(this.getMapID());
  }

  @Override
  public String toString() {
    return "MapDTO{" +
        "mapID=" + mapID +
        ", cityID=" + cityID +
        ", version=" + version +
        ", status=" + status +
        ", siteList=" + siteList +
        ", toursList=" + toursList +
        '}';
  }
}
