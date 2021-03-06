import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

public class LeftArrowBubble extends JPanel {
   private static final long serialVersionUID = -5389178141802153305L;
   private int radius = 10; //반지름
   private int arrowSize = 12; //말풍선화살표
   private int strokeThickness = 3; //
   private int padding = strokeThickness / 2;

   @Override
   protected void paintComponent(final Graphics g) {
      final Graphics2D g2d = (Graphics2D) g;
      g2d.setColor(new Color(0.5f, 0.8f, 1f));
      int x = padding+30 + strokeThickness + arrowSize; //30더해주니까 더 오른쪽으로 갔음
      int width = 230 - arrowSize - (strokeThickness * 2);
      int bottomLineY = 50 - strokeThickness;
      g2d.fillRect(x, padding, width, bottomLineY);
      g2d.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING,   RenderingHints.VALUE_ANTIALIAS_ON));
      g2d.setStroke(new BasicStroke(strokeThickness));
      RoundRectangle2D.Double rect = new RoundRectangle2D.Double(x, padding, width, bottomLineY, radius, radius);
      Polygon arrow = new Polygon();
      arrow.addPoint(50, 8); //x축에 30씩 더해줌->오른쪽으로 감
      arrow.addPoint(30, 10);
      arrow.addPoint(50, 12);
      Area area = new Area(rect);
      area.add(new Area(arrow));
      g2d.draw(area);
   }
}
