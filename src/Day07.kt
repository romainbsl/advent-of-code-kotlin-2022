fun main() {
    fun initFileSystem(input: List<String>): Directory {
        val root = Directory("/", null)
        var dir: Directory = root

        input.drop(1).forEach { line ->
            when (line.first()) {
                '$' -> {
                    val command = line.split(" ")
                    when (command[1]) {
                        "cd" -> dir = when (command[2]) {
                            ".." -> dir.parent!!
                            else -> dir.findDirectory(command[2])
                        }

                        else -> { /* ignore */
                        }
                    }
                }

                else -> {
                    val (left, right) = line.split(" ")
                    when (left) {
                        "dir" -> {
                            val newDir = Directory(right, dir)
                            dir.addChild(newDir)
                        }

                        else -> dir.size += left.toLong()
                    }
                }
            }
        }

        return root
    }

    fun part1(input: List<String>): Long {
        val root = initFileSystem(input)

        return root
            .flattenChildren()
            .filter { it.totalSize <= 100_000 }
            .sumOf(Directory::totalSize)
    }

    fun part2(input: List<String>): Long {
        val root = initFileSystem(input)

        val unused = 70_000_000L - root.totalSize
        val missing = 30_000_000L - unused

        return root
            .flattenChildren()
            .filter { it.totalSize >= missing }
            .minOf(Directory::totalSize)
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95_437L)
    check(part2(testInput) == 24933642L)

    val input = readInput("Day07")
    check(part1(input) == 1_243_729L)
    check(part2(input) == 4_443_914L)
}

private fun Directory.flattenChildren(): List<Directory> {
    return children + children.flatMap { it.flattenChildren() }
}

data class Directory(
    val name: String,
    val parent: Directory?,
) {
    val children: MutableList<Directory> = mutableListOf()

    val totalSize: Long
        get() = size + children.sumOf { it.totalSize }
    var size: Long = 0L

    fun addChild(directory: Directory) {
        children.add(directory)
    }

    fun reset() = children.clear()

    fun findDirectory(name: String): Directory {
        return children
            .firstOrNull { it.name == name }
            ?: error("NOT FOUND!")
    }
}

fun String.isChangeDirCommand(): Boolean = startsWith("$ cd")
fun String.isListCommand(): Boolean = startsWith("$ ls")


























