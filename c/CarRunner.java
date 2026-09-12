package c;
public class CarRunner {
    public static void main(String[] args) {
        HybridVehicle car = new HybridVehicle();

        //prices
        car.setCostPerGallon(3.50);
        car.setCostPerkWh(0.24);

        car.setMilesfromGas(240.0);
        car.setGallonsfromGas(12.0);

        car.setElectricMiles(300.0);
        car.setTotalkWh(70.0);

        double mpg = car.calcGasMPG();
        double mpge = car.calcMPGe();
        double avgHybridMpg = car.calcAverageHybridMPG();

        System.out.println("==== HybridVehicle CarRunner ====");
        System.out.printf("Fully-gas MPG:  %.2f MPG%n", mpg);
        System.out.printf("Fully-Electric MPGe:  %.2f MPGe%n", mpge );
        System.out.printf("Half-gas/half-electric avg:  %.2f (MPG+MPGe)/2%n", avgHybridMpg);

    }
}