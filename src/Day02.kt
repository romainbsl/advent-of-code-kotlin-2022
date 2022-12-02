fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map {
                val (left, right) = it.split(" ")
                Shape.from(left) to Shape.from(right)
            }
            .fold(0) { score, round ->
                val (elf, me) = round
                val outcome = me fight elf

                score + outcome.points + me.points
            }
    }

    fun part2(input: List<String>): Int {
        return input
            .map {
                val (left, right) = it.split(" ")
                Shape.from(left) to Outcome.from(right)
            }
            .fold(0) { score, round ->
                val (elfMove, outcome) = round
                val necessaryMove = outcome against elfMove
                score + outcome.points + necessaryMove.points
            }
    }

    val testInput = readInput("Day02")
    
    // Part 1
    assert(Outcome.Draw == Shape.Rock fight Shape.Rock)
    assert(Outcome.Lost == Shape.Rock fight Shape.Paper)
    assert(Outcome.Won == Shape.Rock fight Shape.Scissors)

    assert(Outcome.Won == Shape.Paper fight Shape.Rock)
    assert(Outcome.Draw == Shape.Paper fight Shape.Paper)
    assert(Outcome.Lost == Shape.Paper fight Shape.Scissors)

    assert(Outcome.Lost == Shape.Scissors fight Shape.Rock)
    assert(Outcome.Won == Shape.Scissors fight Shape.Paper)
    assert(Outcome.Draw == Shape.Scissors fight Shape.Scissors)

    println(part1(testInput))

    // Part 2
    assert(Shape.Paper == Outcome.Won against Shape.Rock)
    assert(Shape.Rock == Outcome.Won against Shape.Scissors)
    assert(Shape.Scissors == Outcome.Won against Shape.Paper)

    assert(Shape.Scissors == Outcome.Lost against Shape.Rock)
    assert(Shape.Paper == Outcome.Lost against Shape.Scissors)
    assert(Shape.Rock == Outcome.Lost against Shape.Paper)

    assert(Shape.Rock == Outcome.Draw against Shape.Rock)
    assert(Shape.Scissors == Outcome.Draw against Shape.Scissors)
    assert(Shape.Paper == Outcome.Draw against Shape.Paper)

    println(part2(testInput))
}

enum class Shape(val points: Int) {
    Rock(1),
    Paper(2),
    Scissors(3);

    val defeat: Shape
        get() = when (this) {
            Rock -> Scissors
            Paper -> Rock
            Scissors -> Paper
        }

    companion object {
        fun from(key: String): Shape = when (key) {
            "A", "X" -> Rock
            "B", "Y" -> Paper
            "C", "Z" -> Scissors
            else -> error("Unknown move!")
        }
    }
}

infix fun Shape.fight(other: Shape): Outcome = when {
    this == other -> Outcome.Draw
    defeat == other -> Outcome.Won
    else -> Outcome.Lost
}

infix fun Outcome.against(elfMove: Shape) = when (this) {
    Outcome.Lost -> elfMove.defeat
    Outcome.Draw -> elfMove
    Outcome.Won -> elfMove.defeat.defeat
}

enum class Outcome(val points: Int) {
    Lost(0), Draw(3), Won(6);

    companion object {
        fun from(key: String): Outcome = when (key) {
            "X" -> Lost
            "Y" -> Draw
            "Z" -> Won
            else -> error("Unknown outcome!")
        }
    }
}