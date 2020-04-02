import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoanTest {

    private Loan loan = new Loan(5000,3);

    //Positive Tests
    @Test
    void defaultConstructorTest(){
        assertNotNull(new Loan());
    }



    @Test
    void argConstructorTest(){

        assertNotNull(new Loan(5000,3));
    }

    @Test
    void amountTest() {
        assertEquals(5000, loan.getAmount());
    }
    @Test
    void periodTest() {
        assertEquals(3, loan.getPeriod());
    }
    @Test
    void rateTest() {

        Loan loan0 = new Loan(6500,2);
        assertEquals(8, loan0.getRate());
    }
    @Test
    void monthlyPaymentTest() {

        assertEquals(String.format("%.2f",161.34),String.format("%.2f",loan.getMonthlyPayment()));
    }
    @Test
    void totalPaymentTest() {
        assertEquals(String.format("%.2f",5808.09),String.format("%.2f",loan.getTotalPayment()));
    }


    //Negative Tests
    @Test()
    void negativeTest(){
        assertNotEquals(6000, loan.getAmount());
        assertNotEquals(16, loan.getPeriod());
        assertNotEquals(9.0, loan.getRate());
    }

    @Test
    void baseExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> {
             new Loan(500, 7);
             new Loan(0, 0);
             new Loan(-1, -1);
             new Loan((Double.MIN_VALUE), 1);
             new Loan(500, (Integer.MIN_VALUE));
             new Loan(499.99, 1);
             new Loan(10000.01, 1);
             new Loan((Double.MAX_VALUE), 1);
             new Loan(5000, (Integer.MAX_VALUE));
        });
    }

    //------------------------------------------MORE TEST FOR IMPROVING THE CODE --------------------------------//
    //The code can be improved in the setRate() method to throw an exception when a period out of range 1-5 is entered
    //Hence Adding the below test for after improvement of the code.

    //Loan Can be Made that are out of Period Range  the Below Test confirms it.
    @Test
    void expectedExceptonConstructorTest(){
        assertNotNull(new Loan(500,8));
    }

    @Test
    void outOfPeriodRangeTest() {
            Loan ln = new Loan(4500,8);

        assertEquals(4500, ln.getAmount());
    }

//The Below Test was suppose to generate and Exception of a loan out of the period range is made.
/*
    @Test
    void expectedIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Loan(500, 8);

        });  }*/


}


