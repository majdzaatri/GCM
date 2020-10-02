package g6.gcm.core.entity;

/**
 * This class is created in order to make future different roles' hierarchies
 * easy. Any changes to attributes of ALL employees should be made here.
 */
public abstract class EmployeeDTO extends UserDTO {

    protected Roles role;
    private int employeeID;

    public Roles getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    abstract public void setRole(Roles role);

    /**
     * @return the employeeID
     */
    public int getEmployeeID() {
        return employeeID;
    }

    /**
     * @param employeeID the employeeID to set
     */
    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    // TODO: remove this roles and add them in employees table as DTOtype
    public enum Roles {
        CONTENT_EDITOR, CONTENT_MANAGER, COMPANY_MANAGER;
    }

}
