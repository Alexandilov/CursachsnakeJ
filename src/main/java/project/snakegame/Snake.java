package project.snakegame;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Представляет змейку в игре.
 * <p>
 * Управляет положением змейки на игровом поле, ее направлением движения,
 * а также отслеживает, жива ли змейка.
 */
public class Snake {

    /**
     * Переменная для логирования
     */
    private static final Logger logger = LogManager.getLogger(Snake.class);

    /**
     * Список клеток, составляющих тело змейки.
     * Первая клетка в списке - голова, последняя - хвост.
     */
    private List<Cell> body = new ArrayList<>();

    /**
     * Клетка, представляющая текущее положение яблока.
     */
    private Cell apple;

    /**
     * Текущее направление движения змейки.
     */
    private Direction direction = Direction.RIGHT;

    /**
     * Флаг, указывающий, жива ли змейка.
     */
    private boolean isAlive = true;

    /**
     * Создает новый экземпляр змейки с заданным начальным положением тела.
     *
     * @param cells Список {@link Cell}, представляющих начальные клетки тела змейки.
     */
    public Snake(List<Cell> cells) {
        body.addAll(cells);
    }

    /**
     * Устанавливает положение яблока.
     *
     * @param cell {@link Cell}, представляющая положение яблока.
     */
    void apple(Cell cell) {
        apple = cell;
    }

    /**
     * Изменяет направление движения змейки.
     * <p>
     * Игнорирует попытки установить противоположное текущему направление.
     *
     * @param direction Новое направление движения {@link Direction}.
     */
    void directionTo(Direction direction) {
        if (this.direction==Direction.RIGHT && direction==Direction.LEFT) {
            this.direction = Direction.RIGHT;

        } else if (this.direction==Direction.LEFT && direction==Direction.RIGHT) {
            this.direction = Direction.LEFT;

        } else if (this.direction==Direction.UP && direction==Direction.DOWN) {
            this.direction = Direction.UP;

        } else if (this.direction==Direction.DOWN && direction==Direction.UP) {
            this.direction = Direction.DOWN;

        } else {
            this.direction = direction;
            logger.info("changing the direction of the snake");
        }
    }

    /**
     * Выполняет один шаг движения змейки.
     * <p>
     * Определяет следующую позицию головы змейки в зависимости от текущего направления.
     * Проверяет столкновение с собственным телом.
     * Если змейка съедает яблоко, ее длина увеличивается.
     *
     * @return {@code true}, если змейка съела яблоко на этом шаге, {@code false} в противном случае.
     */
    boolean step() {
        var tail = body.get(body.size() - 1);
        var next = switch (direction) {
            case RIGHT -> new Cell(
                    (tail.getX() + 1) % Main.getSize(), tail.getY()
            );
            case LEFT -> new Cell(
                    (tail.getX() - 1 + Main.getSize()) % Main.getSize(), tail.getY()
            );
            case UP -> new Cell(
                    tail.getX(), (tail.getY() - 1 + Main.getSize()) % Main.getSize()
            );
            case DOWN -> new Cell(
                    tail.getX(), (tail.getY() + 1) % Main.getSize()
            );
        };


        if (body.contains(next)) {
            logger.info("the snake crashed into itself. The game is over");
            isAlive = false;
            return false;
        }

        var eat = next.equals(apple);
        if (!eat) {
            body.remove(0);
        }
        body.add(next);
        return eat;
    }

    /**
     * Возвращает список клеток, составляющих тело змейки.
     *
     * @return {@link List<Cell>} - список клеток тела змейки.
     */
    List<Cell> asCells() {
        return body;
    }

    /**
     * Возвращает текущий статус змейки (жива или нет).
     *
     * @return {@code true}, если змейка жива, {@code false} в противном случае.
     */
    public boolean isAlive() {
        return this.isAlive;
    }
}
