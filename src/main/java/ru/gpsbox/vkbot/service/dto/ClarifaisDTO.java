package ru.gpsbox.vkbot.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Clarifais entity.
 */
public class ClarifaisDTO implements Serializable {

    private String id;

    private String name;

    private String email;

    private String emailpassword;

    private String apikey;

    private String modelname;

    private String country;

    private String billday;

    private Integer totalusage;

    private Integer count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailpassword() {
        return emailpassword;
    }

    public void setEmailpassword(String emailpassword) {
        this.emailpassword = emailpassword;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getModelname() {
        return modelname;
    }

    public void setModelname(String modelname) {
        this.modelname = modelname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBillday() {
        return billday;
    }

    public void setBillday(String billday) {
        this.billday = billday;
    }

    public Integer getTotalusage() {
        return totalusage;
    }

    public void setTotalusage(Integer totalusage) {
        this.totalusage = totalusage;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClarifaisDTO clarifaisDTO = (ClarifaisDTO) o;
        if(clarifaisDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clarifaisDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClarifaisDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", emailpassword='" + getEmailpassword() + "'" +
            ", apikey='" + getApikey() + "'" +
            ", modelname='" + getModelname() + "'" +
            ", country='" + getCountry() + "'" +
            ", billday='" + getBillday() + "'" +
            ", totalusage=" + getTotalusage() +
            ", count=" + getCount() +
            "}";
    }
}
