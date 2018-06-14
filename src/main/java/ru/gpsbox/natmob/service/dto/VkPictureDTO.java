package ru.gpsbox.natmob.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the VkPicture entity.
 */
public class VkPictureDTO implements Serializable {

    private String id;

    @NotNull
    private String pictId;

    @NotNull
    private Integer ownerId;

    @NotNull
    private String url;

    private Integer proc;

    private Integer downloaded;

    private Integer deleterank;

    private String type;

    private Integer procid;

    private Integer recognized;

    private Integer timestamp;

    private Integer picdate;

    private Integer size;

    private String text;

    private Integer likes;

    private Integer ignored;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPictId() {
        return pictId;
    }

    public void setPictId(String pictId) {
        this.pictId = pictId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getProc() {
        return proc;
    }

    public void setProc(Integer proc) {
        this.proc = proc;
    }

    public Integer getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(Integer downloaded) {
        this.downloaded = downloaded;
    }

    public Integer getDeleterank() {
        return deleterank;
    }

    public void setDeleterank(Integer deleterank) {
        this.deleterank = deleterank;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getProcid() {
        return procid;
    }

    public void setProcid(Integer procid) {
        this.procid = procid;
    }

    public Integer getRecognized() {
        return recognized;
    }

    public void setRecognized(Integer recognized) {
        this.recognized = recognized;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getPicdate() {
        return picdate;
    }

    public void setPicdate(Integer picdate) {
        this.picdate = picdate;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getIgnored() {
        return ignored;
    }

    public void setIgnored(Integer ignored) {
        this.ignored = ignored;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VkPictureDTO vkPictureDTO = (VkPictureDTO) o;
        if(vkPictureDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vkPictureDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VkPictureDTO{" +
            "id=" + getId() +
            ", pictId='" + getPictId() + "'" +
            ", ownerId=" + getOwnerId() +
            ", url='" + getUrl() + "'" +
            ", proc=" + getProc() +
            ", downloaded=" + getDownloaded() +
            ", deleterank=" + getDeleterank() +
            ", type='" + getType() + "'" +
            ", procid=" + getProcid() +
            ", recognized=" + getRecognized() +
            ", timestamp=" + getTimestamp() +
            ", picdate=" + getPicdate() +
            ", size=" + getSize() +
            ", text='" + getText() + "'" +
            ", likes=" + getLikes() +
            ", ignored=" + getIgnored() +
            "}";
    }
}
