import com.intelycare.common.ContainerConfig;
import com.intelycare.model.DocumentModel;
import com.intelycare.service.LuceneIndexServiceImpl;
import com.intelycare.service.LuceneSearchServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LuceneSearchServiceTest {

    private LuceneIndexServiceImpl luceneIndexService;
    private LuceneSearchServiceImpl luceneSearchService;

    @Test
    public void testQueryDocumentConjuction() {
        buildLuceneIndexService();
        buildLuceneSearchService();

        DocumentModel documentModel = TestUtils.buildDocumentModel(1, "index", "1", "apple", "orange", "butter");
        DocumentModel documentModel1 = TestUtils.buildDocumentModel(2, "index", "2", "apple", "orange");
        DocumentModel documentModel2 = TestUtils.buildDocumentModel(3, "index", "3", "apple", "butter");

        assertDoesNotThrow(() -> {
            luceneIndexService.saveIndex(documentModel);
            luceneIndexService.saveIndex(documentModel1);
            luceneIndexService.saveIndex(documentModel2);

            List<Integer> search = luceneSearchService.searchDocuments("query ((apple | orange) & butter)");
            assertEquals(1, search.get(0));
            assertEquals(3, search.get(1));
        });
    }

    @Test
    public void testQueryDocumentFind() {
        buildLuceneIndexService();
        buildLuceneSearchService();

        DocumentModel documentModel = TestUtils.buildDocumentModel(1, "index", "1", "apple", "orange", "butter");
        DocumentModel documentModel1 = TestUtils.buildDocumentModel(2, "index", "2", "apple", "orange");
        DocumentModel documentModel2 = TestUtils.buildDocumentModel(3, "index", "3", "apple", "butter");

        assertDoesNotThrow(() -> {
            luceneIndexService.saveIndex(documentModel);
            luceneIndexService.saveIndex(documentModel1);
            luceneIndexService.saveIndex(documentModel2);

            List<Integer> search = luceneSearchService.searchDocuments("query apple");
            assertEquals(2, search.get(0));
            assertEquals(3, search.get(1));
            assertEquals(1, search.get(2));
        });
    }

    private void buildLuceneSearchService() {
        ContainerConfig containerConfig = ContainerConfig.INSTANCE;
        luceneSearchService = containerConfig.getContainer().getComponent(LuceneSearchServiceImpl.class);
    }

    private void buildLuceneIndexService() {
        ContainerConfig containerConfig = ContainerConfig.INSTANCE;
        luceneIndexService = containerConfig.getContainer().getComponent(LuceneIndexServiceImpl.class);
    }
}
