package nl.qweenink.adventofcode2023

import org.junit.jupiter.api.Test

class Day6 {

    @Test
    fun puzzle1() {
        val times = "53     91     67     68".split(" ").filter { it.isNotBlank() }.map { it.toInt() }
        val distances = "250   1330   1081   1025".split(" ").filter { it.isNotBlank() }.map { it.toInt() }
        val races = times.mapIndexed { index, time -> listOf(time, distances[index]) }

        val games = races.map { race ->
            val time = race.first()
            val distance = race.last()
            var count = 0
            for (holdTime in 0..<time) {
                val timeRemaining = time - holdTime
                val distanceTraveled = timeRemaining * holdTime
                if (distanceTraveled > distance) {
                    count++
                }
            }
            count
        }
        val result = games.reduce { acc, ele -> acc * ele }
        println(result)
    }

    @Test
    fun puzzle2() {
        val time = 53916768L
        val distance = 250133010811025L
        var count = 0L
        for (holdTime in 0L..<time) {
            val timeRemaining = time - holdTime
            val distanceTraveled = timeRemaining * holdTime
            if (distanceTraveled > distance) {
                count++
            }
        }
        println(count)
    }
}
