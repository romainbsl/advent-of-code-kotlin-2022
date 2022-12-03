fun main() {
    fun part1(input: List<String>): Int = input.sumOf { Rucksack.fromBatch(it).score }

    fun part2(input: List<String>): Int = input.chunked(3).sumOf { chunkOfThree: List<String> ->
        val rucksackBatch = chunkOfThree.map { rucksack -> rucksack.map { Item.fromChar(it) }.toSet() }
        rucksackBatch[0]
            .intersect(rucksackBatch[1])
            .intersect(rucksackBatch[2])
            .sumOf { it.value }
    }

    val testInput = readInput("Day03")

    // Part 1
    println(part1(testInput))

    // Part 2
    println(part2(testInput))
}

data class Rucksack(
    val left: Compartment,
    val right: Compartment,
) {
    val score: Int get() = left.items.intersect(right.items.toSet()).sumOf { it.value }

    companion object {
        fun fromBatch(batch: String): Rucksack {
            val chunkSize = batch.length / 2
            return Rucksack(
                left = Compartment.fromBatch(batch.take(chunkSize)),
                right = Compartment.fromBatch(batch.drop(chunkSize))
            )
        }
    }
}

data class Compartment(val items: List<Item>) {
    companion object {
        fun fromBatch(batch: String): Compartment = Compartment(batch.map { Item.fromChar(it) })
    }
}

@JvmInline
value class Item(val value: Int) {
    companion object {
        private const val a = 'a'
        private const val A = 'A'

        fun fromChar(c: Char): Item = when (c.isLowerCase()) {
            true -> Item(c - a + 1)
            false -> Item(c - A + 27)
        }
    }
}
