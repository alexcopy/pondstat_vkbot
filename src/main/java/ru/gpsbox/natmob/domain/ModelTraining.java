package ru.gpsbox.natmob.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ModelTraining.
 */
@Document(collection = "model_training")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "modeltraining")
public class ModelTraining implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("pictid")
    private Integer pictid;

    @Field("modelid")
    private Long modelid;

    @Field("procid")
    private Integer procid;

    @Field("result")
    private Integer result;

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

    public ModelTraining pictid(Integer pictid) {
        this.pictid = pictid;
        return this;
    }

    public void setPictid(Integer pictid) {
        this.pictid = pictid;
    }

    public Long getModelid() {
        return modelid;
    }

    public ModelTraining modelid(Long modelid) {
        this.modelid = modelid;
        return this;
    }

    public void setModelid(Long modelid) {
        this.modelid = modelid;
    }

    public Integer getProcid() {
        return procid;
    }

    public ModelTraining procid(Integer procid) {
        this.procid = procid;
        return this;
    }

    public void setProcid(Integer procid) {
        this.procid = procid;
    }

    public Integer getResult() {
        return result;
    }

    public ModelTraining result(Integer result) {
        this.result = result;
        return this;
    }

    public void setResult(Integer result) {
        this.result = result;
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
        ModelTraining modelTraining = (ModelTraining) o;
        if (modelTraining.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), modelTraining.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ModelTraining{" +
            "id=" + getId() +
            ", pictid=" + getPictid() +
            ", modelid=" + getModelid() +
            ", procid=" + getProcid() +
            ", result=" + getResult() +
            "}";
    }
}
