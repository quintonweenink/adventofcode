package nl.qweenink.adventofcode2023

import org.junit.jupiter.api.Test
import java.lang.Integer.min

class Day13 {

    @Test
    fun puzzle1() {
        val sections = data.split(System.getProperty("line.separator") + System.getProperty("line.separator"))
        var sum = 0
        val reflectionPoints = mutableListOf<Int>()
        sections.forEach { section ->
            val lines = section.lines().map { it.map { char -> if (char == '#') 1 else 0 }.joinToString(separator = "") }
            val rows = (0..lines[0].length - 1).map { rowIndex ->
                lines.map { it[rowIndex] }.joinToString(separator = "")
            }

            val isMirrored = (1..lines[0].length - 1).any { reflectionPoint ->
                val spaceBehind = reflectionPoint
                val spaceForward = lines[0].length - reflectionPoint
                val reflectionSize = min(spaceBehind, spaceForward)
                val mirrored = lines.all {
                    val left = it.substring(reflectionPoint - reflectionSize, reflectionPoint)
                    val right = it.substring(reflectionPoint, reflectionPoint + reflectionSize).reversed()
                    Integer.parseInt(left, 2) == Integer.parseInt(right, 2)
                }
                if (mirrored) {
                    sum += reflectionPoint
                    reflectionPoints.add(reflectionPoint)
                }
                mirrored
            }
            if (!isMirrored) {
                (1..rows[0].length - 1).any { reflectionPoint ->
                    val spaceBehind = reflectionPoint
                    val spaceForward = rows[0].length - reflectionPoint
                    val reflectionSize = min(spaceBehind, spaceForward)
                    val mirrored = rows.all {
                        val left = it.substring(reflectionPoint - reflectionSize, reflectionPoint)
                        val right = it.substring(reflectionPoint, reflectionPoint + reflectionSize).reversed()
                        Integer.parseInt(left, 2) == Integer.parseInt(right, 2)
                    }
                    if (mirrored) {
                        sum += reflectionPoint * 100
                        reflectionPoints.add(reflectionPoint)
                    }
                    mirrored
                }

            }
        }

        println(reflectionPoints)
        println(sum)


    }

    @Test
    fun puzzle2() {
        val sections = data.split(System.getProperty("line.separator") + System.getProperty("line.separator"))
        var sum = 0
        sections.forEach { section ->
            val lines = section.lines().map { it.map { char -> if (char == '#') 1 else 0 }.joinToString(separator = "") }
            val rows = (0..lines[0].length - 1).map { rowIndex ->
                lines.map { it[rowIndex] }.joinToString(separator = "")
            }

            var oldReflectionPoint = Pair("", 0)

            val isMirrored = (1..lines[0].length - 1).any { reflectionPoint ->
                val spaceBehind = reflectionPoint
                val spaceForward = lines[0].length - reflectionPoint
                val reflectionSize = min(spaceBehind, spaceForward)
                val mirrored = lines.all {
                    val left = it.substring(reflectionPoint - reflectionSize, reflectionPoint)
                    val right = it.substring(reflectionPoint, reflectionPoint + reflectionSize).reversed()
                    Integer.parseInt(left, 2) == Integer.parseInt(right, 2)
                }
                if (mirrored) {
                    oldReflectionPoint = Pair("H", reflectionPoint)
                }
                mirrored
            }
            if (!isMirrored) {
                (1..rows[0].length - 1).any { reflectionPoint ->
                    val spaceBehind = reflectionPoint
                    val spaceForward = rows[0].length - reflectionPoint
                    val reflectionSize = min(spaceBehind, spaceForward)
                    val mirrored = rows.all {
                        val left = it.substring(reflectionPoint - reflectionSize, reflectionPoint)
                        val right = it.substring(reflectionPoint, reflectionPoint + reflectionSize).reversed()
                        Integer.parseInt(left, 2) == Integer.parseInt(right, 2)
                    }
                    if (mirrored) {
                        oldReflectionPoint = Pair("V", reflectionPoint)
                    }
                    mirrored
                }

            }

            var isAgainMirrored = false

            lines.forEachIndexed lit@ { lineIndex, line->
                rows.forEachIndexed { rowIndex, row ->
                    if (isAgainMirrored) return@lit
                    val replacement = if(lines[lineIndex][rowIndex] == '1') '0' else '1'
                    val newLinesTemp = lines.toMutableList().map { it.toMutableList() }
                    newLinesTemp[lineIndex][rowIndex] = replacement
                    val newLines = newLinesTemp.map { it.joinToString(separator = "") }
                    val newRowsTemp = rows.toMutableList().map { it.toMutableList() }
                    newRowsTemp[rowIndex][lineIndex] = replacement
                    val newRows = newRowsTemp.map { it.joinToString(separator = "") }

                    isAgainMirrored = (1..newLines[0].length - 1).any { reflectionPoint ->
                        val spaceBehind = reflectionPoint
                        val spaceForward = newLines[0].length - reflectionPoint
                        val reflectionSize = min(spaceBehind, spaceForward)
                        val mirrored = newLines.all {
                            val left = it.substring(reflectionPoint - reflectionSize, reflectionPoint)
                            val right = it.substring(reflectionPoint, reflectionPoint + reflectionSize).reversed()
                            Integer.parseInt(left, 2) == Integer.parseInt(right, 2)
                        }
                        if (mirrored && Pair("H", reflectionPoint) != oldReflectionPoint) {
                            sum += reflectionPoint

                        }
                        mirrored && Pair("H", reflectionPoint) != oldReflectionPoint
                    }
                    if (!isAgainMirrored) {
                        isAgainMirrored = (1..newRows[0].length - 1).any { reflectionPoint ->
                            val spaceBehind = reflectionPoint
                            val spaceForward = newRows[0].length - reflectionPoint
                            val reflectionSize = min(spaceBehind, spaceForward)
                            val mirrored = newRows.all {
                                val left = it.substring(reflectionPoint - reflectionSize, reflectionPoint)
                                val right = it.substring(reflectionPoint, reflectionPoint + reflectionSize).reversed()
                                Integer.parseInt(left, 2) == Integer.parseInt(right, 2)
                            }
                            if (mirrored && Pair("V", reflectionPoint) != oldReflectionPoint) {
                                sum += reflectionPoint * 100
                            }
                            mirrored && Pair("V", reflectionPoint) != oldReflectionPoint
                        }

                    }
                }
            }

        }

        println(sum)
    }

    val data2 = """#.##..##.
..#.##.#.
##......#
##......#
..#.##.#.
..##..##.
#.#.##.#.

#...##..#
#....#..#
..##..###
#####.##.
#####.##.
..##..###
#....#..#"""

    val data = """##..##..##.
######..###
.####.##.##
..........#
.####.##.##
.####....##
..##..##..#

##.##.#.#..##
##.##.#.#...#
.#.###......#
.###.##..#..#
##.#.##....##
.#..###.###.#
.#...#...#.##
#.#.##.#...##
#.###.#.##.#.
#.#.#...####.
#.#.#...####.
#.###.#.##.#.
#.#.##.#...##

.........#.##
...........##
..###....##..
##.##.#..#..#
####....####.
##.####.#####
....##.#.....
##.#..##..#.#
###...##.....
##..##..###..
......######.
###.##.#####.
...#.##.#####
##..#..#.##.#
#####..#..##.

.#....########...
.##.....####.....
.##.....####.....
##....########...
..#..##..##..##..
####.###.##.###.#
#.######....#####
..#.#...####...#.
....##.#.##.#.##.
#.#...##.##.##...
.#.##..##..##..##
#.##.#.#.##.#.#.#
.#.#....#..#....#
.#...###.##.###..
##.###........###

..###..##..
.#.#.....##
#.##..##.#.
#.#..###..#
.#..##.##.#
.#....##..#
#.####.##.#
#.####.##.#
.#....###.#
.#..##.##.#
#.#..###..#
#.##..##.#.
.#.#.....##
..###..##..
..###..##..

####..#
#...#.#
.##.#.#
.##.#..
####...
.##.###
.##.##.
....###
.##..#.
......#
####..#
######.
####.##
####...
####...

#####.#..
#.#......
#.#......
#####.#.#
#####.#.#
#.#......
#.#......
#####.#..
.#####...
#..#...##
#.#..##..
####.####
#...##...

.#.##.....#.#..
....####.##.###
#.#..#...###...
.###..#.###...#
..###.##..###..
...#..#.....###
...#..#.....###
..###.##..###..
.###..#.###...#
#.#..#...###...
....####.##.###
.#.##...#.#.#..
.#.##...#.#.#..
....####.##.###
#.#..#...###...
.###..#.###...#
..###.##..###..

#......
..#.##.
##.#...
.##..#.
.##..#.
##.#...
..#.##.
#......
#......
..#.###
##.#...

.##...###
......##.
.##..##.#
#..#.#..#
....#.###
#..#.#..#
.....#..#
######.#.
######.#.
.##..##..
.##.....#
#..#.....
#..#.#...

.#..#.###.#..##
.#..#.###.#..##
#.##..#..####..
#.#####..#..#..
#..#..#.#...###
..#.##.##.##.##
..#..##.#.##.##
####.##...#..#.
..#...#..#.#...
###.#.#..#...##
.#..#######..##
###.#.#.###....
.#.....#.....##

##.#.#..#.##.#..#
.####..##....##..
#.##.#.##.##.##.#
#..#..#..#..#..#.
###.#############
#.##.............
...####..####..##
..#..##..####..##
#.#.#.####..####.

#..#...
..#.##.
#####.#
#..####
####..#
......#
....###
#..#.#.
####.##
####.##
#..#.#.

####...
##.#...
####...
.#.#...
##.####
##.##..
#####..
##.#.##
.##....
.##.###
##..#..
#..####
#...#..

.#.##.#..
.#....#..
#......##
##.##.###
..####...
#.#..#.##
.##..##..
........#
...##....

###.#.##...#..#..
###.#.##...#..#..
#....#.#.###..###
.####.#.##......#
#####.#.##.####.#
#.##....#...##...
...#.###...#..#..
###...##..#####..
##..#.###........

#....######....#.
#....#.##.#....#.
..##........##..#
.##.#..##..#.##..
#....##..##....##
..##..####..##..#
.#..#......#..#..

..#..#.
#..#.#.
#..#.#.
.....#.
##.....
.####.#
.####.#

####...##..##..
####.####.####.
..#.#.##..####.
..#.#.##..####.
####.####.####.
####...##..##..
.###.#.##.#.##.
####.#.##.####.
#...#.##.......
#..###.##.####.
.#...#####....#
####.###.#.##.#
.#.###.###....#
..#.##..##.##.#
#.###..#.#.##.#

##.###..##.
.####......
.####......
##.###..##.
######..##.
.#.##......
.##..#.#..#
#.#...#..#.
#.#.##.....
###.###....
...#..#.##.

.......#.####
.......#.####
#..##........
.#...###.##.#
##.#.##.#####
#.#####...#.#
.#.###.####.#
..###....#..#
#.#.####..##.
#.#.####..##.
..###....#..#
.#.###.####.#
#.#####..##.#
##.#.##.#####
.#...###.##.#
#..##........
.......#.####

#....##.###
#.#.#.#.#..
#.#.#.#.#..
#....##.###
#.#...#.#.#
.#......##.
.#..#.##...
....#..#..#
.###.##.##.
.###.##.##.
....#..#..#
.#.##.##...
.#......##.
#.#...#.#.#
#....##.###

.#....#.##.
..#...#....
####...####
..#.#######
.##...#....
#.###.#....
#.###.#....
.##...#....
..#.#######
####...####
..#........
.#....#.##.
#.#.#.#....
.#.##......
.##..#.#..#
#..##......
#.######..#

.###.#.####.#.#
#..##...##...##
#..##.##..##.##
.###.#......#.#
##..##.####.##.
....##.####.##.
.#...########..
...#...####...#
..##..........#
..##....##....#
..###.#.##...##
.#.###.#..#.###
.###...#..#...#
##.#####..#####
##..#........#.
#######.##.####
#######.##.####

###...##......#
###....#......#
#..#.#.#......#
..##.##........
#.#...#.##..##.
###...#########
.#...###.####.#
..#..#...#..#..
..#.##.##....##

.##..#..###.#.#
.##..##.....##.
....###.####..#
.......###..#..
######..#.##..#
.##..#.#####.#.
.##..#.###.#.#.

..##.##....##
#.##.#....##.
#....##.#.##.
######.#.####
######..#....
#....#..#..##
.#..#.####...
.####.##.#...
.####.##.#...

#...##...#.#.
#..####..#.##
#........#..#
.#.####.#..##
..##..##..###
#.####.#.#..#
##.#..#.##.#.
...####...##.
#.######.#.##
#.##..##.#...
.##.##.##..#.
.##.##.##....
#.#.##.#.#.##
#........####
############.
##.####.##.##
##.####.##.##

.#....##...
...#......#
##.##....#.
...#......#
..#.#....#.
#..##....##
###.######.
.###......#
#.#..#..#..
..#.#....#.
..##.#..#.#
..##......#
####.####.#
#..###..###
#.###....##
..###.##.##
..###.##.##

#.....#.#....#.#.
##....#.#....#.#.
#.##.#.###..###.#
.#.##.#..#..#..#.
###...##.####.##.
#.###.####..####.
#.#.....######...

##.#..#.###
...####...#
###....####
###....####
...####...#
##.#..#.###
#.##.###.#.
..#.##.#..#
##......##.
..######...
#.#.##.#.##
#.##..##.##
..######..#

#####.##...##...#
#..##.###.##.##.#
.##..#.#.......##
....####.#.##.##.
#..#..#..#...##..
#..##.##.#.#.##.#
#..##.##...#.##.#

..#.#.####.#.#.
##.####..####.#
##.#........#.#
..#....##......
#####......####
..#.#.####.#.#.
..#.#.#..#.#.#.
...##..##..##..
..#.#......#.#.

..#.###.##.##
##.#.##...###
..#####..#.##
####..####.##
##...#.#.....
.##...#.##...
####....###..
##...###..###
..#..##..##..
##.##.#.#..##
####.#..#.###
...###.#.####
...#..#.##.##
##.....#.....
...########..
#####...##...
##.#.#....#..

...##.##.
###.#####
..######.
##.#.....
...#.####
##.###..#
##.#.#..#
..##.####
..#.#.##.

.##....##..#..#
####.#.#....##.
#.#....#..##.#.
#..#...###..#.#
#.#.###..#####.
.#...#....#...#
.#...#....#...#
#.#.###..#.###.
#..#...###..#.#
#.#....#..##.#.
####.#.#....##.
.##....##..#..#
.##....##..#..#

..##.......
.####..###.
......###..
......###.#
..#....#..#
##..##..#..
.####..##..
..##..#..#.
#....##.#.#
#....##.#.#
..##..#..#.
.####..##..
##..##..#..

###.##.######
....##.......
##..##..####.
..#.##.......
..#....#....#
#........##..
###....######
..#....#....#
.#.####.#..#.
#.##..##.##.#
.#.####.#..#.
.########..##
#.##..##.##.#

###....####..#.##
##.#..#.#####.###
....##....###..##
...#..#....#.##..
.########..#.....
..######...######
.#..##..#..#.#..#

#####.##.##..
....##..#.###
.##...###.#.#
.##..#..##...
.##.#..#..#.#
#..#...###.##
....#..##..##
#####....##.#
......####...
.##...##..###
.##.####.###.
....##...##.#
....##..#...#
.##.#..####..
####.####....
.##..#...#.#.
.##..#...#.##

##.#....##....#
.#.###.#..#.###
.##.#.##..##.#.
.###.##.##..#.#
..##..#.##.#..#
...#.#......#.#
.#.###......###
.##..#.#..#.#..
...#.##....##.#
######..##..###
..#.#..####..#.
##...##.##.##..
#.#####....####
#.#####....####
##...##.##.##..
..#.#..####..#.
######..##..###

##.##....
###......
....#.#..
#..#.##..
#..#.##..
....#.#..
#.#......
##.##....
#####..##
##..#.#..
####.##..

#.#....
#.....#
.###..#
.###..#
#.....#
#.#....
#.##..#
#...###
##.#...
..##.##
##..#..
..#...#
..#....
##..#..
..##.##

#..#.##.....#
#..#.#....#..
.##.###....#.
.##.###....#.
#..#.#....#..
#..#.#......#
#..##....##..
.##.....####.
#..##..#...#.
.##.#..#...#.
#..#..#......
.##..#.##.#..
.....#####.#.

..#.#..##
###.####.
...#..##.
.....#.#.
...##...#
.....#.#.
####.....
#####....
.....#.#.

#..#####..#
#......###.
#......###.
#..#####..#
######.....
#.##.....##
..#####.##.
###......##
###.....###
..#####.##.
#.##.....##

.#.....#.#.
...#.###.##
...#.######
.#####....#
##.#..#..##
##.#..#..##
.#####....#
...#.######
...#.###.##

..#......
##..#.#.#
#.#.#.#.#
#.####..#
.##...##.
.#....##.
#.####..#
#.#.#.#.#
##..#.#.#
..#......
..#......

.#####.#..####.
#..#..#...####.
##.###..#..##..
#..###..#..##..
#..#..#...####.
.#####.#..####.
#.........####.
#.####.###.##.#
..#.###....##..
#.#.#..####..##
#..##.##..####.
.....#.####..##
###.......####.
..#...#........
###.#.##.######
###..###...##..
...#...##..##..

..#.##.....#.#.
.####..##..####
.#....####....#
#...###..###...
..#.##....##.#.
..#.##....##.#.
#...###..###...

#........##.#.###
.#.##..##.....#..
.#.#.#.#.####..#.
.#.#.#.#.####..#.
.#.##..##.....#..
#........##.#.##.
#.#.###.#....#...
#.#.###.#....#...
#........##.#.##.
.#.##..##.....#..
.#.#.#.#.####..#.

..#...#####
#.##...##..
##...##..##
##...##..##
#.##...##..
..#...#####
#.####.#..#
...#..###..
#..##.##.##
#.##.#..###
.###.##.#..
.#.#...#.##
#..###..#..

....#...####...
..##..##.##.##.
##..##..####..#
.#..###.#..#.##
..###.##....##.
...###..#..#..#
...#.#.######.#
..##..##.##.##.
##..#...####...

....##.....##.#.#
#.#....#.######..
############..###
#.##..##.#.####..
#.##..##.#.####..
############..###
#.#....#.######..
....##.....##.#.#
#.#.##.#.#.#..###
#.######.##.####.
#######.##.#.#.#.
#.######.#..##.#.
..#.##.#......#.#
..#.##.#..##...#.
.##....##.##..#..

##..#...##...####
##..#...##...####
###......#.......
.##.##..##..#.##.
.#.#.#.##....#..#
##..#.#.#..##.##.
#..####...##..##.
##.#....###.##.##
.#...###.####....

..#.#..######..#.
..#.#..######..#.
..#..##.......#..
...#.#.######.#.#
##...#.#....#.#..
#.######....#####
#..####..##..####
......#......#...
#...###.####.###.
....#.#.#..#.#.#.
#..#.###.##.###.#

..########..#
...#.#.##....
##.###.#..##.
###.#.#.#...#
##.##....##.#
...###.####.#
###...#.###..
###...#.###..
...###.####.#
##.##....##.#
###.#.#.#...#
##.###.#..###
...#.#.##....

##....#..#.
##.#.#####.
##.###.##.#
#...###.##.
#####..#..#
##.##.#####
..#..#####.
...#.##.#.#
...#.##.#.#
..#..#####.
##.##.#####

.#.....#.###.###.
##..##.#.###.#.#.
##.###.#.###.#.#.
.#.....#.###.###.
..#....#.......##
...###.#.#..#...#
##.####.#.##..##.
...#.##.##...##.#
..##..#.##......#
..##..#.##......#
...#.##.##...##.#
##.####.#.##..##.
...###.#.#..#...#

.###....##...
##....##..##.
###..##.##.##
...#..#....#.
#......####..
.#.##...##...
.....#.####.#
#.####..##..#
#.####..##..#
.....#.####.#
.#.##...##...
#......####..
......#....#.
###..##.##.##
##....##..##.

...#.##
#.###..
#...###
#...###
#.###..
...#.##
.#.####
#.###.#
.#..###

#.#..#.####
.#....#.###
########.##
.##..##....
#......#..#
.#....#..#.
#.#..#.##.#
.######....
.##..##..##
##....####.
##.##.###..
#..##..####
#......#..#
##.##.###..
.##..##...#
.##..##...#
##.##.##...

.#.#.####....#.#.
..#.##..##.#.##.#
##..###....###.#.
..#..#..##...####
.##..#..####..###
.####.##...##..##
.####.##...##..##
.##..##.####..###
..#..#..##...####
##..###....###.#.
..#.##..##.#.##.#
.#.#.####....#.#.
.#.####.#...#.###
...#.##.#.####...
...#.##.#.####...
.#.####.#...#.###
.#.#.####....#.#.

#####.#..#.##
.##..#.###..#
......##.####
#..#.#...####
#..#.#...####
......##.####
.##..#.###.##
#####.#..#.##
#..#.#.##...#
.##....##...#
#..###..#...#
#..#..####...
########...#.
#..#.#..#####
.##..#...#..#

#.#..##..####
.#.#.########
####.#.......
.......##.##.
.#..#.#......
######...#..#
.##.####.....
.##.####.....
######..##..#
.#..#.#......
.......##.##.

...#.###..###
#.#####.##.##
#.#####.##.##
..##.###..###
##.####....##
...##########
#.....##..##.
.#....#....#.
..#.#..####..
.#....#....#.
.###...####..

#######.#....
#######.#....
.####..#..##.
#....##..#.##
##..#########
..##..#####..
...#..#.####.

#..####..
#..#.##..
.....##.#
#######.#
####.###.
######...
#..#.####
.##..####
.##.####.
.##....##
....#....
######.##
.##.###.#
#..#...##
....###.#
#######.#
####..#..

..##.#####....##.
..##.#####.#..##.
..##...#..######.
#....#.#..#.#..#.
###.#...#.##.##.#
.##..####...####.
#....###..#..##.#
....#....#.##.#..
..#...#####.###..
#.#......#.#.#..#
..####.#..##..##.
..#####.##...#..#
.#####..#....#...
...#...#...#.##..
...#...#...#.##..

##..####...#.##.#
##..##.#...#.##.#
.####..###.#.#..#
##..#####.#...#.#
#....######..##..
#....#..###......
..##..##.##.#.#..
#.##.##..#...#.##
.......##.....##.
#######.####.#..#
#.##.###.####.###
.#..#...#..#.##..
##..##.#.####.#..
#.##.#..#..###...
......#.#.#.#.##.
........#.####..#
..##...#.##..#...

#.##...#.
#.#..#..#
#....##.#
.#.#...##
.#.#...##
#....##.#
#.#..#..#
#.##...#.
#...##.##
.####...#
.####...#
#...##.##
#.##...#.
#.#..#.##
#....##.#

#####.#...#..
#####.#...#..
.##..###.#...
.##.#.....#..
#.#.#.##.#..#
.###.########
...#.###....#
.#####..#.##.
#.#...###..#.
..#.#........
..#.#......#.
#.#...###..#.
.#####..#.##.

#.##.#.#.##
#....##....
.####.###..
.####.###..
#....##....
#.##.###.##
######.###.
##..##....#
#.##.##.###
########..#
#....##..#.

..###..##..
.#.#.##..##
.#.####..##
#...#######
.####......
#......##..
##.....##..
....#......
....#......
#...###..##
.##.##.##.#
##..#......
#..#.######
#.###..##..
.#..#......

##.#..#..#.
#......#.#.
#.####....#
.#..#..####
####.....##
####.....##
.#..#..####
#.####....#
#......#.#.
##.#..#..#.
..##..#.#..
...#.##....
.##..##.###
###..##.###
...#.##....

....#..#.##
......#..##
#..######.#
#..#..#.#..
######.####
.##.###.#..
.##.#.#....
#..##...###
....#..##..

#.###.#.#..#.
#....###.##..
#...#.##.##.#
.....#.#....#
##...########
.##...##.##.#
..####..#..#.
.###...######
.#.#.##.####.
..##.........
..##.........
.#.#.##.####.
.###...######
..####..#..#.
.##...##.##.#
##...########
.....#.#....#

#...##.#....#
#..###.#....#
##.....##..##
#.#..####..##
#...#..#....#
###.#.#######
####.##.####.
####.#.......
#....##.#..#.
.##.#.###..##
#.#..#..#..#.
#.#..#..####.
..#...##....#
.##.#..##..##
####.###.##.#
.##..#.......
...##..######

##.#..###
##.#.####
.##.#.###
.#.#.##..
.#..#####
...###...
#....##..

.#..####..#
#...#..#...
#...#..#...
##..####..#
.###....###
....#..#...
.#.##..##.#
##.#.##.#.#
#..........
.##.#..#.##
#..#.##.#..
.....##....
.#..####..#

##.#..#.#
...####..
..##..##.
..##..##.
###.##.##
.########
....##...

###.####....##.
###...#...##.##
...#.#.#...#.##
##...#....##.#.
###..###.......
......##..####.
##..######..###
##..######..###
......##..####.
###..###.#.....
##...#....##.#.

###.####.
#....##..
.#..#..#.
#....##..
..##....#
#....##..
##..####.

#..#.######.#
.#.##......##
#.#....##....
..#####..###.
..#...####...
....#......#.
.##...####...
.#.##.####.##
..#..##..##..
.##...####...
#.####....###
##...######..
#..##.#..#.##
#..##.#..#.##
##...######..

.........##
........#..
##....#####
#.####.####
..#..#..#..
##....###..
.#.##.#.#..
#......##..
#.####.####
...##.#....
.######....

.##.######.##.#
..###....###..#
#.#.##..##.#.##
###........####
###........####
#.#.##..##.#.##
..###....###..#
.##.######.##.#
.#.#.####.#.##.
..##########..#
.#.########.#.#
.#..#.##.#..#..
#..#......#..#.

.#.#..#
.###..#
..#....
#.##..#
#.#####
..#....
#..####

##..#..####..#..#
..###........###.
##..###....#.#..#
#..##.#.##.#.##..
##..###.##.###..#
..#...##..##...#.
###.#.#....#.#.##
#.#.#.#....#.#.#.
##...#......#...#
#...#.#....#.#...
#...#.#....#.#...
##...#......#...#
#.#.#.#....#.#.#.
###.#.#....#.#.##
..#...##..##...#.

#..##.#.#
.#...##..
.##.#..#.
.##.#..#.
.#..###..
#..##.#.#
.######.#
#.#..#...
#.#..#...

.#..#.#.#..#.....
#....#.##.#......
#....#.##.#......
.#..#.#.#..#...#.
.#..#...#..###...
#....##.##.#..#.#
#.##.#..#..#.##..
#....#####..##.##
#....###.########
.####.##..##.....
..##.......#.....
######.#.#.#.#.#.
......##.###...##
.####.#.##.#.#.##
#....#..##.#..###

#..##............
.##.####..##..###
.##..#.#.####.#.#
......#.#....#.#.
......##########.
#..##.###..#.###.
......##.####.##.
.##.##..........#
.##..#####..#####
....#...#.##.#...
.##...#..#..#..#.

....#.##.#.##.#
.##.##.########
#..##.####.##.#
.##.###.#.#..#.
#####..##.#..#.
.##...#.###..##
.#...#....####.
.....##....##..
####.####.####.
#####...##....#
......#.#..##..
#####...##....#
####..####....#

.#....#..#..#
.#.####.#.##.
###..#####..#
.##..##.#....
..#..#..#####
#......#.####
#......#.....
...##...##..#
##....##.#..#
##....##.....
#.#..#.#..##.
###..###.....
.######.##..#

......##..#
....#.#.##.
..#.#......
.#......##.
.###..##..#
.###..##..#
.#......##.
..#.#......
....#.#.##.
......##..#
##.#.#.#.##
.#.###.....
####..#####

##...#######.#.##
##..###...#.#....
...#.####.#..#...
##.#...#.#...####
####..#.##..##.##
....#####.#.####.
##.##..#.#.#.#...

..#.#...#.#.##..#
.##..#...##.#.#..
#...##.#..#..##..
#..#.#.#..#.#####
#...##.###.#.###.
##..#.#.##...#..#
##..#.#.##.##.###
####.##.#.#...##.
.....###....###..
#.##.#.##..#...#.
.#..#.#....###..#
#.#.####.#.#.#.#.
#.#.####.#.#...#.
##.###.##.##.....
##.###.##.##.....
#.#.####.#.#...#.
#.#.####.#.#.#.#.

######.#.#..#.#
##..###........
#.##.#....##...
.#..#.####..###
..##..###....##
......#........
#....###.####.#
......#.##..##.
#.##.##...##..#
#....##.##..##.
#.##.#.........

####.##.#
.##....#.
...#####.
...#####.
.##....#.
####.#..#
####.#..#

.###..###.####.
###.##.####..##
#........######
#.#....#.######
#.##..#..######
##.#..#.##....#
.#..##..#......
#.#.##.#.##..##
###.##.####..##
..#.##.#...##..
..######.......

#...##..#
#...##..#
..######.
.#.###..#
#...####.
.#..###..
#....#..#
#.#.#....
####..#.#
####..#.#
#.#.#....
#....#..#
.#..###..
#...####.
.#.###..#
..#####..
#...##..#"""
}
