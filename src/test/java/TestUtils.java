import com.intelycare.model.DocumentModel;

import java.util.Arrays;

public class TestUtils {

    public static DocumentModel buildDocumentModel(int id, String... args) {
        DocumentModel documentModel = new DocumentModel();
        documentModel.setTokens(Arrays.asList(args));
        documentModel.setDocumentId(id);
        return documentModel;
    }
}
