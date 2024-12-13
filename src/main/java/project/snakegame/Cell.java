package project.snakegame;


/**
 * Представляет собой отдельную клетку на игровом поле.
 * <p>
 * Используется для хранения координат (X и Y) объекта на поле, например, змейки или яблока.
 */
public class Cell {

    /**
     * Переменная Х
     */
    private final int x;

    /**
     * Переменная У
     */
    private final int y;

    /**
     * Создает новый экземпляр клетки с заданными координатами.
     *
     * @param x Координата X клетки.
     * @param y Координата Y клетки.
     */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Возвращает координату X клетки.
     *
     * @return Координата X.
     */
    public int getX() {
        return x;
    }

    /**
     * Возвращает координату Y клетки.
     *
     * @return Координата Y.
     */
    public int getY() {
        return y;
    }

    /**
     * Сравнивает данный объект клетки с другим объектом на равенство.
     * <p>
     * Два объекта {@code Cell} считаются равными, если их координаты X и Y совпадают.
     *
     * @param o Объект для сравнения.
     * @return {@code true}, если объекты равны, {@code false} в противном случае.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cell cell = (Cell) o;
        return x == cell.x
                && y == cell.y;
    }
}
