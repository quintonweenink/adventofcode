package nl.qweenink.adventofcode2023

import org.junit.jupiter.api.Test
import java.lang.RuntimeException
import kotlin.math.abs

class Day11 {

    @Test
    fun puzzle1() {
        val lines = data.split(System.getProperty("line.separator"))
        val grid = lines.map { it.toCharArray().toMutableList() }
        val newGrid = mutableListOf<MutableList<Char>>()
        grid.forEach {
            if (it.contains('#')) {
                newGrid.add(it)
            } else {
                newGrid.add(it)
                newGrid.add(it)
            }
        }
        val newGrid2 = newGrid.map { value ->
            mutableListOf<Char>()
        }
        newGrid[0].forEachIndexed { charIndex, value ->
            var foundGalaxy = false
            for (lineIndex in 0..newGrid.size - 1) {
                if (newGrid[lineIndex][charIndex] == '#') {
                    foundGalaxy = true
                }
            }
            if (!foundGalaxy) {
                for (lineIndex in 0..newGrid.size - 1) {
                    newGrid2[lineIndex].add('.')
                    newGrid2[lineIndex].add('.')
                }
            } else {
                for (lineIndex in 0..newGrid.size - 1) {
                    newGrid2[lineIndex].add(newGrid[lineIndex][charIndex])
                }
            }
        }

        val galaxyPositions = mutableListOf<Pair<Int, Int>>()
        newGrid2.forEachIndexed {lineIndex, line ->
            line.forEachIndexed { charIndex, char ->
                if(char == '#') {
                    galaxyPositions.add(Pair(lineIndex, charIndex))
                }
            }
        }

        var sum = 0
        galaxyPositions.forEach { first ->
            galaxyPositions.forEach { second ->
                sum += abs(first.first - second.first) + abs(first.second - second.second)
            }
        }
        println(sum/2)
    }

    @Test
    fun puzzle2() {
        val lines = data.split(System.getProperty("line.separator"))
        val grid = lines.map { it.toCharArray().toMutableList() }
        val emptyLines = mutableListOf<Int>()
        grid.forEachIndexed { index, it ->
            if (!it.contains('#')) {
                emptyLines.add(index)
            }
        }
        val emptyColumns = mutableListOf<Int>()
        grid[0].forEachIndexed { charIndex, value ->
            var foundGalaxy = false
            for (lineIndex in 0..grid.size - 1) {
                if (grid[lineIndex][charIndex] == '#') {
                    foundGalaxy = true
                }
            }
            if(!foundGalaxy) {
                emptyColumns.add(charIndex)
            }
        }

        val galaxyPositions = mutableListOf<Pair<Int, Int>>()
        grid.forEachIndexed {lineIndex, line ->
            line.forEachIndexed { charIndex, char ->
                if(char == '#') {
                    galaxyPositions.add(Pair(lineIndex, charIndex))
                }
            }
        }

        val larger = 1000000L
        var sum = 0L
        galaxyPositions.forEach { first ->
            galaxyPositions.forEach { second ->
                val currentSum = abs(first.first - second.first) + abs(first.second - second.second)

                val columnsBetween = emptyColumns.filter { (it >= first.second && it <= second.second) ||
                        (it >= second.second && it <= first.second) }.size
                val colExpanded = columnsBetween * larger - columnsBetween
                val linesBetween = emptyLines.filter { (it >= first.first && it <= second.first) ||
                        (it >= second.first && it <= first.first) }.size
                val linesExpanded = linesBetween * larger - linesBetween
                sum += currentSum + colExpanded + linesExpanded
            }
        }
        println(sum/2)
    }

    val data2 = """...#......
.......#..
#.........
..........
......#...
.#........
.........#
..........
.......#..
#...#....."""

    val data = """.........................#.........#..................#..............#.......#.....#........................#............#.....#...........#
.............................................#.....................................................#........................................
.......#............#..........#............................................................................................................
.#................................................#........#.............................................#..............................#...
................#.....................#.............................................................................#.......................
........................................................................#....................#................#...........#.................
...........................................#........................................#.......................................................
..................................#......................#.......#...........#..............................................................
..........#.................................................................................................................................
..............................................#..........................................#.....................................#............
..........................................................................#.................................................................
...#................#...........#.......#............................#...............................#......................................
.........................................................................................................................#..................
.............................................................................#........#.............................#....................#..
..........#.....#...............................#..............................................................#...................#........
.........................................................#..................................................................................
.....................#......#.......................#.......................................................................................
.....................................#.....#.......................#.........................#...........#..............#..................#
...................................................................................................#........................................
...............#........................................................#.........................................#............#............
#........................................................................................#..................................................
...............................................................................#............................................................
....................................................#......#.........#..........................#...........................................
...............................................#.....................................................#...........................#..........
.....#............#........................................................................................................................#
...........................................#.................................................................#......#.......................
............#....................#...............................#..........#...............................................................
............................................................#......................................#........................#...............
...........................#.......................................................#..........#......................................#......
...................#..............................................................................................#.........................
....................................................................#......................................................................#
.............#................................#.............................................................................................
........#.........................#..........................................#..................#...........................................
...........................................................................................................#...............#................
........................................#.........#.......#.....................................................#...........................
..#................................................................................#.................................#.............#........
.....................#......#.......................................................................#.......................................
........................................................................................................................................#...
..........#.....#........................................................#............#......................................#..............
.....................................................#.......#..............................................................................
.............................................................................................................#.........#....................
...#.............................................................#........................................................................#.
.............................#.........#.................#.......................#.................#........................................
..............................................#.........................#...................#...............................................
............#............#..........................#.......................................................................................
....................................................................................................................#.......................
...................#...............................................#........................................................................
...................................#............#..........................................................#..............#.....#......#....
.......................#......#...........................................#..............#.........#........................................
............................................................................................................................................
..#............................................................#.....#...............................................#......................
.......#......................................#.........#.....................................................#..........................#..
.........................#...............................................................................#..................................
.............#...............................................................................#..............................................
#........................................#..................#.............#.........#.................................................#.....
............................................................................................................................................
..................................................#........................................................................................#
........#.............#............#...........................................................................#............................
..............#.....................................................................................#.......................................
...................................................................................................................#........................
..................#...........#............#................#...................................#..............................#............
......................................................................#.................#....................#.......................#......
.................................................................................#.........................................#................
............#....................................#......#...................................................................................
......................#.........................................#...........................................................................
.......#....................#.........#....................................................#.......................#............#...........
.............................................................................#......................#.......................................
...#.............#..........................#........#...............#...................................#................#.................
............................................................................................................................................
.................................................................................................#..........................................
.........................#.....#..................................................#...........................................#.......#.....
............................................................................................................................................
............#.....................................................#...............................................................#.........
...#........................#.................#.........................................................#...........#.....................#.
................#....................................#......................................#...............................................
............................................................................................................................................
............................................................................................................................................
...................................#.................................................#.................................#....................
.............#..............................#....................................................#..............#.......................#...
...................................................#.........#..........#..................#................................................
#......#.............#.......#...........................................................................#..................................
.................................................................................#..................#...............#.......................
..........................................................#.................................................................................
.......................................................................................................................................#....
...........#....................................................#......................#...................#................................
.......................................#................................#...................................................................
...................#............................................................................................#........#..........#.......
............................................#....................................................#......#.....................#.............
........#........................#................#...............#.........................................................................
.............#.............#............................................................#............................#......................
...........................................................#................................................................................
........................................#......#.............................#...............................#..............................
................#...........................................................................................................................
.................................................................#................#.................#.......................................
......#.............#...........#.....................................................................................................#.....
.#.......................#...........#......#.............................................#.................................................
........................................................#.................#.................................................................
....................................................................................#.......................................................
...............#..................#.............................#............................#..............#...............#...............
.........................................#.......................................................................#..........................
..#........#........#..............................#........................................................................................
............................................................#.....................#.........................................................
....................................#........................................#...........#......#......#....................................
.......................#.......................#..................................................................................#.........
........#......#.........................................................................................................................#..
..........................................................#..............................................................#..................
...#........................#........................#................#......................#......................#.......................
................................................................#..........#...........#......................................#.............
..............................................#.....................................................#.................................#.....
......................................#.....................................................................................................
..........#......#...............................................................................................#..........................
...........................................#.................#...................#........................................#.................
........................................................................................#......#...........................................#
....................................................................#.......................................................................
.#.......................#................................#..............#.............................................#....................
................................................#...............................................................#...........................
.......................................#.............#.....................................................#...............#................
.............#................#...................................................................#................................#........
.............................................#..........................................#.................................................#.
.................#................................................................#.........................................................
.#..........................................................................................................................................
........................................................................................................................#...................
...........#..........#...........#..............#.......................................................#.......#....................#.....
....#................................................................#......................................................................
...........................................#.................#...............................................#..................#...........
.....................................#....................................#..........#..........#...........................................
.........................................................................................................................#..............#...
.................................................................................#.........#............#...................................
................#......#.............................#.............#........................................................................
#.......#....................#.........#....................................................................................................
.............................................#..............................#.....................................................#.........
...................#.................................................................#......................................................
....................................#................................................................#..............#.......................
...................................................#...........#................................#...........#..............................#
.........................................................................#.....#............................................................
..#.....................#......................#.................................................................#...........#..............
.........................................................................................#.........#........................................
.........#.......................................................................................................................#......#...
.............................#.....#............................#.....#..............#...............................#......................
.............#.............................................#.............................................#.................#................"""
}