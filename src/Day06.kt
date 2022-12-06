fun main() {

    fun findMarkerIndex(raw: String, numberOfUniqueChar: Int): Int {
        return raw.windowed(numberOfUniqueChar).indexOfFirst {
            it.toSet().size == numberOfUniqueChar
        } + numberOfUniqueChar
    }

    fun part1(raw: String): Int = findMarkerIndex(raw, 4)
    fun part2(raw: String): Int = findMarkerIndex(raw, 14)

    val testInput = readInput("Day06_test")
    // Part 1
    check(part1(testInput[0]) == 7)
    check(part1(testInput[1]) == 5)
    check(part1(testInput[2]) == 6)
    check(part1(testInput[3]) == 10)
    check(part1(testInput[4]) == 11)

    // Part 2
    check(part2(testInput[0]) == 19)
    check(part2(testInput[1]) == 23)
    check(part2(testInput[2]) == 23)
    check(part2(testInput[3]) == 29)
    check(part2(testInput[4]) == 26)

    val input = readInput("Day06")

    check(part1(input.first()) == 1623)
    check(part2(input.first()) == 3774)
}