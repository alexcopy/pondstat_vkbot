package ru.gpsbox.vkbot.domain;

import io.swagger.annotations.ApiModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Document(collection = "vk_group")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "vkgroup")
public class VkGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("groupid")
    private Integer groupid;

    @NotNull
    @Field("name")
    private String name;

    @Field("screenname")
    private String screenname;

    @Field("type")
    private String type;

    @Field("isclosed")
    private Integer isclosed;

    @Field("proc")
    private Integer proc;

    @Field("procusers")
    private Integer procusers;

    @Field("usersqty")
    private Integer usersqty;

    @Field("usersadded")
    private Integer usersadded;

    @Field("ignore")
    private Integer ignore;

    @Field("state_province")
    private String stateProvince;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public VkGroup groupid(Integer groupid) {
        this.groupid = groupid;
        return this;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public String getName() {
        return name;
    }

    public VkGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreenname() {
        return screenname;
    }

    public VkGroup screenname(String screenname) {
        this.screenname = screenname;
        return this;
    }

    public void setScreenname(String screenname) {
        this.screenname = screenname;
    }

    public String getType() {
        return type;
    }

    public VkGroup type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIsclosed() {
        return isclosed;
    }

    public VkGroup isclosed(Integer isclosed) {
        this.isclosed = isclosed;
        return this;
    }

    public void setIsclosed(Integer isclosed) {
        this.isclosed = isclosed;
    }

    public Integer getProc() {
        return proc;
    }

    public VkGroup proc(Integer proc) {
        this.proc = proc;
        return this;
    }

    public void setProc(Integer proc) {
        this.proc = proc;
    }

    public Integer getProcusers() {
        return procusers;
    }

    public VkGroup procusers(Integer procusers) {
        this.procusers = procusers;
        return this;
    }

    public void setProcusers(Integer procusers) {
        this.procusers = procusers;
    }

    public Integer getUsersqty() {
        return usersqty;
    }

    public VkGroup usersqty(Integer usersqty) {
        this.usersqty = usersqty;
        return this;
    }

    public void setUsersqty(Integer usersqty) {
        this.usersqty = usersqty;
    }

    public Integer getUsersadded() {
        return usersadded;
    }

    public VkGroup usersadded(Integer usersadded) {
        this.usersadded = usersadded;
        return this;
    }

    public void setUsersadded(Integer usersadded) {
        this.usersadded = usersadded;
    }

    public Integer getIgnore() {
        return ignore;
    }

    public VkGroup ignore(Integer ignore) {
        this.ignore = ignore;
        return this;
    }

    public void setIgnore(Integer ignore) {
        this.ignore = ignore;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public VkGroup stateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
        return this;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VkGroup vkGroup = (VkGroup) o;
        if (vkGroup.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vkGroup.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VkGroup{" +
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
