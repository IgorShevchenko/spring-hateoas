package com.igor.client;

import java.util.Objects;

public final class ClientHref {

    private String href;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.href);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ClientHref) {
            ClientHref other = (ClientHref) obj;
            return Objects.equals(this.href, other.href);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("ClientHref [href = %s]", this.href);
    }

}
