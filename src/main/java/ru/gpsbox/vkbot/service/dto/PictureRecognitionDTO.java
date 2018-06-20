package ru.gpsbox.vkbot.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the PictureRecognition entity.
 */
public class PictureRecognitionDTO implements Serializable {

    private String id;

    private Integer pictid;

    private Long modelid;

    private Integer simpres;

    private Integer medres;

    private Integer ignore;

    private Integer isselected;

    private Integer ismanual;

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

    public Integer getSimpres() {
        return simpres;
    }

    public void setSimpres(Integer simpres) {
        this.simpres = simpres;
    }

    public Integer getMedres() {
        return medres;
    }

    public void setMedres(Integer medres) {
        this.medres = medres;
    }

    public Integer getIgnore() {
        return ignore;
    }

    public void setIgnore(Integer ignore) {
        this.ignore = ignore;
    }

    public Integer getIsselected() {
        return isselected;
    }

    public void setIsselected(Integer isselected) {
        this.isselected = isselected;
    }

    public Integer getIsmanual() {
        return ismanual;
    }

    public void setIsmanual(Integer ismanual) {
        this.ismanual = ismanual;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PictureRecognitionDTO pictureRecognitionDTO = (PictureRecognitionDTO) o;
        if(pictureRecognitionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pictureRecognitionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PictureRecognitionDTO{" +
            "id=" + getId() +
            ", pictid=" + getPictid() +
            ", modelid=" + getModelid() +
            ", simpres=" + getSimpres() +
            ", medres=" + getMedres() +
            ", ignore=" + getIgnore() +
            ", isselected=" + getIsselected() +
            ", ismanual=" + getIsmanual() +
            "}";
    }
}
