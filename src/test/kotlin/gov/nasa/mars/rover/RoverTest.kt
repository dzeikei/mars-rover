package gov.nasa.mars.rover

import gov.nasa.mars.model.Plateau
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RoverTest {
    val plateau = Plateau(3, 3)

    @Test
    fun `rover SHOULD turn right to all directions`() {
        val rover = Rover(
            Position(0, 0, Heading.North)
        )

        // Turn right
        rover.runCommands(plateau, listOf(Command.Right))
        assertEquals(Heading.East, rover.position.heading)

        // Turn right
        rover.runCommands(plateau, listOf(Command.Right))
        assertEquals(Heading.South, rover.position.heading)

        // Turn right
        rover.runCommands(plateau, listOf(Command.Right))
        assertEquals(Heading.West, rover.position.heading)

        // Turn right
        rover.runCommands(plateau, listOf(Command.Right))
        assertEquals(Heading.North, rover.position.heading)

    }

    @Test
    fun `rover SHOULD turn left to all directions`() {
        val rover = Rover(
            Position(0, 0, Heading.North)
        )

        // Turn left
        rover.runCommands(plateau, listOf(Command.Left))
        assertEquals(Heading.West, rover.position.heading)

        // Turn left
        rover.runCommands(plateau, listOf(Command.Left))
        assertEquals(Heading.South, rover.position.heading)

        // Turn left
        rover.runCommands(plateau, listOf(Command.Left))
        assertEquals(Heading.East, rover.position.heading)

        // Turn left
        rover.runCommands(plateau, listOf(Command.Left))
        assertEquals(Heading.North, rover.position.heading)

    }

    @Test
    fun `rover SHOULD move all directions`() {
        val rover = Rover(
            Position(0, 0, Heading.East)
        )

        // Move north
        rover.runCommands(plateau, listOf(Command.Left, Command.Move))
        assertEquals(0, rover.position.x)
        assertEquals(1, rover.position.y)
        assertEquals(Heading.North, rover.position.heading)

        // Turn right and move East
        rover.runCommands(plateau, listOf(Command.Right, Command.Move))
        assertEquals(1, rover.position.x)
        assertEquals(1, rover.position.y)
        assertEquals(Heading.East, rover.position.heading)

        // Turn right and move South
        rover.runCommands(plateau, listOf(Command.Right, Command.Move))
        assertEquals(1, rover.position.x)
        assertEquals(0, rover.position.y)
        assertEquals(Heading.South, rover.position.heading)

        // Turn right and move East
        rover.runCommands(plateau, listOf(Command.Right, Command.Move, Command.Right))
        assertEquals(0, rover.position.x)
        assertEquals(0, rover.position.y)
        assertEquals(Heading.North, rover.position.heading)
    }

    @Test
    fun `rover SHOULD NOT move beyond the plateau`() {
        val rover = Rover(
            Position(2, 2, Heading.East)
        )

        // Turn right and move 3 times
        rover.runCommands(plateau, listOf(Command.Move, Command.Move, Command.Move))
        assertEquals(3, rover.position.x)
        assertEquals(2, rover.position.y)
        assertEquals(Heading.East, rover.position.heading)
    }
}