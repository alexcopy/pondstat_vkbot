package ru.gpsbox.natmob.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PictureRecognition.
 */
@Document(collection = "picture_recognition")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "picturerecognition")
public class PictureRecognition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("pictid")
    private Integer pictid;

    @Field("modelid")
    private Long modelid;

    @Field("simpres")
    private Integer simpres;

    @Field("medres")
    private Integer medres;

    @Field("ignore")
    private Integer ignore;

    @Field("isselected")
    private Integer isselected;

    @Field("ismanual")
    private Integer ismanual;

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

    public PictureRecognition pictid(Integer pictid) {
        this.pictid = pictid;
        return this;
    }

    public void setPictid(Integer pictid) {
        this.pictid = pictid;
    }

    public Long getModelid() {
        return modelid;
    }

    public PictureRecognition modelid(Long modelid) {
        this.modelid = modelid;
        return this;
    }

    public void setModelid(Long modelid) {
        this.modelid = modelid;
    }

    public Integer getSimpres() {
        return simpres;
    }

    public PictureRecognition simpres(Integer simpres) {
        this.simpres = simpres;
        return this;
    }

    public void setSimpres(Integer simpres) {
        this.simpres = simpres;
    }

    public Integer getMedres() {
        return medres;
    }

    public PictureRecognition medres(Integer medres) {
        this.medres = medres;
        return this;
    }

    public void setMedres(Integer medres) {
        this.medres = medres;
    }

    public Integer getIgnore() {
        return ignore;
    }

    public PictureRecognition ignore(Integer ignore) {
        this.ignore = ignore;
        return this;
    }

    public void setIgnore(Integer ignore) {
        this.ignore = ignore;
    }

    public Integer getIsselected() {
        return isselected;
    }

    public PictureRecognition isselected(Integer isselected) {
        this.isselected = isselected;
        return this;
    }

    public void setIsselected(Integer isselected) {
        this.isselected = isselected;
    }

    public Integer getIsmanual() {
        return ismanual;
    }

    public PictureRecognition ismanual(Integer ismanual) {
        this.ismanual = ismanual;
        return this;
    }

    public void setIsmanual(Integer ismanual) {
        this.ismanual = ismanual;
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
        PictureRecognition pictureRecognition = (PictureRecognition) o;
        if (pictureRecognition.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pictureRecognition.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PictureRecognition{" +
            "id=" + getId() +
            ", pictid=" + getPictid() +
            ", modelid=" + getModelid() +
            ", simpres=" + getSimpres() +
            ", medres=" + getMedres() +
            ", ignore=" + getIgnore() +
            ", isselected=" + getIsselected() +
            ", ismanual=" + getIsmanual() +
            "}";
    }
}
