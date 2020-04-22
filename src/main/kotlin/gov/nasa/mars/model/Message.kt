package gov.nasa.mars.model

import gov.nasa.mars.rover.Command
import gov.nasa.mars.rover.Position

data class Message(
    val plateau: Plateau,
    val instructions: List<Pair<Position, List<Command>>>
)