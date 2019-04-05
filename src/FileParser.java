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

    void parseHeader(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            int dimensions = Integer.parseInt(br.readLine());

            switch(csp_type){
                case FUTOSHIKI:
                    futoshiki = new Futoshiki(dimensions);
                    break;
                case SKYSCRAPER:
                    skyscraper = new Skyscraper(dimensions);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void parseFutoshikiFile(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line = br.readLine();

            // create temporary variable of futoshiki board
            int[][] board = new int[futoshiki.getDimensions()][futoshiki.getDimensions()];

            // skip all lines from beginning to node coordinate section
            while (!line.startsWith("ITEMS")){
                line = br.readLine();
            }
            // here caret should be on the first row of board data

            // read board from file
            for (int i = 0; i < futoshiki.getDimensions(); i++){
                line = br.readLine();

                // split row to table (one value per cell, split by tab)
                String[] cardRowString = line.split(";");
                int[] cardRow = convertStringArrayToIntegerArray(cardRowString);

                // add cardRow to board
                board[i] = cardRow;

                futoshiki.setBoard(board);
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private int[] convertStringArrayToIntegerArray(String[] cardRowString) {
        int[] result = new int[cardRowString.length];

        for (int i = 0; i < cardRowString.length; i++){
            result[i] = Integer.parseInt(cardRowString[i]);
        }

        return result;
    }
}
