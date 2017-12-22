package agh.po.mszpyrka.projekt1;

import org.apache.commons.cli.ParseException;

import java.io.IOException;

public class Main {
    public static void main(String args[]) {

        try {
            ExecSystem.exec(args);
        }
        catch (IOException e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
        catch (InvalidFileContentsException e) {
            System.err.println("Error occurred: " + e.getMessage() + "\ncheck input file contents");
        }
        catch (InvalidSearchExpressionException | ParseException e) {
            System.err.println("Error occurred: " + e.getMessage() + "\ntry -h option to see usage guide");
        }
        catch (Exception e) {
            System.err.println("Unexpected error occurred: " + e.getMessage());
        }
    }
}
