package freemarker;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;

/**
 * Singleton configuration class
 * for FreeMarker.
 *
 * @author Ioan Sava
 * @see <a href="https://freemarker.apache.org/docs/pgui_quickstart_createconfiguration.html">Freemarker Configuration</a>
 */
public class FreeMarkerConfiguration {
    private static FreeMarkerConfiguration instance;
    private final Configuration configuration;

    private FreeMarkerConfiguration() throws IOException {
        configuration = new Configuration(Configuration.VERSION_2_3_29);
        String TEMPLATES_FILE = "templates";
        configuration.setDirectoryForTemplateLoading(new File(TEMPLATES_FILE));
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(false);
        configuration.setWrapUncheckedExceptions(true);
        configuration.setFallbackOnNullLoopVariable(false);
    }

    public static FreeMarkerConfiguration getInstance() {
        try {
            if (instance == null) {
                instance = new FreeMarkerConfiguration();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return instance;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
