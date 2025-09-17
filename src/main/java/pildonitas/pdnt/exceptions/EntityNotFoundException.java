package pildonitas.pdnt.exceptions;

public class EntityNotFoundException extends RuntimeException {
  public EntityNotFoundException(String entityClass, String attributeName, String attributeValue) {
    super(String.format("%s with %s %s was not found", entityClass, attributeName, attributeValue));
  }
}