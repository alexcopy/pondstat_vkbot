package ru.gpsbox.natmob.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SelectedKeyWords.
 */
@Document(collection = "selected_key_words")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "selectedkeywords")
public class SelectedKeyWords implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("modelid")
    private Long modelid;

    @Field("posmatrix")
    private String posmatrix;

    @Field("neutmatrix")
    private String neutmatrix;

    @Field("negmatrix")
    private String negmatrix;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getModelid() {
        return modelid;
    }

    public SelectedKeyWords modelid(Long modelid) {
        this.modelid = modelid;
        return this;
    }

    public void setModelid(Long modelid) {
        this.modelid = modelid;
    }

    public String getPosmatrix() {
        return posmatrix;
    }

    public SelectedKeyWords posmatrix(String posmatrix) {
        this.posmatrix = posmatrix;
        return this;
    }

    public void setPosmatrix(String posmatrix) {
        this.posmatrix = posmatrix;
    }

    public String getNeutmatrix() {
        return neutmatrix;
    }

    public SelectedKeyWords neutmatrix(String neutmatrix) {
        this.neutmatrix = neutmatrix;
        return this;
    }

    public void setNeutmatrix(String neutmatrix) {
        this.neutmatrix = neutmatrix;
    }

    public String getNegmatrix() {
        return negmatrix;
    }

    public SelectedKeyWords negmatrix(String negmatrix) {
        this.negmatrix = negmatrix;
        return this;
    }

    public void setNegmatrix(String negmatrix) {
        this.negmatrix = negmatrix;
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
        SelectedKeyWords selectedKeyWords = (SelectedKeyWords) o;
        if (selectedKeyWords.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), selectedKeyWords.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SelectedKeyWords{" +
            "id=" + getId() +
            ", modelid=" + getModelid() +
            ", posmatrix='" + getPosmatrix() + "'" +
            ", neutmatrix='" + getNeutmatrix() + "'" +
            ", negmatrix='" + getNegmatrix() + "'" +
            "}";
    }
}
