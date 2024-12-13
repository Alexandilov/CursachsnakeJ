package project.snakegame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Главный класс приложения "Змейка", отвечающий за запуск и управление игровым процессом.
 * <p>
 * Этот класс инициализирует JavaFX Stage, создает основное игровое окно, отрисовывает
 * игровое поле, змейку и яблоко, а также обрабатывает ввод пользователя и игровую логику.
 */
public class Main extends Application {
    /**
     * Переменная для логирования
     */
    private static final Logger logger = LogManager.getLogger(Main.class);
    /**
     * Переменная, хранящая название
     */
    private static final String name = "Snake";
    /**
     * Размер сетки
     */
    private static final int size = 25;
    /**
     * Объект змейки, представляющий игрового персонажа.
     */
    protected final Snake snake = new Snake(List.of(new Cell(0, 0), new Cell(1, 0), new Cell(2, 0)));
    /**
     * Массив данных, хранящий длину змеи
     */
    private final List<Rectangle> snakePosition = new ArrayList<>();
    /**
     * Лейбл для отображения поражения игры
     */
    private Label gameOverLabel;
    /**
     * Объект круга, представляющий позицию яблока на игровом поле.
     */
    protected Circle applePosition;
    /**
     * Счетчик съеденных яблок
     */
    private int score = 0;
    /**
     * Лейбл для отображения съеденных яблок
     */
    private Label scoreLabel;



    private Difficulty selectedDifficulty = Difficulty.NORMAL;

    /**
     * Создает прямоугольник для отрисовки клетки игрового поля.
     *
     * @param x    Координата X клетки на поле.
     * @param y    Координата Y клетки на поле.
     * @param size Размер стороны клетки.
     * @return {@link Rectangle} - созданный прямоугольник.
     */
    protected Rectangle buildRectangle(int x, int y, int size) {
        Rectangle rect = new Rectangle();
        rect.setX(x * size);
        rect.setY(y * size);
        rect.setHeight(size);
        rect.setWidth(size);
        if ((x + y) % 2 == 0) {
            rect.setFill(Color.web("86BB1FFF"));
        } else {
            rect.setFill(Color.web("F5D163FF"));
        }
        return rect;
    }


    /**
     * Создает и возвращает прямоугольник, представляющий клетку змейки.
     *
     * @param cell  {@link Cell} - координаты клетки змейки.
     * @param color {@link Color} - цвет заливки прямоугольника.
     * @return {@link Rectangle} - созданный прямоугольник.
     */
    private Rectangle block(Cell cell, Color color) {
        Rectangle rect = new Rectangle();
        rect.setX(cell.getX() * 20);
        rect.setY(cell.getY() * 20);
        rect.setHeight(20);
        rect.setWidth(20);
        rect.setFill(color);
        return rect;
    }

    /**
     * Создает и возвращает круг, представляющий яблоко на игровом поле.
     *
     * @param cell {@link Cell} - координаты клетки, где расположено яблоко.
     * @return {@link Circle} - созданный круг.
     */
    private Circle circle(Cell cell) {
        Circle circle = new Circle();
        circle.setCenterX(cell.getX() * 20 + 10);
        circle.setCenterY(cell.getY() * 20 + 10);
        circle.setRadius(10);
        circle.setFill(Color.RED);
        return circle;
    }

    /**
     * Создает и возвращает группу узлов, представляющую сетку игрового поля.
     * Сетка состоит из чередующихся цветных прямоугольников.
     *
     * @return {@link Group} - группа узлов, представляющая сетку.
     */
    private Group buildGrid() {
        Group panel = new Group();
        for (int y = 0; y != this.size; y++) {
            for (int x = 0; x != this.size; x++) {
                panel.getChildren().add(
                        this.buildRectangle(x, y, 20)
                );
            }
        }
        return panel;
    }

    /**
     * Основной метод запуска JavaFX приложения.
     * Инициализирует окно, обрабатывает ввод пользователя, запускает игровой цикл.
     *
     * @param stage Основное окно приложения.
     */
    @Override
    public void start(Stage stage) {
        logger.info("opening a window with a choice of difficulty");
        ChoiceDialog<Difficulty> dialog = new ChoiceDialog<>(Difficulty.NORMAL, Difficulty.values());
        dialog.setTitle("Выбор сложности");
        dialog.setHeaderText("Выберите уровень сложности:");
        dialog.setContentText("Сложность:");

        Optional<Difficulty> result = dialog.showAndWait();
        if (result.isPresent()) {
            result.ifPresent(difficulty -> selectedDifficulty = difficulty);

            BorderPane border = new BorderPane();
            stage.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
                switch (event.getCode().getName()) {
                    case "Right":
                        snake.directionTo(Direction.RIGHT);
                        break;
                    case "Up":
                        snake.directionTo(Direction.UP);
                        break;
                    case "Left":
                        snake.directionTo(Direction.LEFT);
                        break;
                    case "Down":
                        snake.directionTo(Direction.DOWN);
                        break;
                }
            });

            gameOverLabel = new Label("Game Over");
            gameOverLabel.setTextFill(Color.RED);
            gameOverLabel.setStyle("-fx-font-size: 40px; -fx-font-weight: bold;");
            gameOverLabel.setVisible(false);

            scoreLabel = new Label("Score: " + score);
            scoreLabel.setStyle("-fx-font-size: 40px; -fx-font-weight: bold;");

            VBox topBox = new VBox(scoreLabel, gameOverLabel);
            topBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            border.setTop(topBox);

            var grid = buildGrid();
            border.setCenter(grid);
            stage.setScene(new Scene(border, 520, 700));
            stage.setTitle(name);
            stage.setResizable(false);
            drawSnake(grid);
            drawApple(grid);
            stage.show();
            logger.info("the program has started with difficulty: {}", selectedDifficulty);
            new Thread(() -> {
                try {
                    while (snake.isAlive()) {
                        var eat = snake.step();
                        Platform.runLater(() -> {

                            if (eat) {
                                logger.info("the snake ate the apple");
                                setScore(1);
                                redrawApple(grid);
                            }
                            updateScoreLabel();
                            redrawSnake(grid);
                        });
                        Thread.sleep(selectedDifficulty.getSpeed());
                    }
                    Platform.runLater(() -> {
                        gameOverLabel.setVisible(true);
                        border.setCenter(gameOverLabel);
                    });
                } catch (InterruptedException e) {
                    logger.error("runtime exception");
                    throw new RuntimeException(e);
                }
            }
            ).start();
        } else {
            Platform.exit();
            logger.error("the user did not choose the difficulty");
        }
    }

    /**
     * Отрисовывает змейку на игровом поле.
     * Удаляет предыдущие отрисовки змейки и добавляет новые прямоугольники,
     * соответствующие текущим клеткам тела змейки.
     *
     * @param grid {@link Group} - группа узлов, представляющая игровое поле.
     */
    private void drawSnake(Group grid) {
        for (Cell body : snake.asCells()) {
            var el = block(body, Color.PURPLE);
            snakePosition.add(el);
            grid.getChildren().add(el);
        }
    }

    /**
     * Отрисовывает яблоко на игровом поле.
     * Генерирует новую позицию для яблока, устанавливает ее для змейки и
     * добавляет круглое представление яблока на игровое поле.
     *
     * @param grid {@link Group} - группа узлов, представляющая игровое поле.
     */
    private void drawApple(Group grid) {
        var cell = genApple();
        snake.apple(cell);
        applePosition = circle(cell);
        grid.getChildren().add(applePosition);
    }

    /**
     * Перерисовывает яблоко на игровом поле.
     * Удаляет текущее изображение яблока и отрисовывает его на новой позиции.
     *
     * @param group {@link Group} - группа узлов, представляющая игровое поле.
     */
    private void redrawApple(Group group) {
        group.getChildren().removeAll(applePosition);
        drawApple(group);
    }

    /**
     * Генерирует случайную позицию для яблока на игровом поле.
     *
     * @return {@link Cell} - случайная позиция для яблока.
     */
    protected Cell genApple() {
        var rm = new Random();
        var x = rm.nextInt(25);
        var y = rm.nextInt(25);
        return new Cell(x, y);
    }

    private void redrawSnake(Group group) {
        group.getChildren().removeAll(snakePosition);
        drawSnake(group);
    }

    /**
     * Возвращает размер игрового поля.
     *
     * @return int - размер игрового поля.
     */
    public static int getSize() {
        return size;
    }

    /**
     * Обновляет счётчик после съеденного яблока
     */
    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + score);
    }

    /**
     * Возвращает текущий счет игрока.
     *
     * @return int - текущий счет.
     */
    public int getScore() {
        return score;
    }

    /**
     * Увеличивает счет игрока на заданное значение.
     *
     * @param number Значение, на которое нужно увеличить счет.
     */
    public void setScore(int number) {
        this.score += number;
    }


    /**
     * Точка входа в программу
     * @param args аргументы
     */
    public static void main(String[] args) {
        launch(args);
    }
}