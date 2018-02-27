package com.igor.client;

import java.util.Objects;

public class ClientMember {

    private Long id;
    private String firstName;
    private String lastName;
    private String zipcode;
    private String dateOfBirth;
    private ClientMemberLinks _links;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public ClientMemberLinks get_links() {
        return _links;
    }

    public void set_links(ClientMemberLinks _links) {
        this._links = _links;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ClientMember) {
            ClientMember other = (ClientMember) obj;
            return Objects.equals(this.getId(), other.getId());
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("ClientMember [id = %d, lastName = '%s']", this.id, this.lastName);
    }

}
