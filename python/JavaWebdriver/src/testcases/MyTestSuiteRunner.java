package testcases;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)

@SuiteClasses({
FirstTestCaseTest.class, 
SecondTestCase.class
})
public class MyTestSuiteRunner {
	
	
}

