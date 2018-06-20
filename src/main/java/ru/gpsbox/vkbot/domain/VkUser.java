package ru.gpsbox.vkbot.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A VkUser.
 */
@Document(collection = "vk_user")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "vkuser")
public class VkUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("user_id")
    private Integer userId;

    @NotNull
    @Field("firstname")
    private String firstname;

    @NotNull
    @Field("lastname")
    private String lastname;

    @NotNull
    @Field("sex")
    private String sex;

    @Field("bdate")
    private String bdate;

    @Field("cityid")
    private Integer cityid;

    @Field("citytitle")
    private String citytitle;

    @Field("countryid")
    private Integer countryid;

    @Field("countrytitle")
    private String countrytitle;

    @Field("groupscount")
    private Integer groupscount;

    @Field("groupsproc")
    private Integer groupsproc;

    @Field("images")
    private Integer images;

    @Field("imagesproc")
    private Integer imagesproc;

    @Field("lastseen")
    private Integer lastseen;

    @Field("platform")
    private String platform;

    @Field("ignore")
    private Integer ignore;

    @Field("proc")
    private Integer proc;

    @Field("photo_100")
    private String photo100;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public VkUser userId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public VkUser firstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public VkUser lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getSex() {
        return sex;
    }

    public VkUser sex(String sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBdate() {
        return bdate;
    }

    public VkUser bdate(String bdate) {
        this.bdate = bdate;
        return this;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }

    public Integer getCityid() {
        return cityid;
    }

    public VkUser cityid(Integer cityid) {
        this.cityid = cityid;
        return this;
    }

    public void setCityid(Integer cityid) {
        this.cityid = cityid;
    }

    public String getCitytitle() {
        return citytitle;
    }

    public VkUser citytitle(String citytitle) {
        this.citytitle = citytitle;
        return this;
    }

    public void setCitytitle(String citytitle) {
        this.citytitle = citytitle;
    }

    public Integer getCountryid() {
        return countryid;
    }

    public VkUser countryid(Integer countryid) {
        this.countryid = countryid;
        return this;
    }

    public void setCountryid(Integer countryid) {
        this.countryid = countryid;
    }

    public String getCountrytitle() {
        return countrytitle;
    }

    public VkUser countrytitle(String countrytitle) {
        this.countrytitle = countrytitle;
        return this;
    }

    public void setCountrytitle(String countrytitle) {
        this.countrytitle = countrytitle;
    }

    public Integer getGroupscount() {
        return groupscount;
    }

    public VkUser groupscount(Integer groupscount) {
        this.groupscount = groupscount;
        return this;
    }

    public void setGroupscount(Integer groupscount) {
        this.groupscount = groupscount;
    }

    public Integer getGroupsproc() {
        return groupsproc;
    }

    public VkUser groupsproc(Integer groupsproc) {
        this.groupsproc = groupsproc;
        return this;
    }

    public void setGroupsproc(Integer groupsproc) {
        this.groupsproc = groupsproc;
    }

    public Integer getImages() {
        return images;
    }

    public VkUser images(Integer images) {
        this.images = images;
        return this;
    }

    public void setImages(Integer images) {
        this.images = images;
    }

    public Integer getImagesproc() {
        return imagesproc;
    }

    public VkUser imagesproc(Integer imagesproc) {
        this.imagesproc = imagesproc;
        return this;
    }

    public void setImagesproc(Integer imagesproc) {
        this.imagesproc = imagesproc;
    }

    public Integer getLastseen() {
        return lastseen;
    }

    public VkUser lastseen(Integer lastseen) {
        this.lastseen = lastseen;
        return this;
    }

    public void setLastseen(Integer lastseen) {
        this.lastseen = lastseen;
    }

    public String getPlatform() {
        return platform;
    }

    public VkUser platform(String platform) {
        this.platform = platform;
        return this;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Integer getIgnore() {
        return ignore;
    }

    public VkUser ignore(Integer ignore) {
        this.ignore = ignore;
        return this;
    }

    public void setIgnore(Integer ignore) {
        this.ignore = ignore;
    }

    public Integer getProc() {
        return proc;
    }

    public VkUser proc(Integer proc) {
        this.proc = proc;
        return this;
    }

    public void setProc(Integer proc) {
        this.proc = proc;
    }

    public String getPhoto100() {
        return photo100;
    }

    public VkUser photo100(String photo100) {
        this.photo100 = photo100;
        return this;
    }

    public void setPhoto100(String photo100) {
        this.photo100 = photo100;
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
        VkUser vkUser = (VkUser) o;
        if (vkUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vkUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VkUser{" +
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
