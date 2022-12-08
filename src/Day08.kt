fun main() {

    fun initForest(input: List<String>): Forest {
        val width = input.first().count()
        val height = input.count()

        val grid = input.foldIndexed(Array(height) { Tree.emptyArray(width) }) { rowId, forest, row ->
            forest.apply {
                this[rowId] = row.foldIndexed(Tree.emptyArray(width)) { colId, treeLine, tree ->
                    treeLine.apply {
                        this[colId] = Tree(tree.digitToInt(), colId == 1 || rowId == 1)
                    }
                }
            }
        }

        return Forest(width, height, grid)
    }

    fun part1(input: List<String>): Int {
        val (width, height, grid) = initForest(input)

        return grid.mapIndexed { rowIndex, row ->
            row.mapIndexed { colIndex, tree ->
                val colValues = grid.map { it[colIndex] }
                val isOnEdge = rowIndex == 0 ||
                        colIndex == 0 ||
                        rowIndex == height - 1 ||
                        colIndex == width - 1
                val isVisibleFromTop = colValues.take(rowIndex).all { it < tree }
                val isVisibleFromRight = row.drop(colIndex + 1).all { it < tree }
                val isVisibleFromBottom = colValues.drop(rowIndex + 1).all { it < tree }
                val isVisibleFromLeft = row.take(colIndex).all { it < tree }

                tree.copy(
                    isVisible = isOnEdge ||
                            isVisibleFromTop ||
                            isVisibleFromRight ||
                            isVisibleFromBottom ||
                            isVisibleFromLeft
                )
            }
        }.sumOf { row ->
            row.count { it.isVisible }
        }
    }

    fun part2(input: List<String>): Int {
        val (_, _, grid) = initForest(input)

        return grid.mapIndexed { rowIndex, row ->
            row.mapIndexed { colIndex, tree ->
                val colValues = grid.map { it[colIndex] }

                fun List<Tree>.takeVisible() = takeUntil { it >= tree }

                val topVisibleTrees = colValues
                    .take(rowIndex).reversed().takeVisible()
                val rightVisibleTrees = row
                    .drop(colIndex + 1).takeVisible()
                val bottomVisibleTrees = colValues
                    .drop(rowIndex + 1).takeVisible()
                val leftVisibleTrees = row
                    .take(colIndex).reversed().takeVisible()

                topVisibleTrees.size *
                        rightVisibleTrees.size *
                        bottomVisibleTrees.size *
                        leftVisibleTrees.size
            }
        }.flatten().max()
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    check(part1(input) == 1779)
    check(part2(input) == 172224)
}

data class Forest(val width: Int, val height: Int, val grid: Array<Array<Tree>>)

data class Tree(val height: Int, val isVisible: Boolean = false) : Comparable<Tree> {
    override fun compareTo(other: Tree) = this.height.compareTo(other.height)

    companion object {
        fun emptyArray(size: Int) = Array<Tree>(size) { Tree(-1) }
    }
}
