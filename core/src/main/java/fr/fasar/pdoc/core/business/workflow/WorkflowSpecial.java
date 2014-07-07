package fr.fasar.pdoc.core.business.workflow;

import fr.fasar.pdoc.core.model.pdocument.PDataType;
import fr.fasar.pdoc.core.model.pdocument.PData;
import fr.fasar.pdoc.core.model.pdocument.PDocument;

import java.util.List;
import java.util.Map;

/**
 * Created by fabien_s on 07/07/2014.
 */
public interface WorkflowSpecial {

    public List<PDataType> produce();

    public Map<PDataType, PData> apply(PDocument document);

}
