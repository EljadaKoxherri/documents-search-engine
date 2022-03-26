package integration;

import com.intelycare.cli.Processor;
import com.intelycare.common.ContainerConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class SearchEngineIntegrationTest {

    private Processor processor;

    @Test
    public void testIndexDocumentsOK() throws Exception {
        buildProcessor();

        String response = processor.process("index 1 abc");
        Assertions.assertEquals("index ok 1" , response);
    }

    @Test
    public void testIndexDocumentsError() throws Exception {
        buildProcessor();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {String response = processor.process("index 1 $abc");});
    }

    @Test
    public void testIndexDocumentsUpdatedOK() throws Exception {
        buildProcessor();
        String response = processor.process("index 2 fgh");
        Assertions.assertEquals("index ok 2" , response);

        response = processor.process("index 3 def");
        Assertions.assertEquals("index ok 3" , response);

        String queryResponse = processor.process("query abcd");
        Assertions.assertEquals("query error no results found" , queryResponse.trim());

        queryResponse = processor.process("query def");
        Assertions.assertEquals("query results 3" , queryResponse);
    }

    @Test
    public void testQueryDocumentsOK() throws Exception {
        buildProcessor();
        processor.process("index 4 test");
        String queryResponse = processor.process("query test");
        Assertions.assertEquals("query results 4" , queryResponse.trim());
    }

    @Test
    public void testQueryDocumentsMoreThenOneResultOK() throws Exception {
        buildProcessor();
        processor.process("index 5 test5");
        processor.process("index 6 test5");
        String queryResponse = processor.process("query test5");
        Assertions.assertEquals("query results 5 6" , queryResponse.trim());
    }

    @Test
    public void testQueryDocumentsNoResultOK() throws Exception {
        buildProcessor();
        processor.process("index 7 test7");
        String queryResponse = processor.process("query zxc");
        Assertions.assertEquals("query error no results found" , queryResponse.trim());
    }

    @Test
    public void testComplexQueryDocumentstOK() throws Exception {
        buildProcessor();
        processor.process("index 8 soup tomato cream salt");
        processor.process("index 9 cake sugar eggs flour sugar cocoa cream butter");
        processor.process("index 8 bread butter salt");
        processor.process("index 10 soup fish potato salt pepper");
        String queryResponse = processor.process("query (butter | potato) & salt");
        Assertions.assertEquals("query results 8 10" , queryResponse.trim());
    }

    public void buildProcessor() {
        ContainerConfig containerConfig = ContainerConfig.INSTANCE;
        processor = containerConfig.getContainer().getComponent(Processor.class);
    }
}
