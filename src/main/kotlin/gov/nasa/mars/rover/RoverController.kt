package gov.nasa.mars.rover

import gov.nasa.mars.parser.MessageParser

class RoverController {
    private val parser = MessageParser()

    fun sendMessageString(messageString: String): String {
        val message = parser.parse(messageString)

        val roverPositions = message.instructions
            .map { (position, commands) ->
                val rover = Rover(position)
                rover.runCommands(message.plateau, commands)
                rover.position
            }

        return sendResponse(roverPositions)
    }

    private fun sendResponse(roverFinalPositions: List<Position>): String {
        return roverFinalPositions
            .map { it.serialize() }
            .joinToString("\n")
    }

    private fun Position.serialize() = "$x $y ${heading.code}"
}