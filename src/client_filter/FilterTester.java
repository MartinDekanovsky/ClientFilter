package client_filter;

import java.io.IOException;

/**
 * Class for testing of implementation methods of class Filter.
 * Application can be correctly started only via Command Prompt or Terminal by using command, e.g., java FilterTester clients.csv v
 *
 * @author Martin Dekanovský
 */
public class FilterTester {

    public static void main(String[] args) throws IOException {

        String fileName = args[0];
        String filterType = args[1];

        Filter filter = new Filter(fileName, filterType);
    }
}

