package u;

import static org.junit.Assert.assertEquals;

import c.HybridVehicle;
import org.junit.Before;
import org.junit.Test;

public class HybridVehicleTests {

    private static final double DELTA = 0.01;

    private HybridVehicle car;

    @Before
    public void setUp() {
        car = new HybridVehicle();
    }

    @Test
    public void testCalcGasMPG_normalCase() {
        car.setMilesfromGas(240.0);
        car.setGallonsfromGas(12.0);
        assertEquals(20.0, car.calcGasMPG(), DELTA);
    }

    @Test
    public void testCalcGasMPG_zeroGallonsEdgeCase() {
        car.setMilesfromGas(50.0);
        car.setGallonsfromGas(0.0);
        assertEquals(0.0, car.calcGasMPG(), DELTA);
    }

    @Test
    public void testCalcMPGe_labExample() {
        car.setElectricMiles(300.0);
        car.setTotalkWh(70.0);
        assertEquals(144.43, car.calcMPGe(), DELTA);
    }

    @Test
    public void testCalcMPGe_zeroKwhEdgeCase() {
        car.setElectricMiles(50.0);
        car.setTotalkWh(0.0);
        assertEquals(0.0, car.calcMPGe(), DELTA);
    }

    @Test
    public void testCalcAverageHybridMPG() {
        car.setMilesfromGas(240.0);
        car.setGallonsfromGas(12.0);
        car.setElectricMiles(300.0);
        car.setTotalkWh(70.0);
        double expectedAvg = (20.0 + 144.43) / 2.0;
        assertEquals(expectedAvg, car.calcAverageHybridMPG(), DELTA);
    }

    @Test
    public void testCostPerGallonGetSet() {
        car.setCostPerGallon(3.50);
        assertEquals(3.50, car.getCostPerGallon(), DELTA);
    }

    @Test
    public void testCostPerkWhGetSet() {
        car.setCostPerkWh(0.24);
        assertEquals(0.24, car.getCostPerkWh(), DELTA);
    }
}