package ru.gpsbox.vkbot.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ClarifaiProcess entity.
 */
public class ClarifaiProcessDTO implements Serializable {

    private String id;

    @NotNull
    private Integer pictid;

    private Integer pictkey;

    @NotNull
    private String url;

    private String firstfive;

    private Integer ignore;

    private Integer proc;

    private String model;

    private String rawdata;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPictid() {
        return pictid;
    }

    public void setPictid(Integer pictid) {
        this.pictid = pictid;
    }

    public Integer getPictkey() {
        return pictkey;
    }

    public void setPictkey(Integer pictkey) {
        this.pictkey = pictkey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFirstfive() {
        return firstfive;
    }

    public void setFirstfive(String firstfive) {
        this.firstfive = firstfive;
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRawdata() {
        return rawdata;
    }

    public void setRawdata(String rawdata) {
        this.rawdata = rawdata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClarifaiProcessDTO clarifaiProcessDTO = (ClarifaiProcessDTO) o;
        if(clarifaiProcessDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clarifaiProcessDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClarifaiProcessDTO{" +
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
