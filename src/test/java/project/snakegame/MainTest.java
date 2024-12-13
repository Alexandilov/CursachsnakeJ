package project.snakegame;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 *  Тестовый класс для проверки функциональности класса {@link Main}.
 */
public class MainTest {

    private static final Main game = new Main();

    /**
     * Проверяет начальное состояние игры: размер змейки и начальный счет.
     * Также проверяет работу методов {@link Main#setScore(int)} и {@link Main#getScore()}.
     */
    @Test
    void testInitialState() {
        assertEquals(3, game.snake.asCells().size());
        assertEquals(0, game.getScore());
        game.setScore(5);
        assertEquals(5, game.getScore());
    }

    /**
     * Проверяет метод {@link Main#buildRectangle(int, int, int)} при четных координатах.
     * Ожидается, что созданный прямоугольник будет иметь светло-зеленый цвет.
     */
    @Test
    void buildRectangle_evenCoordinates_returnsLightGreenRectangle() {
        Rectangle rect = game.buildRectangle(0, 0, 20);
        assertEquals(0, rect.getX());
        assertEquals(0, rect.getY());
        assertEquals(20, rect.getWidth());
        assertEquals(20, rect.getHeight());
        assertEquals(Color.web("86BB1FFF"), rect.getFill());
    }

    /**
     * Проверяет метод {@link Main#genApple()}.
     * Убеждается, что сгенерированные координаты яблока находятся в пределах границ игрового поля.
     */
    @Test
    void genApple_generatesAppleWithinBounds() {
        for (int i = 0; i < 100; i++) {
            Cell apple = game.genApple();
            assertTrue(apple.getX() >= 0 && apple.getX() < Main.getSize());
            assertTrue(apple.getY() >= 0 && apple.getY() < Main.getSize());
        }
    }
}
