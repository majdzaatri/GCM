package g6.gcm.core.entity;

import g6.gcm.core.factories.DTOsFactory;
import g6.gcm.core.interfaces.AbstractDTO;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class ActivityDTO extends AbstractDTO {

    private int DoneTo;

  /* This enum needs to be set in subclasses */
  protected Activity activity = Activity.UNSET;
    private String email;
  /* Common attributes for all types of activities */
  private int id; // Auto-generated (Ascending)
    private List<Boolean> reportGeneratorOptions;
  private Date date; // Could be: Date of Purchase, Date of Viewing, Date of Download ...

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    /**
     * @return the DoneTo
     */
    public int getDoneTo() {
        return DoneTo;
    }

    /**
     * @param DoneTo the DoneTo to set
     */
    public void setDoneTo(int DoneTo) {
        this.DoneTo = DoneTo;
    }

  /**
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * @return the date
   */
  public Date getDate() {
    return date;
  }

  /**
   * @param date the date to set
   */
  public void setDate(Date date) {
    this.date = date;
  }

  /**
   * @return the userEmail
   */
  public String getEmail() {
      return email;
  }

  /**
   * @param email the userEmail to set
   */
  public void setEmail(String email) {
      this.email = email;
  }


  /**
   * @return the activity
   */
  public Activity getActivity() {
    return activity;
  }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityDTO that = (ActivityDTO) o;
        return DoneTo == that.DoneTo &&
                id == that.id &&
                activity == that.activity &&
                Objects.equals(email, that.email) &&
                Objects.equals(date, that.date) &&
                Objects.equals(reportGeneratorOptions, that.reportGeneratorOptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activity, DoneTo, id, email, date, reportGeneratorOptions);
    }

    /**
     *
     */

  /**
   * @return the reportGeneratorOptions
   */
  public List<Boolean> getReportGeneratorOptions() {
    return reportGeneratorOptions;
  }


  /**
   * @param reportGeneratorOptions the reportGeneratorOptions to set
   */
  public void setReportGeneratorOptions(List<Boolean> reportGeneratorOptions) {
    this.reportGeneratorOptions = reportGeneratorOptions;
  }


  /*
   * (non-Javadoc)
   *
   * @see core.java.entity.AbstractDTO#setDataType()
   */
  @Override
  final protected void setDTOType() {
    this.type = DTOsFactory.ACTIVITY;
  }

  @Override
  public String render() {
      return this.getId() + "";
  }


    public enum Activity {
        SIGN_UP, SIGN_IN, CITY_VIEW, MAP_VIEW, SUBSCRIBED, ONE_SHOT_DEAL, EXTENDED_SUBSRIPTION, DOWNLOADED, UNSET, MAP_REQUEST
    }

}
