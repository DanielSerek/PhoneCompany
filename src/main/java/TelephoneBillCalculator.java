import java.math.BigDecimal;
import java.util.List;

public interface TelephoneBillCalculator {
    BigDecimal calculate(String phoneLog);
    String theMostFrequentNumber(List<String> lines);
}
