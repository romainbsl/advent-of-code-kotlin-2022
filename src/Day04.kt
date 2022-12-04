fun main() {
    fun String.toRange(): Set<Int> {
        val (start, end) = split("-").map(String::toInt)
        return IntRange(start, end).toSet()
    }

    fun List<String>.toAssignments(): List<Pair<Set<Int>, Set<Int>>> = map {
        val (left, right) = it.split(",")
        left.toRange() to right.toRange()
    }

    fun part1(input: List<String>): Int {
        return input.toAssignments()
            .count { assignments ->
                val (first, second) = assignments
                first.all { it in second } || second.all { it in first }
            }
    }

    fun part2(input: List<String>): Int {
        return input.toAssignments()
            .count { assignments ->
                val (first, second) = assignments
                first.any { it in second } || second.any { it in first }
            }
    }

    val testInput = readInput("Day04")

    // Part 1
    println(part1(testInput))

    // Part 2
    println(part2(testInput))
}