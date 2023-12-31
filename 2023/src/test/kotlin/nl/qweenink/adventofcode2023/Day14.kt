package nl.qweenink.adventofcode2023

import org.junit.jupiter.api.Test

class Day14 {

    @Test
    fun puzzle1() {
        val lines = data.lines()
        val rows = (0..<lines[0].length).map { rowIndex ->
            lines.map { it[rowIndex] }
        }
        var sum = 0
        val newRows = rows.map { row ->
            val newRow = MutableList(row.size) { '.' }
            var stopPosition = 0
            row.forEachIndexed { index, char ->
                when (char) {
                    'O' -> {
                        newRow[stopPosition] = 'O'
                        sum += row.size - stopPosition
                        stopPosition++

                    }
                    '#' -> {
                        newRow[index] = '#'
                        stopPosition = index + 1
                    }
                }
            }
            newRow
        }

        newRows.forEach { println(it) }
        println(sum)
    }

    @Test
    fun puzzle2() {
        val lines = data.lines()
        val rows = (0..<lines[0].length).map { rowIndex ->
            lines.map { it[rowIndex] }
        }

        var newRows = rows
        val progression = ArrayList<String>()
        var startIndex = -1
        while (startIndex == -1) {
            newRows = cycle(newRows)
            startIndex = progression.indexOf(getKey(newRows))
            progression.add(getKey(newRows))
        }
        repeat((999999999 - startIndex) % (progression.lastIndex - startIndex)) {
            newRows = cycle(newRows)
        }

        println(eval(newRows))
    }

    private fun getKey(rows: List<List<Char>>) =
            rows.joinToString(separator = "") { it.joinToString(separator = "") }

    private fun cycle(rows: List<List<Char>>) =
            rotate(slide(rotate(slide(rotate(slide(rotate(slide(rows))))))))

    private fun rotate(newRows: List<List<Char>>) =
            (0..<newRows[0].size).reversed().map { rowIndex ->
                newRows.map { it[rowIndex] }
            }

    private fun slide(rows: List<List<Char>>): List<MutableList<Char>> {
        val newRows = rows.map { row ->
            val newRow = MutableList(row.size) { '.' }
            var stopPosition = 0
            row.forEachIndexed { index, char ->
                when (char) {
                    'O' -> {
                        newRow[stopPosition] = 'O'
                        stopPosition++

                    }
                    '#' -> {
                        newRow[index] = '#'
                        stopPosition = index + 1
                    }
                }
            }
            newRow
        }
        return newRows
    }

    private fun eval(rows: List<List<Char>>): Int {
        var sum = 0
        rows.forEach { row ->
            var stopPosition = 0
            row.forEachIndexed { index, char ->
                when (char) {
                    'O' -> {
                        sum += row.size - index
                        stopPosition++
                    }
                }
            }
        }
        return sum
    }

    val data2 = """O....#....
O.OO#....#
.....##...
OO.#O....O
.O.....O#.
O.#..O.#.#
..O..#O..O
.......O..
#....###..
#OO..#...."""

    val data = """......O........#....O..O.O#O.O.##.O#.O##.O#......O........O....O.#..OO#.OO.#..#.........#.O..#O..#.O
O#..O...O....O.#...##.O....O..O.#...#...O..O#.#.#......#.O##O#....#..O...O..#.#......#.#O..#..#....#
O....##.O#O##.O...#...#.#.......OO.##..###.OO..O.#.#.OO#O..##..O.O.#...#..O.#....O.##.O#OO..........
....O#.O..OO.#..OOOO#.......O.....O.#O......OOO#....O.....O......O.....#..OOO.....O...O.O##OO.....##
OO#..#.O.##..##..#........O..OO.......#..O.OO.OO..#O...OO.##...#.OOO...O.O.O..#.O..#..O.O.##.O...OOO
#.....#OO.O......O..####O...O.....O.......#..#OO#..#...O...OOO.#....O#.....O...........#O#OO...O..O.
#O.O......O...O.O...O##.O#..OO....O..##.OO#O.##O.#.#.......O.O#.....OOOO......#..OO###OO.......O..#.
#.O.OO...#...##.O..OO..#.......O.O.O.O......O#....##.OOO.O.....#O#..##...OO.......#OOO.O..O......##.
..O.OO.O.#O.O........#..##.#.OO.O..##.O...O.#.....#..O.#....O..#O..O.#OO......O........#.#....#O..#.
..O......OO.O............OO#....##....#......O.#..O..O...O.O..#O......O.OO#.#.......O....#....##.O.#
.....#.O#O.#..#.#.##....#O....O.O.O.OO......#..O.O..#..OOOO....#.#..O.#.O.O..O.O#.O...O#..#..O##.O..
O..O#.OO.#.##.#.........OO.#.....#OO...........O....O..OO..O.O###..O.O.#O...O.O...#...O..O#O...OO...
.#.#...OO...#.O...O.........#OO..#.....O....##.#.OO.#.#.#OOO...#OOO.O.O..O#......#.##..........O....
....#..#...#...#.......#..#......##..O........O.OO.#O..#O.#.......#..#..O.O#.#...O#..O...O#.O...O.#O
.#O#O.O..........#O##.O#.#.........##.#............#.#.....#..#OO...#O....#OO.O.OO.#..#...O#........
.#..#.O#O...##O....O.....##...OOO#...#O..#..O##O..#..OO.#...#O.....#.O...O#.O#.OO...###.#..O#O..OO..
...#...O....O.#....#..#.O#OO...O.O#.#....O..#OO.#.O....#O#...O...O..O.##O..#.......O.##....O##....O.
#...O...O...O#O##O.O.#.#.....#.O...#.....##..#.#OO....OO#.......#..O..O.#.#O...#.#.#.....OO.O...OO..
.O...OO.O.....##.O.##.#O.....#O..O.###..#..##.O.##O....OO..#O#....O.....O...#O#O...OO.........O..##O
.O..OO.#O.#...O...O...#...O....#.O.O.O....#O.###O.#..#..##OO.O...O.#.#.#..O...#.....O.#.O.O.#..O....
O...OO.#...O.#..#.O..O#..O#....##.#O.##...#......#O#O..O..#.##.....O...#O#..O.O...OO...OO..#.OO.O.OO
.O.O....O.O.##O#....##..##O......#.#O#..O..#...OO..O...O.#..#O#..#..#.O...#.O.OO....O#..O##.O..O..O.
....#..O..O.OO.#.O...#OOOOO.OO.O#..O....#.#O.##........#OO.#.#.O.#.OO#...#OO..#.OO...O..#.O.....O#.#
.#O...O......#OOO#..#..#....O...O..O.#O....#O#...O..#...OO.#.O.O...O..#..#..O..##.O##.....O.O..OO..#
..#O.##.#.......OOOO.OO.....O..O........##.O.OO....O###..#.O...#O.O.#OO.#....O.O.#..........#.OO....
.##...##.#......O.#.OO#...#..O##..#..##.O.......O#..O.O.#.O..#.....#..O#....O....OO.OO.O..#...O#..O.
..##O##O......O.#O.O.....#..#.#O#.O..#O#O..#OO.#..#.#.....##O.................O.....O#OO...##...O..O
....#O#O..O.#..O....#.....#.....#......O.#O........O#..O.O#..OO...#.#.O..##....O.....#......O#....#.
#.....#...O....OO..O##.OO....O.#..#O.........O..............O....##OO##.....#.##.O...#..O..#O#OO...O
...O.#O#...O..OO#...O.#O#.......#..O...O#.#..#O...#.O.O....O##...##...#.OO...O.O..OO#.#..........##.
.......#OO....#.#O#..........O.##.O.O#.....O.OO.O.OO##...##O...OO.O..O#.OO...O......O#O..O.O.##...OO
..O.O#.#.....OO............O.O..#.O#.O.O.#.##.#O.O...#.O.....O..#.#....O..#O#O....O.O.#..OOO..O..O..
..O.#.O.............#OO#.#O....#O#...OO..O##...#.....O...............#..#..O..O#....O..##...O..O.O#.
...........O.........##O#.#.O.O..O##........#..#....#O..##O..##.OO...O#.##..#..#.#O....O.O.#........
...............OOO.#........O....#.#O..##.O...O.O#.......#.#O.#...#O.......###.......O.OO...O.O..O.#
.#.O#....#......O...OO##..O#O...O.O#.O.##.OO...OOOO....OO....#.#....##.###.O.O..O..##....#..........
..OO.....OO#O.........O.#..O....#......#.#.#.#...##O.OO#.O......O..O.#O....O#...OOO....O..O.O..#..#O
#O...O.O.......##..#..O..O...O...O..O.#..#.#.O.OO............OO....#OOOO#...OO.#.O#OO....O##.O....#.
.O#..OOO.O.#.......O....#......O.OO.#......#..#.O.......###.#....#O..O....O#.O.....#O#....O#.#.....#
....OOO#.#..#.O..O....#...O.#...OO....#..OO.O#O..O....O.O...O...O#O..O..O......O.#O..O#O..........O#
..O..#.OO###....#..O....#.O..#.....O.O.#..OOO.O...##...O#..##..O#.O.......O.#.###...O.....O......O..
..O##..OO..O.O...O.#....OO.OOO.O........O......#.O..O..........O...O#OOO..O....O.#..O##O.......#....
O.O..OO##.#...#O#..#...O..#.......#O..###..OOO..O..#.O#.O..#....O.OO#.##..##O.......OO#OO..#.O....#O
...#..O.#........OO.O....OOO#.O..O#O.......O....O........#O#...#O#.#..OO..O....#O.O....O...O###.#...
O..#.......#..#.....O..OOO.#..#OO...#O.O...O.....##O.O.#O....#.....#...#.........OO.O....#...O#O#...
.O..O.#O....O#O.###....#O.O.....................###......O..#...O.OO.....#.......O..#...OO.......##.
............#O#..#.#.......#.##....#O.#....O.#...O...OOO.O.....#O..####O...O..#..#.O##....#OO##.#.#.
....O.#..#.....#...#OO#.##...O..OO....#....O#..O......OO.OO.#OO....#.O..#.....#.##..#.O...O.O#.##O..
...#OO#.O.O......#...O.O.....#......#.......................#.#.....O..O.#OO.#..OOO.OO.O.......#..#.
O#..#OO.OO.O#....##.O.#....OO..#.#.....O...O...#.O...O...O..#..O.....O..OO..O...O...O#....#...O.O...
.......OO.#O..#O#OO.#.............#....O.O#..#..#.....#..O.O#..#..##O.O.O...O.O.#O..OO..#O...OO...#.
#..#O.O.O.....O..O#.#............##O#....O.O#...#...............#OO..O.....#...OOO#.......OO...#....
..#...O...OO..#...O#O.O.O.O....O..#O#O......#.#....#..OO....#O...OO.O..O#.#O#OO.#...O.O.O..#....##.O
..#..O#..##.O...O..O....O.O.#.....#.O.OO..#.#.#...OO#....#..#O........OO.OO#.#..#..##O...O.OO.O#.#..
...#O..OO.O#OO..O#O#O....#O....#.O#.OO.##.......O.O.OO##.......#.O.O.#O.##.......O..O....##.#O..#..#
#....##...O.......#..#OO.........#O.O.O.O#...O......O.......OO##....O....#.#O.#.#.....O.OO..#...O.O.
.O..O#.....O.#..O..#..#O..O.O...O..O.#....#.....#.#.O...O..#.O.O.O....O....O.O..O#O.....O.O....O...O
.......O...O.O....O.O.OO...##..OOOOOO.O...O.##.O...#...O.O#O...#.O.O.O.........O..#..O.....##.#....#
...O..........#.O...O.O.OOO...##.....#OO.......O....#.....O.......#.#O.........O.##O#..##...O.O.#.O.
..OO#O.....O.##......O..#...#..OO.....#O#.O..#.O..#.O.#......O.#O.#..O.#.#.O..#.O......#.....O..#.O.
..O#...O..O.O.OO....#..O.......#..#.O.#....O..OO#.OO.O..#.#O..O.#.......O...O#.OO.......O#........O#
...OO....#..O...O.#....#.#OOO...O.#.#.#.O....OO...OO...#......#O..#..O.#O....#O...#..O..OO#....OO..O
.O.OO##....OO.#...O.......O...O.O.O.O.O.....O.O#..O#O......##O..#..###.O....O...O..#O.##O#....#..##.
.#..O.O....O#O..OO#........#O#.OOO...##.....O#..#.#..O#.OOO.O...#.....O#........O#O......##...O.....
..O#O.....O.#O...O...#.O.........#OO..#O.O.##..##O..OO....O...O.#.#...#..O...O#.O...O......#OO...#.#
O.........##.....###O.O.#.O##..##.O.#.O.OO......OOO...O#.O#..OO..........##.##O.O#O.O...##.O....O..O
O..##OO.OO.......#......O.#..#.....O...O#.O#..#...OO...#.O...#O.#.O..O..OOO#....O..O...#....O...#.O.
..#.....#OOO#..O..O.....O........#...O..OO.O.....OO....O.###.....#O.#..O..#...#.###O..O#...O.#...O..
O.O..#O..###.#....O#......###..#....O..OOO..O..O..OO..OO#O.....O..#..O##.#.#..OO..##..#.....O....O..
..O......O..#.......##..#O..OO.O.O....#..OOO..OO.#..#..OO.O...O.....#.OO#OOO.O.##.....O...O##.#...O.
O.OO.#O..#.#...#...##...O.#O....O##O...O##.OO#.O.###.#OO..OO#O......OOO..#OOOO...OO....#.O#.........
.#O#O#O.O.O....O.O.O...###O.O#.#OO#.O......#O#.O#O#........O...#OOO..OOO.........O...#O#...O......#.
.OO.O.#..#.#..O.....OO.O.O#.....O...O..O.#.OO.O.....#.....O.......O.OO......O...OO..O..O....#..O.#.O
.#...O#..O..#.#O...#O...O..O.....#.#.O#.OO#.#..#.....OO.#....O.#OO.....OO.O.O..O.O....#......O..OO..
...OO.##.O...#.#....##O.O..#.............#.#...#.#.#.OOO.##.......O#.O.O...OO....O..#....#.....O...O
.O...OO............#....O.#OO.#....#O....O###.#.....O.O.......#.O....#O...O...O.O.#..OO....#....#...
#.#......#O.....O..#.O.OO.........O...##....O....#..##OO..##O#..O.O......O....#.O..O......#..##....O
OOO....#.#.O.#.O#..#.....#O.O.O..O.OO.......#O.#......O##.O.#.#...#.#..OO#O.O...O..O..O.O.....#..#O.
##..O##..O...#.#..#..............#O..#O.O.O..#..O....O...#..#OO...O##....O...O...#O....OO...#.O..O..
.#...OO#.#O.......##..#O...O.#..#......O#O....#..OO..OO.#O..O...O.#.O.........O........#.##.##...O.#
...O.O..O...#O..O.O..OO.OO##O.O.O.#..#...O..##...O##O#O..#.#.O#.#O#..#..OO..O....O..OOOO..##..O.....
##........#............#....O.....O.#.O.#.O.O.#O...#O......O.#....O...#O#...OO#.#O##........O.......
OOO#O.OO..#.......OO.OO...O...##.O.##..O....OO...O.O.............##O......#.#..O..#O..##..#....#...O
.#O.......#O...O..#O......O#OO.O.O#.O......#O#......#..###.O.#.#..O#.....##.O.O...O..O.O.#..OO#.#.#O
.....O.O...O.#.....OO###..##.O.....O....#...O.O...........#O#..#O..#....#.O.......###...#..O.#....#O
#O.OO.........O#..#O.....O....O#......#....O..OOO..O.#.OO#...O..O##....##O..#..O#..O......O.OOO.O.OO
.#...O.#.#O....O..OO....#O..#...#O........#....O...O.....O..O..O.##.......O#...#..#O..#.#...O..O.O.O
......O..O...#....O....#OO#....#.#..##OO.....O.O.#......OO.O#..........#..O..#.O##.........###..O...
.O.O#..#O.#..#....O......O..OO.#O.O....O.#......#O.O.....O..OO#..O#.O.#....#...O.O...#O..#......##.O
......O.....O.#...#....#....O#O.##...O.......O.#.##.###O#.#........O.#O.O..#...#...#.O.....O.O.O#...
#.#O#.#..#.#.......#..#..#.....##O#.......#.#..##OO.........O.O....#O...O....O....#..##..#OO..O...OO
.O..#.O.O.......O....#.O........O...#O.#.....O#....#.......O.O...O...O#..##....O...#.OOO...O.#O##...
.......#..O..O...O....O#...O#....#O..O....##..#....##O....OO..#......#....O..#O.....O.O#OO..........
..O#......O.O......#.....#O.#.#.#.O...#OO.....O.O#.....O#.O.#.....OO.O.O.O....O..OO..##.#.....OO#..O
#O...O.#..#...OOO#...O......O#..O..O....###....OO..O#.#O..##.O#.O##....#.#.O#.##.....O.............O
.O.##.........#O....#..O#.O.#...O......O#.O#.OO#...O.O..........##.##O....O.#O#O..O.#...OO.O#.O.O...
#OO.O.OO.....O#....O.OO..###...O.....#..O##...O..###.O...#.O..#.O...O...OOO.#.O.#..O...O#..OO.#O...O
.###..#.OO#O..O.#..#.O..O...#....#.#..#O.O....#O....O#..#......#...#..O.....O.O.#O...O..O.....O#..#.
..###....OOOO......#O....O.....O.O.O..O.#O..##...#O#..#..#....#.O..#..O.......O...#.O#.#..#...O....#
......#O.#.......O..OO#O#....#.....OO#..#.OO..O.#....#...#O....#...O....O#.......O..#.O.#...#.#.O..."""
}
