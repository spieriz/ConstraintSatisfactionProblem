import java.io.*;

class FileParser {
    private File file;
    private CSP_TYPE csp_type;

    private Futoshiki futoshiki;
    private Skyscraper skyscraper;

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
            while (!line.startsWith("START")){
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

            // skip all lines from current line to relations section
            while (!line.startsWith("REL")){
                line = br.readLine();
            }

            // read relations
            while ((line = br.readLine()) != null) {
                String[] relations = line.split(";");

                FutoshikiRestriction restriction = new FutoshikiRestriction();

                restriction.setRowSmaller(getNumberFromChar(relations[0].charAt(0)) - 1);
                restriction.setColumnSmaller(Character.getNumericValue(relations[0].charAt(1)) - 1);

                restriction.setRowBigger(getNumberFromChar(relations[1].charAt(0)) - 1);
                restriction.setColumnBigger(Character.getNumericValue(relations[1].charAt(1)) - 1);

                futoshiki.addRestriction(restriction);
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private int getNumberFromChar(char charAt) {
        return Character.getNumericValue(Character.toUpperCase(charAt) - 16);
    }

    void parseSkyscraperFile(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            // skip first line which contains dimensions number
            br.readLine();

            // for each direction get restrictions
            for (int i = 0; i < 4; i++){
                String line = br.readLine();

                String[] array = line.split(";");

                String direction = array[0];
                int[] restrictions = new int[skyscraper.getDimensions()];

                for (int j = 0; j < skyscraper.getDimensions(); j++){
                    restrictions[j] = Integer.parseInt(array[j + 1]);
                }

                parseSkyscraperRestrictions(direction, restrictions);
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void parseSkyscraperRestrictions(String direction, int[] floors) {
        switch (direction){
            case "G":
                skyscraper.setRestrictionsTop(floors);
                break;
            case "D":
                skyscraper.setRestrictionsBottom(floors);
                break;
            case "L":
                skyscraper.setRestrictionsLeft(floors);
                break;
            case "R":
                skyscraper.setRestrictionsRight(floors);
                break;
        }
    }

    private int[] convertStringArrayToIntegerArray(String[] cardRowString) {
        int[] result = new int[cardRowString.length];

        for (int i = 0; i < cardRowString.length; i++){
            result[i] = Integer.parseInt(cardRowString[i]);
        }

        return result;
    }

    public Futoshiki getFutoshiki(){
        return futoshiki;
    }
}
