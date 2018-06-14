package ru.gpsbox.natmob.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ClarifaiProcess.
 */
@Document(collection = "clarifai_process")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "clarifaiprocess")
public class ClarifaiProcess implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("pictid")
    private Integer pictid;

    @Field("pictkey")
    private Integer pictkey;

    @NotNull
    @Field("url")
    private String url;

    @Field("firstfive")
    private String firstfive;

    @Field("ignore")
    private Integer ignore;

    @Field("proc")
    private Integer proc;

    @Field("model")
    private String model;

    @Field("rawdata")
    private String rawdata;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPictid() {
        return pictid;
    }

    public ClarifaiProcess pictid(Integer pictid) {
        this.pictid = pictid;
        return this;
    }

    public void setPictid(Integer pictid) {
        this.pictid = pictid;
    }

    public Integer getPictkey() {
        return pictkey;
    }

    public ClarifaiProcess pictkey(Integer pictkey) {
        this.pictkey = pictkey;
        return this;
    }

    public void setPictkey(Integer pictkey) {
        this.pictkey = pictkey;
    }

    public String getUrl() {
        return url;
    }

    public ClarifaiProcess url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFirstfive() {
        return firstfive;
    }

    public ClarifaiProcess firstfive(String firstfive) {
        this.firstfive = firstfive;
        return this;
    }

    public void setFirstfive(String firstfive) {
        this.firstfive = firstfive;
    }

    public Integer getIgnore() {
        return ignore;
    }

    public ClarifaiProcess ignore(Integer ignore) {
        this.ignore = ignore;
        return this;
    }

    public void setIgnore(Integer ignore) {
        this.ignore = ignore;
    }

    public Integer getProc() {
        return proc;
    }

    public ClarifaiProcess proc(Integer proc) {
        this.proc = proc;
        return this;
    }

    public void setProc(Integer proc) {
        this.proc = proc;
    }

    public String getModel() {
        return model;
    }

    public ClarifaiProcess model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRawdata() {
        return rawdata;
    }

    public ClarifaiProcess rawdata(String rawdata) {
        this.rawdata = rawdata;
        return this;
    }

    public void setRawdata(String rawdata) {
        this.rawdata = rawdata;
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
        ClarifaiProcess clarifaiProcess = (ClarifaiProcess) o;
        if (clarifaiProcess.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clarifaiProcess.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClarifaiProcess{" +
            "id=" + getId() +
            ", pictid=" + getPictid() +
            ", pictkey=" + getPictkey() +
            ", url='" + getUrl() + "'" +
            ", firstfive='" + getFirstfive() + "'" +
            ", ignore=" + getIgnore() +
            ", proc=" + getProc() +
            ", model='" + getModel() + "'" +
            ", rawdata='" + getRawdata() + "'" +
            "}";
    }
}
