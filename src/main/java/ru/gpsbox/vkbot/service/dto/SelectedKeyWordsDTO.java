package ru.gpsbox.vkbot.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SelectedKeyWords entity.
 */
public class SelectedKeyWordsDTO implements Serializable {

    private String id;

    private Long modelid;

    private String posmatrix;

    private String neutmatrix;

    private String negmatrix;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getModelid() {
        return modelid;
    }

    public void setModelid(Long modelid) {
        this.modelid = modelid;
    }

    public String getPosmatrix() {
        return posmatrix;
    }

    public void setPosmatrix(String posmatrix) {
        this.posmatrix = posmatrix;
    }

    public String getNeutmatrix() {
        return neutmatrix;
    }

    public void setNeutmatrix(String neutmatrix) {
        this.neutmatrix = neutmatrix;
    }

    public String getNegmatrix() {
        return negmatrix;
    }

    public void setNegmatrix(String negmatrix) {
        this.negmatrix = negmatrix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SelectedKeyWordsDTO selectedKeyWordsDTO = (SelectedKeyWordsDTO) o;
        if(selectedKeyWordsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), selectedKeyWordsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SelectedKeyWordsDTO{" +
            "id=" + getId() +
            ", modelid=" + getModelid() +
            ", posmatrix='" + getPosmatrix() + "'" +
            ", neutmatrix='" + getNeutmatrix() + "'" +
            ", negmatrix='" + getNegmatrix() + "'" +
            "}";
    }
}
