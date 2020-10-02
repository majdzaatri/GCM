
package g6.gcm.core.entity;

import g6.gcm.core.factories.DTOsFactory;
import g6.gcm.core.interfaces.AbstractDTO;
import g6.gcm.core.interfaces.Request;

import java.sql.Date;

public class CreditCardDTO extends AbstractDTO {

    private String cardholdersName;
    private String cardholdersID;
    private String creditCardNumber;
    private Date creditCardExpirationDate;
    private String creditCardCSV;
    private String accountEmail;

    /**
     * @return the cardholdersName
     */
    public String getCardholdersName() {
        return cardholdersName;
    }

    /**
     * @param cardholdersName the cardholdersName to set
     */
    public void setCardholdersName(String cardholdersName) {
        this.cardholdersName = cardholdersName;
    }

    /**
     * @return the cardholdersID
     */
    public String getCardholdersID() {
        return cardholdersID;
    }

    /**
     * @param cardholdersID the cardholdersID to set
     */
    public void setCardholdersID(String cardholdersID) {
        this.cardholdersID = cardholdersID;
    }

    /**
     * @return the creditCardNumber
     */
    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    /**
     * @param creditCardNumber the creditCardNumber to set
     */
    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

	/**
	 * @return the creditCardExpirationDate
	 */
	public Date getCreditCardExpirationDate() {
        return Date.valueOf(String.valueOf(creditCardExpirationDate));
	}
//    /**
//     * @return the creditCardExpirationDate
//     */
//    public Date getCreditCardExpirationDate() {
//        return creditCardExpirationDate;
//    }

    /**
     * @param creditCardExpirationDate the creditCardExpirationDate to set
     */
    public void setCreditCardExpirationDate(Date creditCardExpirationDate) {
        this.creditCardExpirationDate = creditCardExpirationDate;
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    /**
     * @return the creditCardCSV
     */
    public String getCreditCardCSV() {
        return creditCardCSV;
    }

    /**
     * @param creditCardCSV the creditCardCSV to set
     */
    public void setCreditCardCSV(String creditCardCSV) {
        this.creditCardCSV = creditCardCSV;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((cardholdersID == null) ? 0 : cardholdersID.hashCode());
        result = prime * result + ((cardholdersName == null) ? 0 : cardholdersName.hashCode());
        result = prime * result + ((creditCardCSV == null) ? 0 : creditCardCSV.hashCode());
        result = prime * result + ((creditCardExpirationDate == null) ? 0 : creditCardExpirationDate.hashCode());
        result = prime * result + ((creditCardNumber == null) ? 0 : creditCardNumber.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        CreditCardDTO other = (CreditCardDTO) obj;
        if (cardholdersID == null) {
            if (other.cardholdersID != null)
                return false;
        } else if (!cardholdersID.equals(other.cardholdersID))
            return false;
        if (cardholdersName == null) {
            if (other.cardholdersName != null)
                return false;
        } else if (!cardholdersName.equals(other.cardholdersName))
            return false;
        if (creditCardCSV == null) {
            if (other.creditCardCSV != null)
                return false;
        } else if (!creditCardCSV.equals(other.creditCardCSV))
            return false;
        if (creditCardExpirationDate == null) {
            if (other.creditCardExpirationDate != null)
                return false;
        } else if (!creditCardExpirationDate.equals(other.creditCardExpirationDate))
            return false;
        if (creditCardNumber == null) {
            if (other.creditCardNumber != null)
                return false;
        } else if (!creditCardNumber.equals(other.creditCardNumber))
            return false;
        return true;
    }

    @Override
    protected void setDTOType() {
        this.type = DTOsFactory.CREDIT_CARD;
    }

    @Override
    public String render() {
        return this.getCreditCardNumber();
    }
}
