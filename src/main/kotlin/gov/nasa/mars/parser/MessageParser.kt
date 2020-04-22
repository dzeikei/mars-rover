package gov.nasa.mars.parser

import gov.nasa.mars.rover.*
import gov.nasa.mars.model.Message
import gov.nasa.mars.model.Plateau

class MessageParser {

    fun parse(message: String): Message {
        val lines = message.lines()

        val header = lines.first()
        val payload = lines.drop(1)

        val plateau = parsePlateau(header)
        val instructions = parseInstructions(payload)

        return Message(plateau, instructions)
    }

    internal fun parsePlateau(line: String): Plateau {
        val values = line.tokenize(2)
            .map {
                try {
                    it.toInt()
                } catch (e: NumberFormatException) {
                    throw ParseException("Invalid plateau value: \"$line\"")
                }
            }
        if (values.any { it < 0 })
            throw ParseException("Invalid plateau value: \"$line\"")

        return Plateau(values[0], values[1])
    }

    internal fun parseInstructions(
        lines: List<String>
    ): List<Pair<Position, List<Command>>> {
        if (lines.size % 2 != 0)
            throw ParseException("Invalid instruction line count: \"$this\"")

        return lines.chunked(2)
            .map { (positionLine, commandsLine) ->
                val position = parsePosition(positionLine)
                val commands = parseCommands(commandsLine)

                Pair(position, commands)
            }
    }

    internal fun parsePosition(string: String): Position {
        try {
            return string.tokenize(3)
                .let { (x, y, heading) ->
                    Position(
                        x.toInt(),
                        y.toInt(),
                        heading.toHeading()
                    )
                }
        } catch (e: Exception) {
            throw ParseException("Invalid position value: \"$this\"")
        }
    }

    internal fun parseCommands(string: String): List<Command> {
        return string.map {
            when (it) {
                'R' -> Command.Right
                'L' -> Command.Left
                'M' -> Command.Move
                else -> throw ParseException("Invalid command value: \"$this\"")
            }
        }
    }

    private fun String.tokenize(validTokenCount: Int): List<String> {
        val tokens = this.split(" ")
        if (tokens.size != validTokenCount)
            throw ParseException("Invalid token count: \"$this\"")

        return tokens
    }

    private fun String.toHeading(): Heading {
        return when (this) {
            Heading.North.code -> Heading.North
            Heading.East.code -> Heading.East
            Heading.South.code -> Heading.South
            Heading.West.code -> Heading.West
            else -> throw ParseException("Invalid heading value: \"$this\"")
        }
    }
}

class ParseException(message: String?) : Exception(message)