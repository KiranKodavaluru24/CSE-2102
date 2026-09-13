package c;

public class HybridVehicle implements GasolineInterface, ElectricInterface {

    public static final double KWH_PER_GALLON = 33.7;

    private double milesFromGas;
    private double gallonsFromGas;
    private double costPerGallon;

    private double electricMiles;
    private double totalkWh;
    private double costPerkWh;

    @Override
    public double calcGasMPG() {
        if (gallonsFromGas == 0) {
            return 0.0;
        }
        return milesFromGas / gallonsFromGas;
    }

    @Override
    public void setMilesfromGas(double miles) {
        this.milesFromGas = miles;
    }

    @Override
    public void setGallonsfromGas(double gallons) {
        this.gallonsFromGas = gallons;
    }

    @Override
    public void setCostPerGallon(double costPerGallon){
        this.costPerGallon = costPerGallon;
    }

    @Override
    public double getCostPerGallon() {
        return costPerGallon;
    }

    @Override
    public double calcMPGe() {
        if (totalkWh == 0) {
            return 0.0;
        }
        return (electricMiles / totalkWh) * KWH_PER_GALLON;
    }

    @Override
    public void setElectricMiles(double totalElectricMiles) {
        this.electricMiles = totalElectricMiles;
    }

    @Override
    public void setTotalkWh(double totalkWh) {
        this.totalkWh = totalkWh;
    }

    @Override
    public void setCostPerkWh(double costPerkWh) {
        this.costPerkWh = costPerkWh;
    }

    @Override
    public double getCostPerkWh() {
        return costPerkWh;
    }

    public double calcAverageHybridMPG() {
        return (calcGasMPG() + calcMPGe()) / 2.0;
    }
}