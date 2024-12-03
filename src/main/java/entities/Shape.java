package entities;

import java.awt.Color;

public class Shape {
    public enum ShapeType {
        LINE, RECTANGLE, SQUARE, TRIANGLE, CIRCLE
    }

    private ShapeType type;
    private int x1, y1, x2, y2;
    private Color color;
    private int strokeWidth;

    public Shape(ShapeType type, int x1, int y1, int x2, int y2, Color color, int strokeWidth) {
        this.type = type;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
        this.strokeWidth = strokeWidth;
    }

    public ShapeType getType() { return type; }
    public void setType(ShapeType type) { this.type = type; }
    public int getX1() { return x1; }
    public int getY1() { return y1; }
    public int getX2() { return x2; }
    public int getY2() { return y2; }
    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }
    public int getStrokeWidth() { return strokeWidth; }
    public void setStrokeWidth(int strokeWidth) { this.strokeWidth = strokeWidth; }
}
