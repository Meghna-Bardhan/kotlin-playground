package board

import board.Direction.*

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl<T>(width)

open class SquareBoardImpl(override val width: Int) : SquareBoard {
    protected val listOfCells: ArrayList<Cell> = ArrayList()

    init {
        println("SquareBoardImpl $width")
        for (i in 1..width)
            for (j in 1..width)
                listOfCells.add(Cell(i, j))

        println("listOfCells $listOfCells")
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        return listOfCells.find { cell -> (cell.i == i) && (cell.j == j) }
    }

    override fun getCell(i: Int, j: Int): Cell {
        println("getCell: ($i, $j)")
        return listOfCells.find { cell -> (cell.i == i) && (cell.j == j) } ?: throw IllegalArgumentException()
    }

    override fun getAllCells(): Collection<Cell> {
        return listOfCells
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        val rowsForRange = mutableListOf<Cell>()
        jRange.iterator().forEach { rangeValue ->
            val cellRow = getCellOrNull(i, rangeValue)
            if (cellRow != null) rowsForRange.add(cellRow)
        }
        return rowsForRange
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        val columnsForRange = mutableListOf<Cell>()
        iRange.iterator().forEach { rangeValue ->
            val cellRow = getCellOrNull(rangeValue, j)
            if (cellRow != null) columnsForRange.add(cellRow)
        }
        return columnsForRange
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        return when (direction) {
            Direction.UP -> getCellOrNull(i - 1, j)
            Direction.DOWN -> getCellOrNull(i + 1, j)
            Direction.LEFT -> getCellOrNull(i, j - 1)
            Direction.RIGHT -> getCellOrNull(i, j + 1)
        }
    }
}

class GameBoardImpl<T>(width: Int) : SquareBoardImpl(width), GameBoard<T> {
    private val listOfCellValues = mutableMapOf<Cell, T?>()

    init {
        println("GameBoardImpl")
        listOfCells.forEach { cell -> listOfCellValues[cell] = null }
        println("listOfCells $listOfCells")
        println("listOfCellValues $listOfCellValues")
    }

    override fun get(cell: Cell): T? {
        return listOfCellValues.getOrDefault(cell, null)
    }

    override fun set(cell: Cell, value: T?) {
        listOfCellValues[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return listOfCellValues.filterValues(predicate).keys
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return listOfCellValues.filterValues(predicate).keys.toList().getOrNull(0)
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        return listOfCellValues.values.any(predicate)
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return listOfCellValues.values.all(predicate)
    }
}
