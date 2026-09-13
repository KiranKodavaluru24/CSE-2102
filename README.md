# Lab 1: Java Inheritance and JUnit 4 — Hybrid Vehicle MPG/MPGe

This project models a `HybridVehicle` that implements a `GasolineInterface`
and an `ElectricInterface`, computes gas MPG, electric MPGe, and an
averaged hybrid-mode MPG, and is verified with JUnit 4 unit tests.

## Project layout
## Requirements
Internet access to fetch the JUnit 4 jars

## 1. Get the JUnit 4 / Hamcrest jars

From the repo root:

```bash
curl -O https://repo1.maven.org/maven2/junit/junit/4.13.2/junit-4.13.2.jar
curl -O https://repo1.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar
```

## 2. Build (compile)

```bash
javac -cp junit-4.13.2.jar c/GasolineInterface.java c/ElectricInterface.java c/HybridVehicle.java c/CarRunner.java u/HybridVehicleTests.java
```

## 3. Run the program

```bash
java -cp "./" c.CarRunner
```

Expected output:
=== HybridVehicle CarRunner ===

Fully-gas mode MPG: 20.00 MPG
Fully-electric mode MPGe: 144.43 MPGe
Half-gas/half-electric avg: 82.21 (MPG+MPGe)/2


## 4. Run the unit tests

```bash
java -cp "junit-4.13.2.jar:hamcrest-core-1.3.jar:./" org.junit.runner.JUnitCore u.HybridVehicleTests
```

Expected output:
JUnit version 4.13.2
.......
Time: 0.015

OK (7 tests)

See `LAB_REPORT.md` for the AI system used