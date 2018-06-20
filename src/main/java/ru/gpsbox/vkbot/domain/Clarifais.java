package ru.gpsbox.vkbot.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Clarifais.
 */
@Document(collection = "clarifais")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "clarifais")
public class Clarifais implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("email")
    private String email;

    @Field("emailpassword")
    private String emailpassword;

    @Field("apikey")
    private String apikey;

    @Field("modelname")
    private String modelname;

    @Field("country")
    private String country;

    @Field("billday")
    private String billday;

    @Field("totalusage")
    private Integer totalusage;

    @Field("count")
    private Integer count;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Clarifais name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public Clarifais email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailpassword() {
        return emailpassword;
    }

    public Clarifais emailpassword(String emailpassword) {
        this.emailpassword = emailpassword;
        return this;
    }

    public void setEmailpassword(String emailpassword) {
        this.emailpassword = emailpassword;
    }

    public String getApikey() {
        return apikey;
    }

    public Clarifais apikey(String apikey) {
        this.apikey = apikey;
        return this;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getModelname() {
        return modelname;
    }

    public Clarifais modelname(String modelname) {
        this.modelname = modelname;
        return this;
    }

    public void setModelname(String modelname) {
        this.modelname = modelname;
    }

    public String getCountry() {
        return country;
    }

    public Clarifais country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBillday() {
        return billday;
    }

    public Clarifais billday(String billday) {
        this.billday = billday;
        return this;
    }

    public void setBillday(String billday) {
        this.billday = billday;
    }

    public Integer getTotalusage() {
        return totalusage;
    }

    public Clarifais totalusage(Integer totalusage) {
        this.totalusage = totalusage;
        return this;
    }

    public void setTotalusage(Integer totalusage) {
        this.totalusage = totalusage;
    }

    public Integer getCount() {
        return count;
    }

    public Clarifais count(Integer count) {
        this.count = count;
        return this;
    }

    public void setCount(Integer count) {
        this.count = count;
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
        Clarifais clarifais = (Clarifais) o;
        if (clarifais.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clarifais.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Clarifais{" +
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
