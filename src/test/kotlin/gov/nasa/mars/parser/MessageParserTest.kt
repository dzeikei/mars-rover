package gov.nasa.mars.parser

import gov.nasa.mars.rover.Command
import gov.nasa.mars.rover.Heading
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MessageParserTest {
    val testInput = """
        5 5
        1 2 N
        LMLMLMLMM
        3 3 E
        MMRMMRMRRM
    """.trimIndent()

    val parser = MessageParser()

    @Test
    fun `parser SHOULD parse plateau string`() {
        val plateau = parser.parsePlateau("5 5")
        assertEquals(0, plateau.minX)
        assertEquals(0, plateau.minY)
        assertEquals(5, plateau.maxX)
        assertEquals(5, plateau.maxY)
    }

    @Test
    fun `parser SHOULD fail on invalid plateau string`() {
        assertThrows<ParseException> {
            // Not an integer
            parser.parsePlateau("5 A")
        }

        assertThrows<ParseException> {
            // Too many values
            parser.parsePlateau("5 1 3")
        }
    }

    @Test
    fun `parser SHOULD fail on negative plateau string`() {
        assertThrows<ParseException> {
            // Not an integer
            parser.parsePlateau("5 -5")
        }
    }

    @Test
    fun `parser SHOULD parse rover position string`() {
        val position1 = parser.parsePosition("2 3 E")
        assertEquals(2, position1.x)
        assertEquals(3, position1.y)
        assertEquals(Heading.East, position1.heading)

        val position2 = parser.parsePosition("9 23 N")
        assertEquals(9, position2.x)
        assertEquals(23, position2.y)
        assertEquals(Heading.North, position2.heading)

        val position3 = parser.parsePosition("12 4 S")
        assertEquals(12, position3.x)
        assertEquals(4, position3.y)
        assertEquals(Heading.South, position3.heading)

        val position4 = parser.parsePosition("0 1 W")
        assertEquals(0, position4.x)
        assertEquals(1, position4.y)
        assertEquals(Heading.West, position4.heading)
    }

    @Test
    fun `parser SHOULD fail on invalid rover position string`() {
        assertThrows<ParseException> {
            // Not a position
            parser.parsePosition("LRLRMM")
        }

        assertThrows<ParseException> {
            // Wrong order
            parser.parsePosition("E 5 3")
        }

        assertThrows<ParseException> {
            // Invalid heading
            parser.parsePosition("5 3 A")
        }

        assertThrows<ParseException> {
            // Not enough values
            parser.parsePosition("1 5")
        }

        assertThrows<ParseException> {
            // Too many values
            parser.parsePosition("1 5 W 3")
        }
    }

    @Test
    fun `parser SHOULD parse rover command string`() {
        val commands = parser.parseCommands("LMRMRMLMM")
        assertEquals(9, commands.size)
        assertEquals(
            listOf(
                Command.Left, Command.Move, Command.Right, Command.Move,
                Command.Right, Command.Move, Command.Left, Command.Move, Command.Move
            ), commands
        )

    }

    @Test
    fun `parser SHOULD fail on odd number of instruction lines`() {
        assertThrows<ParseException> {
            parser.parseInstructions(listOf("1 5 W"))
        }

        assertThrows<ParseException> {
            parser.parseInstructions(listOf("1 5 W", "LRLRMM", "0 2 E"))
        }
    }

    @Test
    fun `parser SHOULD fail on invalid rover command string`() {
        assertThrows<ParseException> {
            // Invalid command
            parser.parseCommands("LRTLRMM")
        }
    }

    @Test
    fun `parser SHOULD parse test message string successfully`() {
        val message = parser.parse(testInput)
        assertEquals(0, message.plateau.minX)
        assertEquals(0, message.plateau.minY)
        assertEquals(5, message.plateau.maxX)
        assertEquals(5, message.plateau.maxY)
        assertEquals(2, message.instructions.size)
    }

    @Test
    fun `parser SHOULD fail on empty message string`() {
        assertThrows<ParseException> {
            parser.parse("")
        }
    }
}