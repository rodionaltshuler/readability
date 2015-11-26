package com.ottamotta.readability.library.manager.network;

public class AddBookmarkResponse {

    enum Status {

        UNKNOWN(-1),
        ACCEPTED(202),
        INVALID_URL(400),
        CONFLICT(409),
        NOT_FOUND(404);

        private static final Status DEFAULT_STATUS = UNKNOWN;

        private int code;

        Status(int code) {
            this.code = code;
        }

        public static Status byCode(int code) {
            for (Status status : values()) {
                if (status.code == code) {
                    return status;
                }
            }
            return DEFAULT_STATUS;
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
