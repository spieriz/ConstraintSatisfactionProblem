import java.io.*;

public class FileParser {
    private File file;
    private CSP_TYPE csp_type;

    Futoshiki futoshiki;
    Skyscraper skyscraper;

    public enum CSP_TYPE {
        FUTOSHIKI,
        SKYSCRAPER
    }

    FileParser(File file, CSP_TYPE csp_type){
        this.file = file;
        this.csp_type = csp_type;

        futoshiki = null;
        skyscraper = null;
    }
}
