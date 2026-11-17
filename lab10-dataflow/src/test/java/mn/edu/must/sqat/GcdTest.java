
package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class GcdTest {

    @Test
    @DisplayName("G1: Verify GCD when the loop is skipped; cover initial definitions reaching the return statement")
    void zeroIterations_7_0() {
        assertEquals(7, DataFlowTargets.gcd(7, 0));
    }

    @Test
    @DisplayName("G2: Verify GCD after one loop iteration; cover initial and updated variable definitions")
    void oneIteration_0_5() {
        assertEquals(5, DataFlowTargets.gcd(0, 5));
    }

    @Test
    @DisplayName("G3: Verify GCD after two loop iterations; cover loop-carried definition-use dependencies")
    void twoIterations_12_8() {
        assertEquals(4, DataFlowTargets.gcd(12, 8));
    }

    @Test
    @DisplayName("G4: Verify GCD when the initial first operand is smaller; exercise three loop iterations")
    void swapFirstIteration_8_12() {
        assertEquals(4, DataFlowTargets.gcd(8, 12));
    }
}
