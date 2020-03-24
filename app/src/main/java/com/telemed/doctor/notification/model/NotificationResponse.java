package com.telemed.doctor.notification.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.telemed.doctor.notification.model.NotificationModel;

import java.util.List;

public class NotificationResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {

        @SerializedName("totalCount")
        @Expose
        private Integer totalCount;
        @SerializedName("pageSize")
        @Expose
        private Integer pageSize;
        @SerializedName("currentPage")
        @Expose
        private Integer currentPage;
        @SerializedName("totalPages")
        @Expose
        private Integer totalPages;
        @SerializedName("previousPage")
        @Expose
        private Boolean previousPage;
        @SerializedName("nextPage")
        @Expose
        private Boolean nextPage;
        @SerializedName("searchQuery")
        @Expose
        private String searchQuery;
        @SerializedName("dataList")
        @Expose
        private List<NotificationModel> dataList = null;

        public Integer getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(Integer totalCount) {
            this.totalCount = totalCount;
        }

        public Integer getPageSize() {
            return pageSize;
        }

        public void setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
        }

        public Integer getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(Integer currentPage) {
            this.currentPage = currentPage;
        }

        public Integer getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(Integer totalPages) {
            this.totalPages = totalPages;
        }

        public Boolean getPreviousPage() {
            return previousPage;
        }

        public void setPreviousPage(Boolean previousPage) {
            this.previousPage = previousPage;
        }

        public Boolean getNextPage() {
            return nextPage;
        }

        public void setNextPage(Boolean nextPage) {
            this.nextPage = nextPage;
        }

        public String getSearchQuery() {
            return searchQuery;
        }

        public void setSearchQuery(String searchQuery) {
            this.searchQuery = searchQuery;
        }

        public List<NotificationModel> getDataList() {
            return dataList;
        }

        public void setDataList(List<NotificationModel> dataList) {
            this.dataList = dataList;
        }

    }
}
