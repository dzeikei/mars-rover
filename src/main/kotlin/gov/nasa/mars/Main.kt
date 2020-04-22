package gov.nasa.mars

import gov.nasa.mars.rover.RoverController
import java.io.File

fun main(args: Array<String>) {

    if (args.isNullOrEmpty()) {
        println("Filename of a Mars Rover message filename is required")
        return
    }

    val fileName = args[0]
    val file = File(fileName)
    if (!file.exists()) {
        println("$fileName not found")
        return
    }

    val message = file.readText()

    val roverSquad = RoverController()
    val response = roverSquad.sendMessageString(message)

    println(response)
}