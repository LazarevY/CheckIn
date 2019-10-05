package geometry;

import java.util.ArrayList;

/**
 * @author LAZAREV
 */

public interface Shape {
    boolean contains(int x, int y);
    boolean contains(Point point);

    /**
     * @param location точка приблизительной локации объекта
     * @param radius радиус определения местоположения, в пределах которого может быть объект
     * @return истина - если объект находится в пределах проверяемых границ, иначе ложь
     */
    boolean contains(Point location, int radius);
    ArrayList<Point> getBoundsPoints();
}
