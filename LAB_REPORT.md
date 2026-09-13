# Lab 1 Report: Java Inheritance and JUnit 4

## Lab Objective

For this lab, my goal was to understand Java inheritance through
interfaces and to use JUnit 4 for basic unit testing, while getting
hands-on with GitHub Codespaces and the command-line Java workflow
(`javac`, `java`, and `org.junit.runner.JUnitCore`). Concretely, I
modeled gasoline versus electric vehicle efficiency — MPG versus MPGe
— using two interfaces and a class that implements both, then verified
that class with unit tests.

## Design and Architecture

I organized the project into two Java packages, as the handout
specified: `c` for source code and `u` for unit tests.

`GasolineInterface` declares the contract for anything gasoline-powered:
a method to calculate MPG, setters for miles driven and gallons used,
and a cost-per-gallon get/set pair. `ElectricInterface` mirrors this
for electricity: a method to calculate MPGe, setters for electric
miles and total kWh consumed, and a cost-per-kWh get/set pair. Neither
interface contains any actual logic — they only declare what must
exist, leaving how it works to whatever implements them.

`HybridVehicle` is that implementation. It tracks gas state (miles,
gallons, cost per gallon) and electric state (miles, kWh, cost per
kWh) independently, so the same object can answer gas-only and
electric-only questions on its own terms. I added
`calcAverageHybridMPG()` on top of the two interfaces to handle the
half-gas/half-electric mode the handout calls for. `CarRunner` ties it
together: it instantiates a `HybridVehicle`, feeds it sample data, and
prints the three required calculations.

## Notable Implementation Details

`calcMPGe()` relies on the handout's conversion rate — one gallon of
gasoline equals 33.7 kWh — which I stored as the constant
`KWH_PER_GALLON` rather than a magic number. Both `calcGasMPG()` and
`calcMPGe()` check for a zero denominator and return `0.0` in that
case, instead of throwing an exception or producing `NaN`, so a
freshly created vehicle with no data yet doesn't break anything
downstream.

I wrote `GasolineInterface.java` and `ElectricInterface.java` myself,
and ran into a few small but instructive mistakes along the way: a
misspelled interface name (`GaslineInterface` instead of
`GasolineInterface`), a kWh-related method accidentally left in the
wrong interface file, and a capitalization slip (`getCostPerkwh`
instead of `getCostPerkWh`). The compiler caught each one immediately,
and I worked through them one at a time using `javac`'s error output.

I didn't yet know how to implement `HybridVehicle.java` or
`CarRunner.java` on my own, so I asked Claude to write that code
directly. Even so, a real bug made it through: `setCostPerkWh`'s
parameter was named `totalkWh` instead of `costPerkWh`, so the line
`this.costPerkWh = costPerkWh;` was quietly assigning the field to
itself rather than storing the value passed in. Nothing about this
failed to compile — it just silently did nothing. I only caught it
once I wrote and ran my own unit test against it, a useful lesson in
why even "trivial" getter/setter pairs are worth testing.

## Test Strategy and Edge Cases

`HybridVehicleTests.java`, in the `u` package, contains seven JUnit 4
tests covering `HybridVehicle`:

- **`calcGasMPG`** — a normal case (240 miles on 12 gallons, expecting
  20 MPG) and a zero-gallons edge case that should return `0.0` rather
  than crash.
- **`calcMPGe`** — the handout's own worked example (300 miles on 70
  kWh, expecting 144.43 MPGe) and a zero-kWh edge case.
- **`calcAverageHybridMPG`** — combined gas and electric data, checked
  against the manually computed average.
- **Cost get/set pairs** — round-trip checks confirming
  `costPerGallon` and `costPerkWh` actually store and return what was
  set.

Each test starts from a fresh `HybridVehicle` via a shared,
`@Before`-annotated `setUp()` method, and all floating-point
comparisons use `assertEquals` with a `0.01` tolerance.

The first time I ran the full suite, `testCostPerkWhGetSet` failed
with `expected:<0.24> but was:<0.0>` — the bug described above. Once I
traced it to the mismatched parameter name and fixed it in
`HybridVehicle.java`, all seven tests passed.

## Conclusion

This lab made the practical weight of Java interfaces click for me:
any class implementing `GasolineInterface` or `ElectricInterface` has
to supply every method those interfaces declare, and the compiler
enforces that the moment you add an interface method without updating
the implementing class. It also drove home how literally Java treats
names — filenames, package declarations, and method
capitalization all have to match exactly, and nearly all of my early
errors traced back to small mismatches like these rather than actual
logic bugs.

The more useful takeaway was what my own tests turned up: a real bug
in AI-generated code that had compiled cleanly and run without
incident in `CarRunner`, simply because `CarRunner` never happened to
exercise that particular method. It was a concrete reminder that code
running without errors isn't the same as code being correct, and that
test coverage earns its keep even on code that looks too simple to
bother testing.

## AI Usage Disclosure

**AI system used:** Claude (Anthropic), web/chat interface.

This is the full chronological log of prompts I gave Claude and what
it produced, as required by the lab's AI-usage policy.

1. **Prompt:** "Uploaded the lab handout PDF and help me step by
   step of how to build the HybridVehicle.java"
   **Response:** Claude provided the full `HybridVehicle.java` code
   directly (implementing both interfaces plus
   `calcAverageHybridMPG()`), with an explanation of `implements`,
   `@Override`, and the divide-by-zero guards.

7. **Prompt:** Pasted terminal output showing a `file not found` error
   when compiling.
   **Response:** Claude diagnosed that the files had typos in their
   names (`GaslineInterface.java`, `HybridVechile.java`) and walked me
   through renaming them with `mv`.

8. **Prompt:** Pasted `cat` output of my files showing multiple typos
   (`GaslineInterface`, `implement` instead of `implements`, `privite`
   instead of `private`, mismatched `setGallonsFromGas` capitalization,
   a missing closing brace).
   **Response:** Claude identified each typo individually, then — after
   repeated `sed` attempts didn't take effect — gave a full corrected
   version of `HybridVehicle.java` to paste in via a terminal heredoc.

9. **Prompt:** Pasted a `javac` compile error after successfully
   compiling `HybridVehicle.java`, showing `ElectricInterface.java`
   still contained leftover incorrect content (wrong package, wrong
   method name).
   **Response:** Claude gave the full corrected `ElectricInterface.java`
   content to overwrite the file.

10. **Prompt:** Confirmed successful compilation; 
    **Response:** Claude gave a full checklist of completed vs.

12. **Prompt:** Reported the Codespace reset overnight and lost all
    files; asked how to get back to where we were.
    **Response:** Claude confirmed via `git log`/`ls` that nothing had
    been pushed to GitHub, then had me recreate `GasolineInterface`,
    `ElectricInterface`, and `HybridVehicle` from scratch (content
    unchanged from before), and emphasized committing/pushing after
    every file from then on. The worst thing to have happend.

13. **Prompt:** Asked to add cost-per-gallon/cost-per-kWh get/set
    stubs, per a re-read of the handout.
    **Response:** Claude identified the missing requirement, gave the
    exact lines to add to both interfaces, then walked me through the
    resulting "not abstract" compiler errors in `HybridVehicle.java`
    and what methods to add to fix them.

14. **Prompt:** Pasted a compile error showing a duplicate
    `setTotalkWh` method (from a botched paste) and a missing
    `setCostPerkWh` method.
    **Response:** Claude diagnosed the duplicate/mismatched method and
    gave the corrected replacement block.

15. **Prompt:** Said I didn't know how to write `CarRunner.java` and
    asked Claude to tell me what to do and explain it along the way.
    **Response:** Claude gave the full `CarRunner.java` code directly,
    with a short explanation of `public static void main` and the
    calculation calls.

17. **Prompt:** Asked what was next after `CarRunner` worked.
    **Response:** Claude gave the `curl` commands to download the
    JUnit/Hamcrest jars and explained why they shouldn't be committed
    to Git.

18. **Prompt:** Asked Claude to write the unit tests directly, since I
    didn't know how to write JUnit tests yet.
    **Response:** Claude generated the full `HybridVehicleTests.java`
    (7 tests covering normal cases, divide-by-zero edge cases, and
    cost stub round-trips), with a short explanation of `@Before`,
    `@Test`, and `assertEquals`'s tolerance parameter.

19. **Prompt:** Pasted a test failure: `testCostPerkWhGetSet` expected
    `0.24` but got `0.0`.
    **Response:** Claude traced the bug to `setCostPerkWh`'s parameter
    being named `totalkWh` instead of `costPerkWh`, causing a
    self-assignment that silently discarded the input value; walked me
    through the fix.

20. **Prompt:** Reported confusion after the fix appeared to have
    landed in the wrong file (`u/hybridVehicleTests.java` contained
    `HybridVehicle` class code).
    **Response:** Claude identified the misplaced paste, had me delete
    the stray file, and gave the corrected `setCostPerkWh` method to
    apply to the real `c/HybridVehicle.java` via a targeted `sed`
    command, which resolved it — all 7 tests then passed.

21. **Prompt:** Asked Claude to draft `README.md`.
    **Response:** Claude generated the full `README.md` with project
    layout, jar download commands, and build/run/test instructions
    matching the exact commands used throughout the session.