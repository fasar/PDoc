package fr.fasar.pdoc.core.model.pdocument;

import java.util.Date;
import java.util.Map;

/**
 * Created by fabien_s on 07/07/2014.
 */
public class PDocument {

    private String id;

    private Date creationDate;
    private Date modifiedDate;

    private String fileSourceId;

    private PType type;

    private Map<PDataType, PData> data;


}
