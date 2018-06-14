package ru.gpsbox.natmob.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the VkUser entity.
 */
public class VkUserDTO implements Serializable {

    private String id;

    @NotNull
    private Integer userId;

    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

    @NotNull
    private String sex;

    private String bdate;

    private Integer cityid;

    private String citytitle;

    private Integer countryid;

    private String countrytitle;

    private Integer groupscount;

    private Integer groupsproc;

    private Integer images;

    private Integer imagesproc;

    private Integer lastseen;

    private String platform;

    private Integer ignore;

    private Integer proc;

    private String photo100;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBdate() {
        return bdate;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }

    public Integer getCityid() {
        return cityid;
    }

    public void setCityid(Integer cityid) {
        this.cityid = cityid;
    }

    public String getCitytitle() {
        return citytitle;
    }

    public void setCitytitle(String citytitle) {
        this.citytitle = citytitle;
    }

    public Integer getCountryid() {
        return countryid;
    }

    public void setCountryid(Integer countryid) {
        this.countryid = countryid;
    }

    public String getCountrytitle() {
        return countrytitle;
    }

    public void setCountrytitle(String countrytitle) {
        this.countrytitle = countrytitle;
    }

    public Integer getGroupscount() {
        return groupscount;
    }

    public void setGroupscount(Integer groupscount) {
        this.groupscount = groupscount;
    }

    public Integer getGroupsproc() {
        return groupsproc;
    }

    public void setGroupsproc(Integer groupsproc) {
        this.groupsproc = groupsproc;
    }

    public Integer getImages() {
        return images;
    }

    public void setImages(Integer images) {
        this.images = images;
    }

    public Integer getImagesproc() {
        return imagesproc;
    }

    public void setImagesproc(Integer imagesproc) {
        this.imagesproc = imagesproc;
    }

    public Integer getLastseen() {
        return lastseen;
    }

    public void setLastseen(Integer lastseen) {
        this.lastseen = lastseen;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Integer getIgnore() {
        return ignore;
    }

    public void setIgnore(Integer ignore) {
        this.ignore = ignore;
    }

    public Integer getProc() {
        return proc;
    }

    public void setProc(Integer proc) {
        this.proc = proc;
    }

    public String getPhoto100() {
        return photo100;
    }

    public void setPhoto100(String photo100) {
        this.photo100 = photo100;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VkUserDTO vkUserDTO = (VkUserDTO) o;
        if(vkUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vkUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VkUserDTO{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", firstname='" + getFirstname() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", sex='" + getSex() + "'" +
            ", bdate='" + getBdate() + "'" +
            ", cityid=" + getCityid() +
            ", citytitle='" + getCitytitle() + "'" +
            ", countryid=" + getCountryid() +
            ", countrytitle='" + getCountrytitle() + "'" +
            ", groupscount=" + getGroupscount() +
            ", groupsproc=" + getGroupsproc() +
            ", images=" + getImages() +
            ", imagesproc=" + getImagesproc() +
            ", lastseen=" + getLastseen() +
            ", platform='" + getPlatform() + "'" +
            ", ignore=" + getIgnore() +
            ", proc=" + getProc() +
            ", photo100='" + getPhoto100() + "'" +
            "}";
    }
}
