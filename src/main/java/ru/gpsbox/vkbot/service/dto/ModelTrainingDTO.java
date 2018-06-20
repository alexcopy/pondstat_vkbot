package ru.gpsbox.vkbot.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ModelTraining entity.
 */
public class ModelTrainingDTO implements Serializable {

    private String id;

    private Integer pictid;

    private Long modelid;

    private Integer procid;

    private Integer result;

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

    public Long getModelid() {
        return modelid;
    }

    public void setModelid(Long modelid) {
        this.modelid = modelid;
    }

    public Integer getProcid() {
        return procid;
    }

    public void setProcid(Integer procid) {
        this.procid = procid;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ModelTrainingDTO modelTrainingDTO = (ModelTrainingDTO) o;
        if(modelTrainingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), modelTrainingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ModelTrainingDTO{" +
            "id=" + getId() +
            ", pictid=" + getPictid() +
            ", modelid=" + getModelid() +
            ", procid=" + getProcid() +
            ", result=" + getResult() +
            "}";
    }
}
