package org.example.enumeration;

public enum ClientStatus {

    ACTIVE("Active"),
    INACTIVE("Inactive");

    private final String description;

    ClientStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
