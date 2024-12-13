package project.snakegame;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестовый класс для проверки функциональности класса {@link Snake}.
 */
public class SnakeTest {

    /**
     * Проверяет корректность инициализации тела змейки через конструктор.
     */
    @Test
    void initialBody() {
        Snake snake = new Snake(List.of(new Cell(0,0), new Cell(1,0)));
        assertEquals(2, snake.asCells().size());
    }

    /**
     * Проверяет возможность изменения направления движения змейки с помощью метода {@link Snake#directionTo(Direction)}.
     * Фактически проверяет, что вызовы метода не приводят к ошибкам.
     * Более детальная проверка изменения направления требует анализа поведения змейки в методе {@link Snake#step()}.
     */
    @Test
    void directionSnake() {
        Snake snake = new Snake(List.of(new Cell(1,1)));
        snake.directionTo(Direction.DOWN);
        snake.directionTo(Direction.UP);
        snake.directionTo(Direction.RIGHT);
        snake.directionTo(Direction.LEFT);
    }

    /**
     * Проверяет сценарий столкновения змейки с собственным телом.
     * Убеждается, что после столкновения метод {@link Snake#isAlive()} возвращает {@code false},
     * а повторный вызов {@link Snake#step()} также возвращает {@code false}.
     */
    @Test
    void testStep_collidesWithSelf_isAliveIsFalse() {
        Snake snake = new Snake(List.of(new Cell(1, 0),
                new Cell(0, 0),
                new Cell(0, 1),
                new Cell(1, 1)));
        snake.directionTo(Direction.UP);
        snake.step();
        snake.directionTo(Direction.LEFT);
        snake.step();
        snake.directionTo(Direction.DOWN);
        snake.step();
        assertFalse(snake.isAlive());
        assertFalse(snake.step());
    }
}
