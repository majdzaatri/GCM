package g6.gcm.core.entity;

import g6.gcm.core.factories.DTOsFactory;
import g6.gcm.core.interfaces.AbstractDTO;
import java.util.ArrayList;
import java.util.List;

public class TourDTO extends AbstractDTO {

  private int tourID;
  private int mapID;
  private String description;

  private List<String> sites = new ArrayList<String>();

  /*
   * Here we assume that there can't be two different recommended visit duration for same SITE in
   * different tours
   */

  // TODO: add function for calculating tour length?


  /**
   * @return the tourID
   */
  public int getTourID() {
    return tourID;
  }


  /**
   * @param tourID the tourID to set
   */
  public void setTourID(int tourID) {
    this.tourID = tourID;
  }


  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }


  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }


  /**
   * @return the mapID
   */
  public int getMapID() {
    return mapID;
  }


  /**
   * @param mapID the mapID to set
   */
  public void setMapID(int mapID) {
    this.mapID = mapID;
  }


  public List<String> getSites() {
    return sites;
  }


  public void setSites(List<String> sites) {
    this.sites = sites;
  }


  /**
   * In order to differentiate between different transferred objects in {@see ServerMessageHandler}
   * and {@see GCMMessageHandler}.
   */
  @Override
  protected void setDTOType() {
    this.type = DTOsFactory.TOUR;
  }

  @Override
  public String render() {
    return String.valueOf(getTourID());
  }

  @Override
  public String toString() {
    return "TourDTO{" +
        "tourID=" + tourID +
        ", description='" + description + '\'' +
        ", mapID=" + mapID +
        ", sites=" + sites +
        '}';
  }
}
