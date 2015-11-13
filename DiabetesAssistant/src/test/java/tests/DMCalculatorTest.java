package tests;

import com.lauttadev.diabetesassistant.DMCalculator;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class DMCalculatorTest {
    private DMCalculator calc = new DMCalculator();
    
    @Test
    public void testAverageBloodsugar() {
        ArrayList<Integer> bloodsugars = new ArrayList<Integer>();
        bloodsugars.add(50);
        bloodsugars.add(1);
        bloodsugars.add(25);
        
        assertEquals(25.3333f, calc.averageBloodsugar(bloodsugars), 0.0001);
    }
}
