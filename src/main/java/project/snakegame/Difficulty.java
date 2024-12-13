package project.snakegame;


/**
 * Перечисление {@code Difficulty} представляет собой уровни сложности игры "Змейка".
 * <p>
 * Каждый уровень сложности характеризуется названием и скоростью игры, определяющей задержку
 * между шагами змейки.
 */
public enum Difficulty {
    /**
     * Легкий уровень сложности. Змейка движется медленно.
     */
    EASY("Легкий", 300),
    /**
     * Нормальный уровень сложности. Средняя скорость движения змейки.
     */
    NORMAL("Нормальный", 200),
    /**
     * Сложный уровень сложности. Змейка движется быстро.
     */
    HARD("Сложный", 100);

    private final String name;
    private final int speed;

    /**
     * Создает новый уровень сложности.
     *
     * @param name  Название уровня сложности для отображения пользователю.
     * @param speed Задержка в миллисекундах между шагами змейки для данного уровня сложности.
     */
    Difficulty(String name, int speed) {
        this.name = name;
        this.speed = speed;
    }

    /**
     * Возвращает скорость игры для данного уровня сложности.
     *
     * @return Задержка в миллисекундах между шагами змейки.
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Возвращает строковое представление уровня сложности (его название).
     *
     * @return Название уровня сложности.
     */
    @Override
    public String toString() {
        return name;
    }
}