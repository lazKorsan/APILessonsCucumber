package testNgFiles;

import utilities.TestNGCucumberBridge;

import java.util.Arrays;
import java.util.List;

// Hemen test etmek için:
public class TestTestNGBridge {


    public static void main(String[] args) {
        List<String> testMethods = Arrays.asList(
                "PaymentTest::testRefund",
                "PaymentTest::testSuccessfulPayment"
        );
        TestNGCucumberBridge.addTestNGMethod(String.valueOf(testMethods));
        //TestNGCucumberBridge.runTestNGMethods(testMethods);
    }
}