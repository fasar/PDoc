package fr.fasar.pdoc.core.business.workflow;

import fr.fasar.pdoc.core.model.pdocument.PDocument;

import java.io.InputStream;

/**
 * Created by fabien_s on 07/07/2014.
 */
public interface WorkflowGeneral {
    public PDocument apply(InputStream inputStream);
}
