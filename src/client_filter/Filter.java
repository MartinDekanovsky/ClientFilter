package client_filter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Class for filtering clients based on specified filter.
 * The list of clients is available in file clients.csv
 *
 * @author Martin Dekanovský
 */
public class Filter {

    private List<String> lineContent;
    private boolean contains;

    /**
     * Constructor for creating new filter based on parameters entered by user before application start.
     *
     * @param fileName name of the file which contains the list of clients.
     * @param filterType type of filter we want to use to filter the clients.
     *                   Following filters are available:
     *                   "v" filters clients who use "voice"
     *                   "int" filters clients who use "internet"
     *                   "b" filters clients who use "both" voice and internet
     *                   "c" filters clients who use "credit"
     *                   "inv" filters clients who use "invoice"
     *                   "m" filters clients who's "mobile" phone number is available
     *                   "l" filters clients who's "landline" phone number is available.
     * @throws IOException
     */
    public Filter(String fileName, String filterType) throws IOException {

        List<String> availableFilters = new ArrayList<>(Arrays.asList("v", "int", "b", "c", "inv", "m", "l"));
        List<String> lines = Files.readAllLines(Paths.get(fileName));

        if (availableFilters.contains(filterType)) {

            System.out.printf("%-20s%-15s%-5s\n", "Name:", "Contact:", "Balance:");

            for (String line : lines) {
                lineContent = List.of(line.split(","));
                if (Objects.equals(filterType, "v")) {
                    filterVoice();
                } else if (Objects.equals(filterType, "int")) {
                    filterInternet();
                } else if (Objects.equals(filterType, "b")) {
                    filterBoth();
                } else if (Objects.equals(filterType, "c")) {
                    filterCredit();
                } else if (Objects.equals(filterType, "inv")) {
                    filterInvoice();
                } else if (Objects.equals(filterType, "m")) {
                    filterMobileContact();
                } else if (Objects.equals(filterType, "l")) {
                    filterLandlineContact();
                }
                if (contains) {
                    System.out.printf("%-20s%-15s%-5s\n", lineContent.get(0), lineContent.get(1),
                            lineContent.get(lineContent.size() - 1));
                }
            }
        } else {
            System.out.println("Such filter does not exist!");
        }
    }

    /**
     * Method filters clients who use voice.
     */
    public void filterVoice() {
        contains = lineContent.get(2).equals("voice");
    }

    /**
     * Method filters clients who use internet.
     */
    public void filterInternet() {
        contains = lineContent.get(2).equals("internet") || lineContent.get(3).equals("internet");
    }

    /**
     * Method filters clients who use both voice and internet.
     */
    public void filterBoth() {
        contains = lineContent.get(2).equals("voice") &&
                (lineContent.get(2).equals("internet") || lineContent.get(3).equals("internet"));
    }

    /**
     * Method filters clients who use credit.
     */
    public void filterCredit() {
        contains = lineContent.get(3).equals("credit") || lineContent.get(4).equals("credit");
    }

    /**
     * Method filters clients who use invoice (they have negative balance).
     */
    public void filterInvoice() {
        contains = lineContent.get(lineContent.size() - 1).contains("-");
    }

    /**
     * Method filters clients whose mobile phone number is available (starts with 09).
     */
    public void filterMobileContact() {
        contains = lineContent.get(1).startsWith("09");
    }

    /**
     * Method filters clients whose landline phone number is available (does not start with 09).
     */
    public void filterLandlineContact() {
        contains = !lineContent.get(1).startsWith("09");
    }
}
