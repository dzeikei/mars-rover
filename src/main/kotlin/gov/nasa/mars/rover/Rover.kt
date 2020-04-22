package gov.nasa.mars.rover

import gov.nasa.mars.model.Plateau

class Rover(var position: Position) {

    fun runCommands(plateau: Plateau, commands: List<Command>) {
        loop@ for (command in commands) {
            this.position = when (command) {
                Command.Left -> {
                    turnLeft(this.position)
                }
                Command.Right -> {
                    turnRight(this.position)
                }
                Command.Move -> {
                    val newPosition = moveForward(this.position)
                    if (plateau.isValidPosition(newPosition)) {
                        newPosition
                    } else {
                        break@loop
                    }
                }
            }
        }
    }

    private fun turnLeft(position: Position): Position {
        return when (position.heading) {
            Heading.East -> position.copy(heading = Heading.North)
            Heading.West -> position.copy(heading = Heading.South)
            Heading.North -> position.copy(heading = Heading.West)
            Heading.South -> position.copy(heading = Heading.East)
        }
    }

    private fun turnRight(position: Position): Position {
        return when (position.heading) {
            Heading.East -> position.copy(heading = Heading.South)
            Heading.West -> position.copy(heading = Heading.North)
            Heading.North -> position.copy(heading = Heading.East)
            Heading.South -> position.copy(heading = Heading.West)
        }
    }

    private fun moveForward(position: Position): Position {
        return when (position.heading) {
            Heading.East -> position.copy(x = position.x + 1)
            Heading.West -> position.copy(x = position.x - 1)
            Heading.North -> position.copy(y = position.y + 1)
            Heading.South -> position.copy(y = position.y - 1)
        }
    }

    private fun Plateau.isValidPosition(position: Position): Boolean {
        return position.x >= minX &&
                position.y >= minY &&
                position.x <= maxX &&
                position.y <= maxY
    }
}

data class Position(
    val x: Int,
    val y: Int,
    val heading: Heading
)

enum class Command {
    Left, Right, Move
}

enum class Heading(val code: String) {
    North("N"), East("E"), South("S"), West("W")
}