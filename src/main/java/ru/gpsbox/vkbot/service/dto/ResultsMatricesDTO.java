package ru.gpsbox.vkbot.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ResultsMatrices entity.
 */
public class ResultsMatricesDTO implements Serializable {

    private String id;

    private Long resid;

    private Integer result;

    private Integer type;

    private Integer matrix;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getResid() {
        return resid;
    }

    public void setResid(Long resid) {
        this.resid = resid;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getMatrix() {
        return matrix;
    }

    public void setMatrix(Integer matrix) {
        this.matrix = matrix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResultsMatricesDTO resultsMatricesDTO = (ResultsMatricesDTO) o;
        if(resultsMatricesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resultsMatricesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResultsMatricesDTO{" +
            "id=" + getId() +
            ", resid=" + getResid() +
            ", result=" + getResult() +
            ", type=" + getType() +
            ", matrix=" + getMatrix() +
            "}";
    }
}
