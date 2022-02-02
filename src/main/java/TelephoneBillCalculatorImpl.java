import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class TelephoneBillCalculatorImpl implements TelephoneBillCalculator {

    private String theMostFrequentNumber;

    @Override
    public BigDecimal calculate(String phoneLog) {
        String[] input = phoneLog.split(",");
        BigDecimal bill = BigDecimal.ZERO;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(input[1], formatter);
        LocalDateTime end = LocalDateTime.parse(input[2], formatter);

        long timeElapseLong = ChronoUnit.MINUTES.between(start,end);

        // The call can't have a negative value
        if(timeElapseLong < 0) return BigDecimal.ZERO;

        // The minimum call duration is 1 minute
        timeElapseLong +=1;

        BigDecimal timeElapse = new BigDecimal(timeElapseLong);

        // The billing algorithm
        if(start.getHour() >= 8 && end.getHour() < 16){
            if(timeElapseLong > 5){
                bill = bill.add(new BigDecimal(5));
                bill = bill.add((timeElapse.subtract(new BigDecimal (5))).divide(new BigDecimal(5)));
            }
            else{
                bill = bill.add(timeElapse);
            }
        }
        else{
            if(timeElapseLong > 5){
                bill = bill.add(new BigDecimal(5).divide(new BigDecimal(2)));
                bill = bill.add((timeElapse.subtract(new BigDecimal (5))).divide(new BigDecimal(5)));
            }
            else{
                bill = bill.add(timeElapse.divide(new BigDecimal(2)));
            }
        }
        if(input[0].replaceAll("[\uFEFF-\uFFFF]","").equals(theMostFrequentNumber)) bill = BigDecimal.ZERO;
        System.out.println(input[0] + "; " + start.format(formatter) + " - " + end.format(formatter) + "; " + timeElapse + " min.; " + bill.setScale(2, RoundingMode.CEILING) + " Kč");

        return bill;
    }

    @Override
    public String theMostFrequentNumber(List<String> lines) {
        HashMap<String,Integer> counter = new HashMap<String, Integer>();
        String phoneNumber;
        String[] input;

        for (String line : lines) {
            input = line.split(",");
            phoneNumber = input[0].replaceAll("[\uFEFF-\uFFFF]","");
            if(counter.containsKey(phoneNumber)){
                counter.put(phoneNumber, counter.get(phoneNumber) + 1);
            }
            else{
                counter.put(phoneNumber, 1);
            }
        }

        int max = Collections.max(counter.entrySet(), Map.Entry.comparingByValue()).getValue();

        // Get a list of numbers with the highest number of occurrences
        List<String> numbers = counter.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == max)
                .map(entry -> entry.getKey()).collect(Collectors.toList());

        // Convert List<String> to List<Integer>
        List<Long> numbersInt = new ArrayList<>();
        for (String number : numbers) {
            numbersInt.add(Long.parseLong(number));
        }

        // Identify the highest number
        long highestNumber = numbersInt
                .stream()
                .mapToLong(v -> v)
                .max().orElseThrow(NoSuchElementException::new);

        theMostFrequentNumber = Long.toString(highestNumber);
        return theMostFrequentNumber;
    }
}
