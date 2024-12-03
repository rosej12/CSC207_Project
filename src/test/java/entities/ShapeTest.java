package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

public class ShapeTest {

    private Shape shape;

    @BeforeEach
    public void setUp() {
        shape = new Shape(Shape.ShapeType.RECTANGLE, 10, 20, 30, 40, Color.RED, 5);
    }

    @Test
    public void testConstructor() {
        assertEquals(Shape.ShapeType.RECTANGLE, shape.getType());
        assertEquals(10, shape.getX1());
        assertEquals(20, shape.getY1());
        assertEquals(30, shape.getX2());
        assertEquals(40, shape.getY2());
        assertEquals(Color.RED, shape.getColor());
        assertEquals(5, shape.getStrokeWidth());
    }

    @Test
    public void testGetType() {
        assertEquals(Shape.ShapeType.RECTANGLE, shape.getType());
    }

    @Test
    public void testSetType() {
        shape.setType(Shape.ShapeType.CIRCLE);
        assertEquals(Shape.ShapeType.CIRCLE, shape.getType());
    }

    @Test
    public void testGetX1() {
        assertEquals(10, shape.getX1());
    }

    @Test
    public void testGetY1() {
        assertEquals(20, shape.getY1());
    }

    @Test
    public void testGetX2() {
        assertEquals(30, shape.getX2());
    }

    @Test
    public void testGetY2() {
        assertEquals(40, shape.getY2());
    }

    @Test
    public void testGetColor() {
        assertEquals(Color.RED, shape.getColor());
    }

    @Test
    public void testSetColor() {
        shape.setColor(Color.BLUE);
        assertEquals(Color.BLUE, shape.getColor());
    }

    @Test
    public void testGetStrokeWidth() {
        assertEquals(5, shape.getStrokeWidth());
    }

    @Test
    public void testSetStrokeWidth() {
        shape.setStrokeWidth(10);
        assertEquals(10, shape.getStrokeWidth());
    }

    @Test
    public void testNegativeStrokeWidth() {
        shape.setStrokeWidth(-1);
        assertEquals(-1, shape.getStrokeWidth()); // This assumes no validation; adapt if validation is added
    }
}