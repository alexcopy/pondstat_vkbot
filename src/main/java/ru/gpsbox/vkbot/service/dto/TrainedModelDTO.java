package ru.gpsbox.vkbot.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TrainedModel entity.
 */
public class TrainedModelDTO implements Serializable {

    private String id;

    @NotNull
    private String modelName;

    private String posmatrix;

    private String negmatrix;

    private String neutmatrix;

    private Long modeltype;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getPosmatrix() {
        return posmatrix;
    }

    public void setPosmatrix(String posmatrix) {
        this.posmatrix = posmatrix;
    }

    public String getNegmatrix() {
        return negmatrix;
    }

    public void setNegmatrix(String negmatrix) {
        this.negmatrix = negmatrix;
    }

    public String getNeutmatrix() {
        return neutmatrix;
    }

    public void setNeutmatrix(String neutmatrix) {
        this.neutmatrix = neutmatrix;
    }

    public Long getModeltype() {
        return modeltype;
    }

    public void setModeltype(Long modeltype) {
        this.modeltype = modeltype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TrainedModelDTO trainedModelDTO = (TrainedModelDTO) o;
        if(trainedModelDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), trainedModelDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TrainedModelDTO{" +
            "id=" + getId() +
            ", modelName='" + getModelName() + "'" +
            ", posmatrix='" + getPosmatrix() + "'" +
            ", negmatrix='" + getNegmatrix() + "'" +
            ", neutmatrix='" + getNeutmatrix() + "'" +
            ", modeltype=" + getModeltype() +
            "}";
    }
}
