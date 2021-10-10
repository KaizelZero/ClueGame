package game;

public class BadConfigFormatException extends Exception{ //Custom exception to ensure that config files are properly loaded in
    public BadConfigFormatException() {
    	super("There was an issue loading your configuration files, ensure they are all correct and run again.");
    }
    public BadConfigFormatException(String in) {
    	super("Error: " + in + ". Canceling run");
    }
}
