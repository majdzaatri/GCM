package g6.gcm.core.entity;

import g6.gcm.core.factories.DTOsFactory;

public class ContentEditorDTO extends EmployeeDTO {

    @Override
    protected void setDTOType() {
        this.type = DTOsFactory.CONTENT_EDITOR;

    }

    @Override
    public void setRole(Roles role) {
        this.role = role;
    }

}
