package wecui.render.points;

import wecui.render.LineColor;
import wecui.render.shapes.Render3DBox;
import wecui.util.Vector2;

/**
 * Stores data about a prism surrounding two
 * blocks in the world. Used to store info
 * about the selector blocks for polys. Keeps 
 * track of color, x/y/z values, and rendering.
 * 
 * @author yetanotherx
 * @author lahwran
 */
public class PointRectangle {

    protected Vector2 point;
    protected LineColor color = LineColor.POLYPOINT;

    public PointRectangle(Vector2 point) {
        this.point = point;
    }

    public PointRectangle(int x, int z) {
        this.point = new Vector2(x, z);
    }

    public void render(int min, int max) {
        float off = 0.03f;
        Vector2 minVec = new Vector2(off, off);
        Vector2 maxVec = new Vector2(off + 1, off + 1);

        new Render3DBox(this.color, this.point.subtract(minVec).toVector3(min - off), this.point.add(maxVec).toVector3(max + 1 + off)).render();
    }

    public Vector2 getPoint() {
        return this.point;
    }

    public void setPoint(Vector2 point) {
        this.point = point;
    }

    public LineColor getColor() {
        return this.color;
    }

    public void setColor(LineColor color) {
        this.color = color;
    }
}
