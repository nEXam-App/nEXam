import org.junit.Assert.assertEquals
import org.junit.Test

// A test class is just a normal class
class ExampleUnitTest {

    // Each test is annotated with @Test (this is a Junit annotation)
    @Test
    fun addition_isCorrect() {
        // Here you are checking that 4 is the same as 2+2
        assertEquals(4, 2 + 2)
    }
}
