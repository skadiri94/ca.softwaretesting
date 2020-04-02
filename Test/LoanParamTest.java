import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

class LoanParamTest {


    //----------------------------------- P O S I T I V E TEST ------------------------------//
    // Anything between 500 and 10000 Euro and 1-5 years period
    @ParameterizedTest
    @CsvSource({"500.00", "5000", "5001", "10000.00"})
    void validAmountTest(double amount) {
        Loan loan = new Loan(amount, 1);
        assertEquals(amount, loan.getAmount());
    }

    @ParameterizedTest
    @CsvSource({"1", "2", "3", "4", "5"})
    void validPeriodTest(int period) {
        Loan loan = new Loan(500, period);
        assertEquals(period, loan.getPeriod());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "LoanParamTestFile0.csv", numLinesToSkip = 1)
    void validAmountTestFromCSV(double amount) {
        Loan loan = new Loan(amount, 5);
        assertEquals(amount, loan.getAmount());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "LoanParamTestFile1.csv", numLinesToSkip = 1)
    void validPeriodTestFromCSV(int period) {
        Loan loan = new Loan(10000, period);
        assertEquals(period, loan.getPeriod());
    }

    //------------------------------- N E G A T I V E--------------------------//

    // Anything < 500 and > 10000 Euro amount && <= 0 && > 5 year period

    //expected = IllegalArgumentException.class
    @ParameterizedTest
    @CsvSource({"-1", "0", "499.99","10001",
            })
    void inValidAmountTest(double amount) {
        assertThrows(IllegalArgumentException.class, () -> {
            Loan loan = new Loan(amount, 3);
        });
    }
    @ParameterizedTest
    @CsvSource({"-1,-1", "0,0", "499.99,0", "499.99,1", "499.99,6", "10001,0",
            "10001,1", "10001,6", "-999999,9999", "9999999,-9999", "9999999,9999",})
    void illegalArgumentExceptionTest(double amount, int period) {
        assertThrows(IllegalArgumentException.class, () -> {
            Loan loan = new Loan(amount, period);
        });
    }



    @ParameterizedTest
    @CsvFileSource(resources = "LoanParamTestFile2.csv", numLinesToSkip = 1)
    void invalidAmountTestIllegalArgumentExceptionAmount(double amount, int period){
        assertThrows(IllegalArgumentException.class, () -> {
            Loan loan = new Loan(amount, period);
        });
    }



}

