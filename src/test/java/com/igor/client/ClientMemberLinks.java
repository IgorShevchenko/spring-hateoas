package com.igor.client;

import java.util.Objects;

public class ClientMemberLinks {

    private ClientHref self;
    private ClientHref invitedBy;

    public ClientHref getSelf() {
        return self;
    }

    public void setSelf(ClientHref self) {
        this.self = self;
    }

    public ClientHref getInvitedBy() {
        return invitedBy;
    }

    public void setInvitedBy(ClientHref invitedBy) {
        this.invitedBy = invitedBy;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.self);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ClientMemberLinks) {
            ClientMemberLinks other = (ClientMemberLinks) obj;
            return Objects.equals(this.self, other.self);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("ClientMemberLinks [self = %s]", this.self);
    }

}
