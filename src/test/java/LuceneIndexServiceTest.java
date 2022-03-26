
import com.intelycare.common.ContainerConfig;
import com.intelycare.model.DocumentModel;
import com.intelycare.service.LuceneIndexServiceImpl;
import com.intelycare.service.LuceneSearchServiceImpl;
import com.intelycare.service.SearchService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LuceneIndexServiceTest {

    private LuceneIndexServiceImpl luceneIndexService;
    private LuceneSearchServiceImpl luceneSearchService;

    @Test
    public void testIndexDocument() {
        buildLuceneIndexService();
        buildLuceneSearchService();

        DocumentModel documentModel = new DocumentModel();
        documentModel.setDocumentId(1);
        documentModel.setTokens(Arrays.asList("index", "11", "test11"));

        assertDoesNotThrow(() -> {
            luceneIndexService.saveIndex(documentModel);
        }, (String) null);
    }

    @Test
    public void testIndexDocumentFailed() {
        buildLuceneIndexService();
        buildLuceneSearchService();

        DocumentModel documentModel = new DocumentModel();
        documentModel.setTokens(Arrays.asList("index", "12", "test_abc"));
        assertThrows(NullPointerException.class,
                () -> {
                    luceneIndexService.saveIndex(documentModel);
                });
    }

    @Test
    public void testIndexDocumentUpdate() {
        buildLuceneIndexService();
        buildLuceneSearchService();

        DocumentModel documentModel = TestUtils.buildDocumentModel(13, "index", "13", "test_13");
        DocumentModel documentModel1 = TestUtils.buildDocumentModel(14, "index", "14", "test_def");

        assertDoesNotThrow(() -> {
            luceneIndexService.saveIndex(documentModel);
            luceneIndexService.saveIndex(documentModel1);

            List<Integer> search = luceneSearchService.searchDocuments("query test_def");
            assertEquals(14, search.get(0));
        }, (String) null);
    }

    @Test
    public void testQueryDocumentConjuction() {
        buildLuceneIndexService();
        buildLuceneSearchService();

        DocumentModel documentModel = TestUtils.buildDocumentModel(15, "index", "15", "apple_15", "orange_15", "butter_15");
        DocumentModel documentModel1 = TestUtils.buildDocumentModel(16, "index", "16", "apple_16", "orange_16");
        DocumentModel documentModel2 = TestUtils.buildDocumentModel(17, "index", "17", "apple_16", "butter_15");

        assertDoesNotThrow(() -> {
            luceneIndexService.saveIndex(documentModel);
            luceneIndexService.saveIndex(documentModel1);
            luceneIndexService.saveIndex(documentModel2);

            List<Integer> search = luceneSearchService.searchDocuments("query ((apple_16 | orange_15) & butter_15)");
            assertEquals(15, search.get(0));
            assertEquals(17, search.get(1));
        });
    }

    @Test
    public void testQueryDocumentFind() {
        buildLuceneIndexService();
        buildLuceneSearchService();

        DocumentModel documentModel = TestUtils.buildDocumentModel(20, "index", "20", "apple_20", "orange_20", "butter_20");
        DocumentModel documentModel1 = TestUtils.buildDocumentModel(21, "index", "20", "apple_20", "orange_20");
        DocumentModel documentModel2 = TestUtils.buildDocumentModel(22, "index", "22", "apple_20", "butter_20");

        assertDoesNotThrow(() -> {
            luceneIndexService.saveIndex(documentModel);
            luceneIndexService.saveIndex(documentModel1);
            luceneIndexService.saveIndex(documentModel2);

            List<Integer> search = luceneSearchService.searchDocuments("query apple_20");
            assertEquals(21, search.get(0));
            assertEquals(22, search.get(1));
            assertEquals(20, search.get(2));
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
