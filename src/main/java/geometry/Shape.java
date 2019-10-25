package geometry;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

import java.util.ArrayList;

/**
 * @author LAZAREV
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Rectangle.class, name = "rect"),
        @JsonSubTypes.Type(value = Circle.class, name = "circle"),
})
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
