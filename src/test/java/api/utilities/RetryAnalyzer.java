package api.utilities;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    // Specify the maximum number of retries
    private int count = 0;
    private static final int MAX_RETRY_COUNT = 0;

    @Override
    public boolean retry(ITestResult result) {
        if (count < MAX_RETRY_COUNT) {
            count++;
            System.out.println("Retrying " + result.getName() + " for the " + count + " time.");
            return true; // Retry the test
        }
        return false; // Do not retry
    }
}

