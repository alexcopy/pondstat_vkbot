package ru.gpsbox.vkbot.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A VkPicture.
 */
@Document(collection = "vk_picture")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "vkpicture")
public class VkPicture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("pict_id")
    private String pictId;

    @NotNull
    @Field("owner_id")
    private Integer ownerId;

    @NotNull
    @Field("url")
    private String url;

    @Field("proc")
    private Integer proc;

    @Field("downloaded")
    private Integer downloaded;

    @Field("deleterank")
    private Integer deleterank;

    @Field("type")
    private String type;

    @Field("procid")
    private Integer procid;

    @Field("recognized")
    private Integer recognized;

    @Field("timestamp")
    private Integer timestamp;

    @Field("picdate")
    private Integer picdate;

    @Field("size")
    private Integer size;

    @Field("text")
    private String text;

    @Field("likes")
    private Integer likes;

    @Field("ignored")
    private Integer ignored;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPictId() {
        return pictId;
    }

    public VkPicture pictId(String pictId) {
        this.pictId = pictId;
        return this;
    }

    public void setPictId(String pictId) {
        this.pictId = pictId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public VkPicture ownerId(Integer ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getUrl() {
        return url;
    }

    public VkPicture url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getProc() {
        return proc;
    }

    public VkPicture proc(Integer proc) {
        this.proc = proc;
        return this;
    }

    public void setProc(Integer proc) {
        this.proc = proc;
    }

    public Integer getDownloaded() {
        return downloaded;
    }

    public VkPicture downloaded(Integer downloaded) {
        this.downloaded = downloaded;
        return this;
    }

    public void setDownloaded(Integer downloaded) {
        this.downloaded = downloaded;
    }

    public Integer getDeleterank() {
        return deleterank;
    }

    public VkPicture deleterank(Integer deleterank) {
        this.deleterank = deleterank;
        return this;
    }

    public void setDeleterank(Integer deleterank) {
        this.deleterank = deleterank;
    }

    public String getType() {
        return type;
    }

    public VkPicture type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getProcid() {
        return procid;
    }

    public VkPicture procid(Integer procid) {
        this.procid = procid;
        return this;
    }

    public void setProcid(Integer procid) {
        this.procid = procid;
    }

    public Integer getRecognized() {
        return recognized;
    }

    public VkPicture recognized(Integer recognized) {
        this.recognized = recognized;
        return this;
    }

    public void setRecognized(Integer recognized) {
        this.recognized = recognized;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public VkPicture timestamp(Integer timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getPicdate() {
        return picdate;
    }

    public VkPicture picdate(Integer picdate) {
        this.picdate = picdate;
        return this;
    }

    public void setPicdate(Integer picdate) {
        this.picdate = picdate;
    }

    public Integer getSize() {
        return size;
    }

    public VkPicture size(Integer size) {
        this.size = size;
        return this;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getText() {
        return text;
    }

    public VkPicture text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getLikes() {
        return likes;
    }

    public VkPicture likes(Integer likes) {
        this.likes = likes;
        return this;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getIgnored() {
        return ignored;
    }

    public VkPicture ignored(Integer ignored) {
        this.ignored = ignored;
        return this;
    }

    public void setIgnored(Integer ignored) {
        this.ignored = ignored;
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
        VkPicture vkPicture = (VkPicture) o;
        if (vkPicture.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vkPicture.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VkPicture{" +
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
