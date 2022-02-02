import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TelephoneBillCalculatorImplTest {

    TelephoneBillCalculator calculator;

    @Before
    public void setUp() throws Exception {
        calculator = new TelephoneBillCalculatorImpl();
    }

    @Test
    public void calculate() {
        assertEquals(BigDecimal.ZERO, calculator.calculate("420602387831,15-01-2020 18:10:17,13-01-2020 18:12:59"));
        assertEquals(new BigDecimal(5.60).setScale(2, RoundingMode.HALF_UP), calculator.calculate("420776785210,13-01-2020 14:10:17,13-01-2020 14:17:59").setScale(2));
        assertEquals(new BigDecimal(3.00).setScale(2, RoundingMode.HALF_UP), calculator.calculate("420776785210,13-01-2020 14:10:17,13-01-2020 14:12:59").setScale(2));
        assertEquals(new BigDecimal(1.50).setScale(2, RoundingMode.HALF_UP), calculator.calculate("420776785210,13-01-2020 18:12:17,13-01-2020 18:14:59").setScale(2));
        assertEquals(new BigDecimal(5.80).setScale(2, RoundingMode.HALF_UP), calculator.calculate("420776785210,13-01-2020 15:12:17,13-01-2020 15:20:59").setScale(2));
    }

    @Test
    public void theMostFrequentNumber() {
        List<String> input = new ArrayList<>();
        input.add("420602387831,13-01-2020 18:10:17,13-01-2020 18:12:59");
        input.add("420776785210,13-01-2020 13:10:17,13-01-2020 13:33:59");
        input.add("420776785210,13-01-2020 14:10:17,13-01-2020 14:17:59");
        assertEquals("420776785210", calculator.theMostFrequentNumber(input));
    }
}