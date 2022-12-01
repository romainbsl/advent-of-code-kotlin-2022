fun main() {
    fun part1(input: String): Elf {
        return input.split("\n\n")
            .map { foods ->
                Elf(foods.split("\n").map { Food(it.toInt()) })
            }
            .maxBy { elf -> elf.foods.sumOf { it.calories } }
    }

    fun part2(input: String): List<Elf> {
        return input.split("\n\n")
            .map { foods ->
                Elf(foods.split("\n").map { Food(it.toInt()) })
            }
            .sortedByDescending { it.foods.sumOf { it.calories } }
            .take(3)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsText("Day01")
    println(part1(testInput).totalCalories)
    println(part2(testInput).sumOf(Elf::totalCalories))
}


@JvmInline
value class Food(val calories: Int)

data class Elf(val foods: List<Food>) {
    val totalCalories: Int get() = foods.sumOf { it.calories }
}