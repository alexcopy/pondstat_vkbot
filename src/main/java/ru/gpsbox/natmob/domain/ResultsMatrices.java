package ru.gpsbox.natmob.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ResultsMatrices.
 */
@Document(collection = "results_matrices")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "resultsmatrices")
public class ResultsMatrices implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("resid")
    private Long resid;

    @Field("result")
    private Integer result;

    @Field("type")
    private Integer type;

    @Field("matrix")
    private Integer matrix;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getResid() {
        return resid;
    }

    public ResultsMatrices resid(Long resid) {
        this.resid = resid;
        return this;
    }

    public void setResid(Long resid) {
        this.resid = resid;
    }

    public Integer getResult() {
        return result;
    }

    public ResultsMatrices result(Integer result) {
        this.result = result;
        return this;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Integer getType() {
        return type;
    }

    public ResultsMatrices type(Integer type) {
        this.type = type;
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getMatrix() {
        return matrix;
    }

    public ResultsMatrices matrix(Integer matrix) {
        this.matrix = matrix;
        return this;
    }

    public void setMatrix(Integer matrix) {
        this.matrix = matrix;
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
        ResultsMatrices resultsMatrices = (ResultsMatrices) o;
        if (resultsMatrices.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resultsMatrices.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResultsMatrices{" +
            "id=" + getId() +
            ", resid=" + getResid() +
            ", result=" + getResult() +
            ", type=" + getType() +
            ", matrix=" + getMatrix() +
            "}";
    }
}
