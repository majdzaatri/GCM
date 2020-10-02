package g6.gcm.core.entity;

import g6.gcm.core.factories.ClientsInquiries;
import g6.gcm.core.factories.DTOsFactory;
import g6.gcm.core.interfaces.AbstractDTO;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReportDTO extends AbstractDTO {

    private LocalDate fromDate;
    private LocalDate toDate;
    private ArrayList<String> reportType;
    private String cityName;
    private String ActivityType;

    private String Email;
    private Date ActivityDate;
    private int doneTo;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Date getActivityDate() {
        return ActivityDate;
    }

    private int numberOfMaps;
    private int oneShotDeals;
    private int numberOfSubscribers;
    private int numberOfRenewals;
    private int numberOfViews;
    private int numberOfDownloads;

    public void setActivityDate(Date activityDate) {
        ActivityDate = activityDate;
    }

    public int getDoneTo() {
        return doneTo;
    }

    public void setDoneTo(int doneTo) {
        this.doneTo = doneTo;
    }
    public ReportDTO() {
        setDTOType();
        setRequest(ClientsInquiries.REPORT);
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public int getNumberOfMaps() {
        return numberOfMaps;
    }

    public void setNumberOfMaps(int numberOfMaps) {
        this.numberOfMaps = numberOfMaps;
    }

    public int getOneShotDeals() {
        return oneShotDeals;
    }

    public void setOneShotDeals(int oneShotDeals) {
        this.oneShotDeals = oneShotDeals;
    }

    public int getNumberOfSubscribers() {
        return numberOfSubscribers;
    }

    public void setNumberOfSubscribers(int numberOfSubscribers) {
        this.numberOfSubscribers = numberOfSubscribers;
    }

    public int getNumberOfRenewals() {
        return numberOfRenewals;
    }

    public void setNumberOfRenewals(int numberOfRenewals) {
        this.numberOfRenewals = numberOfRenewals;
    }

    public int getNumberOfViews() {
        return numberOfViews;
    }

    public void setNumberOfViews(int numberOfViews) {
        this.numberOfViews = numberOfViews;
    }

    public int getNumberOfDownloads() {
        return numberOfDownloads;
    }

    public void setNumberOfDownloads(int numberOfDownloads) {
        this.numberOfDownloads = numberOfDownloads;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getActivityType() {
        return ActivityType;
    }

    public void setActivityType(String activityType) {
        ActivityType = activityType;
    }

    public ArrayList<String> getReportType() {
        return reportType;
    }

    public void setReportType(ArrayList<String> reportType) {
        this.reportType = reportType;
    }

    @Override
    protected void setDTOType() {
        this.type = DTOsFactory.REPORT;
    }

    @Override
    public String render() {
        return this.getReportType() + "";
    }


}
