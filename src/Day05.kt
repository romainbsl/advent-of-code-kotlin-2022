fun main() {
    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>): String {
    val (stacks, moves) = parse(input)

    moves.forEach { move ->
        repeat(move.count) {
            stacks[move.to]?.addLast(
                stacks[move.from]?.removeLast() ?: return@repeat
            )
        }
    }

    return stacks.values.map { it.last() }.joinToString("") { it.mark }
}

fun part2(input: List<String>): String {
    val (stacks, moves) = parse(input)

    moves.forEach { move ->
            val cratesToMove = buildList<Crate> {
                repeat(move.count) {
                    stacks[move.from]?.removeLast()?.let { it1 -> add(it1) }
                }
            }

            stacks[move.to]?.addAll(cratesToMove.reversed())
        }

    return stacks.values.map { it.last() }.joinToString("") { it.mark }
}

@JvmInline
value class Crate(val mark: String) {
    companion object {
        fun fromMark(charSequence: CharSequence) = Crate(charSequence.toString())
    }
}

data class Move(val count: Int, val from: Int, val to: Int) {
    companion object {
        fun fromInput(line: String): Move {
            val (count, from, to) = line
                .remove("move ", "from ", " to")
                .split(" ")
                .map { it.toInt() }

            return Move(count, from, to)
        }
    }
}

fun parse(input: List<String>): Pair<MutableMap<Int, ArrayDeque<Crate>>, List<Move>> {
    val stacks = mutableMapOf<Int, ArrayDeque<Crate>>().apply {
        input
            .subList(0, input.indexOf(""))
            .reversed()
            .drop(1)
            .forEach { line ->
                line
                    .chunked(4) { it.trim().removePrefix("[").removeSuffix("]") }
                    .forEachIndexed { index, mark ->
                        if (mark.isEmpty()) return@forEachIndexed
                        val key = index + 1
                        val stack = getOrPut(key) { ArrayDeque() }.also {
                            it.addLast(Crate.fromMark(mark))
                        }
                        set(key, stack)
                    }
            }
    }

    val moves = input
        .subList(input.indexOf("") + 1, input.lastIndex + 1)
        .map { Move.fromInput(it) }

    return stacks to moves
}