package gov.nasa.mars.rover

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RoverAppTest {
    // Test Input:
    val testInput = """
        5 5
        1 2 N
        LMLMLMLMM
        3 3 E
        MMRMMRMRRM
    """.trimIndent()

    //Expected Output:
    val expectedOutput = """
        1 3 N
        5 1 E
    """.trimIndent()

    @Test
    fun `app SHOULD return expected output`() {
        val roverSquad = RoverController()
        val output = roverSquad.sendMessageString(testInput)
        print(output)
        assertEquals(expectedOutput, output)
    }
}