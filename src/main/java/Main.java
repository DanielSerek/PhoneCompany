import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        TelephoneBillCalculator calculator = new TelephoneBillCalculatorImpl();
        BigDecimal totalBill = BigDecimal.ZERO;

        List<String> lines = new ArrayList<>();

        try{
            Path filePath=Paths.get("src/main/resources/input.csv");
            lines= Files.readAllLines(filePath);
        }catch(Exception e){
            System.out.println("I was not able to read the file.");
        }

        // Fills the mostFrequentNumber private field in the TelephoneBillCalculatorImpl class
        calculator.theMostFrequentNumber(lines);

        System.out.println("The called numbers, start time and end time, call duration and price:");
        for(String line:lines){
            totalBill = totalBill.add(calculator.calculate(line));
        }
        System.out.println("The total bill is: " + totalBill);
    }
}
