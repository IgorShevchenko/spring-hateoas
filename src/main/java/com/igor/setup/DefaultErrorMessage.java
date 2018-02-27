package com.igor.setup;

import javax.servlet.http.HttpServletRequest;

class DefaultErrorMessage {

    private final String href;
    private final String message;

    public DefaultErrorMessage(HttpServletRequest request, Throwable t) {
        this.href = getCompleteUrl(request);
        this.message = t.getLocalizedMessage();
    }

    public String getHref() {
        return href;
    }

    public String getMessage() {
        return message;
    }

    private String getCompleteUrl(HttpServletRequest request) {
        StringBuffer requestUrl = request.getRequestURL();
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestUrl.toString();
        } else {
            return requestUrl.append('?').append(queryString).toString();
        }
    }

}
