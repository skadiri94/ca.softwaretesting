import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class LoanTestPrivate {

    @Test
    public void setAmountTest() throws Exception {

        ArrayList<Loan> list = new ArrayList<>();

        // Add 4 valid Loan objects to list to manipulate
        for(int i=0;i<=5;i++){
            list.add(new Loan(500, 3));
        }

        Method method = Loan.class.getDeclaredMethod("setAmount", double.class);
        method.setAccessible(true);


        method.invoke(list.get(0),500);
        method.invoke(list.get(1),2500);
        method.invoke(list.get(2),5000);
        method.invoke(list.get(3),5001);
        method.invoke(list.get(4),7500);
        method.invoke(list.get(5),10000);

        for (Loan loan : list) {

            Field field = loan.getClass().getDeclaredField("loanAmount");
            field.setAccessible(true);

            double result = field.getDouble(loan);
            assertEquals(loan.getAmount(),result);
        }
        method.invoke(list.get(0),1);
        method.invoke(list.get(1),499.99);
        method.invoke(list.get(2),10000.01);
        method.invoke(list.get(3),200);
        method.invoke(list.get(4),29999);
        method.invoke(list.get(5),(Integer.MAX_VALUE));

        for (Loan loan : list) {

            Field field = loan.getClass().getDeclaredField("loanAmount");
            field.setAccessible(true);

            double result = field.getDouble(loan);

            assertThrows(IllegalArgumentException.class, () -> {

                new Loan(result, 3);
                // Can also add these to another arrayList to store
                // this just creates objects with no reference
            });
        }


        // This exception occurs when 0 or a negative number are set
      //  as loanAmount - The rate is calculated by the amount, we
     //   cannot divide by 0 or a negative number - Hence this
      //  exception is thrown because of this underlying exception itself
        assertThrows(InvocationTargetException.class, () -> {

        method.invoke(list.get(0),-1);
        method.invoke(list.get(1),0);
        method.invoke(list.get(3),(Integer.MIN_VALUE));

        for (Loan loan : list) {
            Class<? extends Loan> secretClass = loan.getClass();
            Field field = secretClass.getDeclaredField("loanAmount");
            field.setAccessible(true);
            double result = field.getDouble(loan);
            new Loan(result, 3);
        }
    });

    }


    @Test
    public void setPeriodTest() throws Exception {

        ArrayList<Loan> list = new ArrayList<>();
        //Add 4 valid Loan objects to list to manipulate
        for(int i = 0; i <= 4; i++){
            list.add(new Loan(500, 3));
        }

        Method method = Loan.class.getDeclaredMethod("setPeriod", int.class);
        method.setAccessible(true);

        //Invoke periods of 1-5 years on all 5 loan objects
        for(int j = 1; j <= 5; j++){
            method.invoke(list.get(0), j);
        }

        //for each loan in list
        for (Loan loan : list) {

            Field period = loan.getClass().getDeclaredField("numberOfPayments");
            period.setAccessible(true);
            Field months = loan.getClass().getDeclaredField("MONTHS_IN_YEAR");
            months.setAccessible(true);

            int result =  (period.getInt(loan) / months.getInt(loan));
            assertEquals(loan.getPeriod(),result);
        }


    }


    @Test
    public void setRateTest() throws Exception {


        ArrayList<Loan> lessThan5000 = new ArrayList<>();
        //Add 4 valid Loan objects to list to manipulate

        lessThan5000.add(new Loan(500, 1));
        lessThan5000.add(new Loan(5000, 2));
        lessThan5000.add(new Loan(501, 3));
        lessThan5000.add(new Loan(4999, 4));
        lessThan5000.add(new Loan(2500, 5));

        Method method = Loan.class.getDeclaredMethod("setRate", int.class);
        method.setAccessible(true);

        //swap them around just to see if setRate works
        method.invoke(lessThan5000.get(0),5);
        method.invoke(lessThan5000.get(1),4);
        method.invoke(lessThan5000.get(2),3);
        method.invoke(lessThan5000.get(3),2);
        method.invoke(lessThan5000.get(4),1);

        for (int i = 0; i <= 1; i ++ ) {

            Field field = lessThan5000.get(i).getClass().getDeclaredField("annualRate");
            field.setAccessible(true);

            //periods 4 and 5 get a rate of 6%
            double result = field.getDouble(lessThan5000.get(i));
            assertEquals(6, result);
        }

        for (int i = 2; i <= 4; i ++ ) {

            Field field = lessThan5000.get(i).getClass().getDeclaredField("annualRate");
            field.setAccessible(true);

            //periods 2, 3 and 4 get a rate of 10%
            double result = field.getDouble(lessThan5000.get(i));
            assertEquals(10, result);
        }

        //------------------------------------------------------------------------------

        ArrayList<Loan> moreThan5000 = new ArrayList<>();
        //Add 4 valid Loan objects to list to manipulate

        moreThan5000.add(new Loan(5001, 1));
        moreThan5000.add(new Loan(10000, 2));
        moreThan5000.add(new Loan(5799, 3));
        moreThan5000.add(new Loan(9999, 4));
        moreThan5000.add(new Loan(8231, 5));

        Method method2 = Loan.class.getDeclaredMethod("setRate", int.class);
        method2.setAccessible(true);

        //swap them around just to see if setRate works
        method2.invoke(moreThan5000.get(0),5);
        method2.invoke(moreThan5000.get(1),4);
        method2.invoke(moreThan5000.get(2),3);
        method2.invoke(moreThan5000.get(3),2);
        method2.invoke(moreThan5000.get(4),1);


        for (int i = 0; i <= 1; i ++ ) {

            Field field = moreThan5000.get(i).getClass().getDeclaredField("annualRate");
            field.setAccessible(true);

            //periods 4 and 5 get a rate of 5%
            double result = field.getDouble(moreThan5000.get(i));
            assertEquals(5, result);
        }

        for (int i = 2; i <= 4; i ++ ) {

            Field field = moreThan5000.get(i).getClass().getDeclaredField("annualRate");
            field.setAccessible(true);

            //periods 2, 3 and 4 get a rate of 8%
            double result = field.getDouble(moreThan5000.get(i));
            assertEquals(8, result);
        }
    }
}
