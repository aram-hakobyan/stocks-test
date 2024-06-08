import com.androidart.common.domain.ext.decimalFormat
import org.junit.Assert.assertEquals
import org.junit.Test

class DecimalFormatTest {

    @Test
    fun `test decimalFormat with two decimal places`() {
        val number = 1234.5678
        val expected = "1,234.57"
        assertEquals(expected, number.decimalFormat())
    }

    @Test
    fun `test decimalFormat with integer`() {
        val number = 1234.toDouble()
        val expected = "1,234.00"
        assertEquals(expected, number.decimalFormat())
    }

    @Test
    fun `test decimalFormat with large number`() {
        val number = 1234567890.1234
        val expected = "1,234,567,890.12"
        assertEquals(expected, number.decimalFormat())
    }
}
