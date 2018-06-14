package ru.gpsbox.natmob.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TrainedModel.
 */
@Document(collection = "trained_model")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "trainedmodel")
public class TrainedModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("model_name")
    private String modelName;

    @Field("posmatrix")
    private String posmatrix;

    @Field("negmatrix")
    private String negmatrix;

    @Field("neutmatrix")
    private String neutmatrix;

    @Field("modeltype")
    private Integer modeltype;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public TrainedModel modelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getPosmatrix() {
        return posmatrix;
    }

    public TrainedModel posmatrix(String posmatrix) {
        this.posmatrix = posmatrix;
        return this;
    }

    public void setPosmatrix(String posmatrix) {
        this.posmatrix = posmatrix;
    }

    public String getNegmatrix() {
        return negmatrix;
    }

    public TrainedModel negmatrix(String negmatrix) {
        this.negmatrix = negmatrix;
        return this;
    }

    public void setNegmatrix(String negmatrix) {
        this.negmatrix = negmatrix;
    }

    public String getNeutmatrix() {
        return neutmatrix;
    }

    public TrainedModel neutmatrix(String neutmatrix) {
        this.neutmatrix = neutmatrix;
        return this;
    }

    public void setNeutmatrix(String neutmatrix) {
        this.neutmatrix = neutmatrix;
    }

    public Integer getModeltype() {
        return modeltype;
    }

    public TrainedModel modeltype(Integer modeltype) {
        this.modeltype = modeltype;
        return this;
    }

    public void setModeltype(Integer modeltype) {
        this.modeltype = modeltype;
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
        TrainedModel trainedModel = (TrainedModel) o;
        if (trainedModel.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), trainedModel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TrainedModel{" +
            "id=" + getId() +
            ", modelName='" + getModelName() + "'" +
            ", posmatrix='" + getPosmatrix() + "'" +
            ", negmatrix='" + getNegmatrix() + "'" +
            ", neutmatrix='" + getNeutmatrix() + "'" +
            ", modeltype=" + getModeltype() +
            "}";
    }
}
