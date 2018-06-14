package ru.gpsbox.natmob.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SuggestIgnored.
 */
@Document(collection = "suggest_ignored")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "suggestignored")
public class SuggestIgnored implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("pictid")
    private Integer pictid;

    @Field("modelid")
    private Long modelid;

    @Field("resultid")
    private Long resultid;

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

    public SuggestIgnored pictid(Integer pictid) {
        this.pictid = pictid;
        return this;
    }

    public void setPictid(Integer pictid) {
        this.pictid = pictid;
    }

    public Long getModelid() {
        return modelid;
    }

    public SuggestIgnored modelid(Long modelid) {
        this.modelid = modelid;
        return this;
    }

    public void setModelid(Long modelid) {
        this.modelid = modelid;
    }

    public Long getResultid() {
        return resultid;
    }

    public SuggestIgnored resultid(Long resultid) {
        this.resultid = resultid;
        return this;
    }

    public void setResultid(Long resultid) {
        this.resultid = resultid;
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
        SuggestIgnored suggestIgnored = (SuggestIgnored) o;
        if (suggestIgnored.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), suggestIgnored.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SuggestIgnored{" +
            "id=" + getId() +
            ", pictid=" + getPictid() +
            ", modelid=" + getModelid() +
            ", resultid=" + getResultid() +
            "}";
    }
}
