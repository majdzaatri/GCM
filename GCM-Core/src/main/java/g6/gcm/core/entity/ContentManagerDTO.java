package g6.gcm.core.entity;

import g6.gcm.core.factories.DTOsFactory;

public class ContentManagerDTO extends EmployeeDTO {

    @Override
    protected void setDTOType() {
        this.type = DTOsFactory.CONTENT_MANAGER;
    }

    @Override
    public void setRole(Roles role) {
        this.role = role;
    }
}
