package be.libis.rdm.export;
import io.gdcc.spi.export.ExportDataProvider;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import jakarta.json.JsonReader;
import be.libis.rdm.export.ROCrate.*;

public class ROCrateExporterTest {

    static ROCrateExporter roCrateExporter;
    static OutputStream outputStream;
    static ExportDataProvider dataProvider;

    @BeforeAll
    public static void setUp() {
        roCrateExporter = new ROCrateExporter();
        roCrateExporter.setCsvPath("./dataverse2ro-crate.csv");
        outputStream = new ByteArrayOutputStream();
        dataProvider = new ExportDataProvider() {
            @Override
            public JsonObject getDatasetJson() {
                InputStream is = null;
                try {
                    is = new FileInputStream("./src/test/resources/testDataset/datasetJson.json");
                } catch (Exception e) {
                    System.err.println("Test dataset file not found.");

                }

                JsonReader jsonReader = Json.createReader(is);
                return (jsonReader.readObject());
            }

            @Override
            public JsonObject getDatasetORE() {
                return Json.createObjectBuilder().build();
            }

            @Override
            public JsonArray getDatasetFileDetails() {
                return Json.createArrayBuilder().build();
            }

            @Override
            public JsonObject getDatasetSchemaDotOrg() {
                return Json.createObjectBuilder().build();
            }

            @Override
            public String getDataCiteXml() {
                return null;
            }
        };
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetFormatName() {
        assertEquals("rocrate_json", roCrateExporter.getFormatName());
    }

    @Test
    public void testGetDisplayName() {
        assertEquals("RO-Crate", roCrateExporter.getDisplayName(new Locale("en", "US")));
    }

    @Test
    public void testIsHarvestable() {
        assertEquals(false, roCrateExporter.isHarvestable());
    }

    @Test
    public void testIsAvailableToUsers() {
        assertEquals(true, roCrateExporter.isAvailableToUsers());
    }

    @Test
    public void testGetMediaType() {
        assertEquals("application/json", roCrateExporter.getMediaType());
    }


}
