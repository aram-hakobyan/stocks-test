import com.androidart.common.domain.ext.growthPercentage
import org.junit.Assert.assertEquals
import org.junit.Test

class GrowthPercentageTest {

    @Test
    fun `test growthPercentage with positive growth`() {
        val pair = Pair(100.0, 150.0)
        val expected = 50.0
        assertEquals(expected, pair.growthPercentage(), 0.001)
    }

    @Test
    fun `test growthPercentage with negative growth`() {
        val pair = Pair(100.0, 50.0)
        val expected = -50.0
        assertEquals(expected, pair.growthPercentage(), 0.001)
    }

    @Test
    fun `test growthPercentage with zero initial value`() {
        val pair = Pair(0.0, 100.0)
        val expected = 100.0
        assertEquals(expected, pair.growthPercentage(), 0.001)
    }

    @Test
    fun `test growthPercentage with no change`() {
        val pair = Pair(100.0, 100.0)
        val expected = 0.0
        assertEquals(expected, pair.growthPercentage(), 0.001)
    }
}
