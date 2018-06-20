package ru.gpsbox.vkbot.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the VkGroup entity.
 */
public class VkGroupDTO implements Serializable {

    private String id;

    @NotNull
    private Integer groupid;

    @NotNull
    private String name;

    private String screenname;

    private String type;

    private Integer isclosed;

    private Integer proc;

    private Integer procusers;

    private Integer usersqty;

    private Integer usersadded;

    private Integer ignore;

    private String stateProvince;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreenname() {
        return screenname;
    }

    public void setScreenname(String screenname) {
        this.screenname = screenname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIsclosed() {
        return isclosed;
    }

    public void setIsclosed(Integer isclosed) {
        this.isclosed = isclosed;
    }

    public Integer getProc() {
        return proc;
    }

    public void setProc(Integer proc) {
        this.proc = proc;
    }

    public Integer getProcusers() {
        return procusers;
    }

    public void setProcusers(Integer procusers) {
        this.procusers = procusers;
    }

    public Integer getUsersqty() {
        return usersqty;
    }

    public void setUsersqty(Integer usersqty) {
        this.usersqty = usersqty;
    }

    public Integer getUsersadded() {
        return usersadded;
    }

    public void setUsersadded(Integer usersadded) {
        this.usersadded = usersadded;
    }

    public Integer getIgnore() {
        return ignore;
    }

    public void setIgnore(Integer ignore) {
        this.ignore = ignore;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VkGroupDTO vkGroupDTO = (VkGroupDTO) o;
        if(vkGroupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vkGroupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VkGroupDTO{" +
            "id=" + getId() +
            ", groupid=" + getGroupid() +
            ", name='" + getName() + "'" +
            ", screenname='" + getScreenname() + "'" +
            ", type='" + getType() + "'" +
            ", isclosed=" + getIsclosed() +
            ", proc=" + getProc() +
            ", procusers=" + getProcusers() +
            ", usersqty=" + getUsersqty() +
            ", usersadded=" + getUsersadded() +
            ", ignore=" + getIgnore() +
            ", stateProvince='" + getStateProvince() + "'" +
            "}";
    }
}
