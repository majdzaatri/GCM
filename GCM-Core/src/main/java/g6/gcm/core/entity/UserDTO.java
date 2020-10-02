package g6.gcm.core.entity;

import g6.gcm.core.factories.DTOsFactory;
import g6.gcm.core.interfaces.AbstractDTO;

import java.util.Objects;

public class UserDTO extends AbstractDTO {

    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
    private String username;
    private int isEmployee = 0;
    private boolean online;

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return if employee
     */
    public int getIsEmployee() {
        return isEmployee;
    }

    /**
     * @param isEmployee
     */
    public void setIsEmployee(int isEmployee) {
        this.isEmployee = isEmployee;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the status
     */
    public boolean isOnline() {
        return online;
    }

    /**
     * @param status the status to set
     */
    public void setOnline(boolean status) {
        this.online = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return isEmployee == userDTO.isEmployee &&
                online == userDTO.online &&
                Objects.equals(email, userDTO.email) &&
                Objects.equals(firstName, userDTO.firstName) &&
                Objects.equals(lastName, userDTO.lastName) &&
                Objects.equals(phoneNumber, userDTO.phoneNumber) &&
                Objects.equals(password, userDTO.password) &&
                Objects.equals(username, userDTO.username);
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(email, firstName, lastName, phoneNumber, password, username, isEmployee, online);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "UserDTO [email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
                + ", phoneNumber="
                + phoneNumber + "]";
    }

    /*
     * (non-Javadoc)
     *
     * @see core.java.entity.AbstractDTO#setDTOType()
     */
    @Override
    protected void setDTOType() {
        this.type = DTOsFactory.USER;
    }

    // TODO: check why if this is removed client gets null as an answer // delete?
    public void setType(DTOsFactory what) {
        this.type = what;
    }

    @Override
    public String render() {
        return this.getFirstName() + this.getLastName();
    }


}
