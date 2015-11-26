package com.ottamotta.readability.library.manager.network;

public class AddBookmarkResponse {

    public enum Status {

        UNKNOWN(-1, "Error adding bookmark"),
        ACCEPTED(202, "Bookmark added successfully"),
        INVALID_URL(400, "Error adding bookmark: invalid URL"),
        CONFLICT(409, "Bookmark already exists"),
        NOT_FOUND(404, "Error adding bookmark: NOT FOUND");

        private static final Status DEFAULT_STATUS = UNKNOWN;

        private int code;
        private String message;

        Status(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public static Status byCode(int code) {
            for (Status status : values()) {
                if (status.code == code) {
                    return status;
                }
            }
            return DEFAULT_STATUS;
        }

        public String getMessage() {
            return message;
        }
    }

    private Status status;

    private String locationUrl;

    private String xArticleLocationUrl;

    public AddBookmarkResponse(int code, String locationUrl, String xArticleLocationUrl) {
        this.status = Status.byCode(code);
        this.locationUrl = locationUrl;
        this.xArticleLocationUrl = xArticleLocationUrl;
    }

    public Status getStatus() {
        return status;
    }

    public String getLocationUrl() {
        return locationUrl;
    }

    public String getxArticleLocationUrl() {
        return xArticleLocationUrl;
    }

    @Override
    public String toString() {
        return "AddBookmarkResponse{" +
                "status=" + status +
                ", locationUrl='" + locationUrl + '\'' +
                ", xArticleLocationUrl='" + xArticleLocationUrl + '\'' +
                '}';
    }
}
