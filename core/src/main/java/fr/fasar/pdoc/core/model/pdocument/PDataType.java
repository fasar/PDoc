package fr.fasar.pdoc.core.model.pdocument;

/**
 * A PDataType is an immutable object
 */
public class PDataType {

    public final String type;
    public final String description;

    private PDataType(String type, String description) {
        this.type = type;
        this.description = description;
    }


}
