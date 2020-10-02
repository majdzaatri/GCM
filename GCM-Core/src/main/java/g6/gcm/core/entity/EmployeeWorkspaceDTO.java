package g6.gcm.core.entity;

import java.util.List;

public class EmployeeWorkspaceDTO {

    private EmployeeDTO employee;
    private List<CoordinatizedDTO> lockedMapCollections;

    /**
     * @return the employee
     */
    public EmployeeDTO getEmployee() {
        return employee;
    }

    /**
     * @param employee the employee to set
     */
    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    /**
     * @return the lockedMapCollections
     */
    public List<CoordinatizedDTO> getLockedMapCollections() {
        return lockedMapCollections;
    }

    /**
     * @param lockedMapCollections the lockedMapCollections to set
     */
    public void setLockedMapCollections(List<CoordinatizedDTO> lockedMapCollections) {
        this.lockedMapCollections = lockedMapCollections;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((employee == null) ? 0 : employee.hashCode());
        result =
                prime * result + ((lockedMapCollections == null) ? 0 : lockedMapCollections.hashCode());
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
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EmployeeWorkspaceDTO other = (EmployeeWorkspaceDTO) obj;
        if (employee == null) {
            if (other.employee != null)
                return false;
        } else if (!employee.equals(other.employee))
            return false;
        if (lockedMapCollections == null) {
            if (other.lockedMapCollections != null)
                return false;
        } else if (!lockedMapCollections.equals(other.lockedMapCollections))
            return false;
        return true;
    }


}
