package org.example.exceptions;

public final class EntityNotFoundException extends RuntimeException {


    private static final long serialVersionUID = 1343430395L;

    private String entityName;

    private Object entityId;

    public EntityNotFoundException(String entityName, Object id) {
        super(entityName + " with id " + id + " not found");
        this.entityName = entityName;
        this.entityId = id;
    }

    public EntityNotFoundException(String message) {
        super(message);
    }


    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
