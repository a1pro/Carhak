package com.app.carhak.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubPartData {

    @SerializedName("sub_cat_id")
    @Expose
    private String subCatId;
    @SerializedName("part_cat_id")
    @Expose
    private String partCatId;
    @SerializedName("part_sub_cat_name")
    @Expose
    private String partSubCatName;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public String getSubCatId() {
        return subCatId;
    }

    public void setSubCatId(String subCatId) {
        this.subCatId = subCatId;
    }

    public String getPartCatId() {
        return partCatId;
    }

    public void setPartCatId(String partCatId) {
        this.partCatId = partCatId;
    }

    public String getPartSubCatName() {
        return partSubCatName;
    }

    public void setPartSubCatName(String partSubCatName) {
        this.partSubCatName = partSubCatName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
