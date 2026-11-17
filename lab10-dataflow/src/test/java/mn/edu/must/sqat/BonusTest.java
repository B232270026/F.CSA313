package mn.edu.must.sqat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BonusTest {

    // =====================================================
    // T1 - ALL-DEFS TEST
    // =====================================================

    @Test
    @DisplayName("T1: Verify broken attendance followed by five consecutive present days")
    void bonus_brokenThenStreak5_belowThreshold_returns3() {
        int[] attendance = {1, 1, 0, 1, 1, 1, 1, 1};
        int threshold = 8;

        int result = DataFlowTargets.bonus(attendance, threshold);

        assertEquals(3, result);
    }

    // =====================================================
    // T2 - ALL-DEFS TEST
    // =====================================================

    @Test
    @DisplayName("T2: Verify one present day meets threshold and returns 5")
    void bonus_onePresent_meetsThreshold_returns5() {
        int[] attendance = {1};
        int threshold = 1;

        int result = DataFlowTargets.bonus(attendance, threshold);

        assertEquals(5, result);
    }

    // =====================================================
    // T3 - ALL-DU-PAIRS ADDITIONAL TEST
    // =====================================================

    @Test
    @DisplayName("T3: Verify null attendance throws IllegalArgumentException")
    void bonus_nullAttendance_throws() {
        int[] attendance = null;
        int threshold = 1;

        assertThrows(
                IllegalArgumentException.class,
                () -> DataFlowTargets.bonus(attendance, threshold)
        );
    }

    // =====================================================
    // T4 - ALL-DU-PAIRS ADDITIONAL TEST
    // =====================================================
   @Test
    @DisplayName("T4: Verify negative threshold throws IllegalArgumentException")
    void bonus_negativeThreshold_throws() {
        int[] attendance = {1, 1, 1};
        int threshold = -1;

        assertThrows(
                IllegalArgumentException.class,
                () -> DataFlowTargets.bonus(attendance, threshold)
        );
    }

    // =====================================================
    // T5 - ALL-DU-PAIRS ADDITIONAL TEST
    // =====================================================


    @Test
    @DisplayName("T5: Verify empty attendance with zero threshold returns 5")
    void bonus_empty_thresholdZero_returns5() {
        int[] attendance = {};
        int threshold = 0;

        int result = DataFlowTargets.bonus(attendance, threshold);

        assertEquals(5, result);
    }

    // =====================================================
    // T6 - ALL-DU-PAIRS ADDITIONAL TEST
    // =====================================================

    @Test
    @DisplayName("T6: Verify all absent days below threshold returns 0")
    void bonus_allAbsent_belowThreshold_returns0() {
        int[] attendance = {0, 0, 0};
        int threshold = 1;

        int result = DataFlowTargets.bonus(attendance, threshold);

        assertEquals(0, result);
    }

    // =====================================================
    // T7 - ALL-DU-PAIRS ADDITIONAL TEST
    // =====================================================

    @Test
    @DisplayName("T7: Verify five consecutive present days meeting threshold returns 8")
    void bonus_fivePresent_meetsThreshold_returns8() {
        int[] attendance = {1, 1, 1, 1, 1};
        int threshold = 5;

        int result = DataFlowTargets.bonus(attendance, threshold);

        assertEquals(8, result);
    }
}
