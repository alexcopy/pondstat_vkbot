package ru.gpsbox.vkbot.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SuggestIgnored entity.
 */
public class SuggestIgnoredDTO implements Serializable {

    private String id;

    private Integer pictid;

    private Long modelid;

    private Long resultid;

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

    public Long getResultid() {
        return resultid;
    }

    public void setResultid(Long resultid) {
        this.resultid = resultid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SuggestIgnoredDTO suggestIgnoredDTO = (SuggestIgnoredDTO) o;
        if(suggestIgnoredDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), suggestIgnoredDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SuggestIgnoredDTO{" +
            "id=" + getId() +
            ", pictid=" + getPictid() +
            ", modelid=" + getModelid() +
            ", resultid=" + getResultid() +
            "}";
    }
}
