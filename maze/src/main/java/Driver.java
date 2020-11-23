import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class Driver extends JFrame {
    private static final int DEFAULT_TEXT_HEIGHT = 10;
    public static final int NO_ALGOS = 4;
    private static AnalysisWindow analysisWindow;

    private MazePanel[] mazePanels;

    public Driver() {
        this.setName("Simulator");
        this.setLayout(new GridLayout(2, NO_ALGOS + 1, 30, 0));
        initializeAlgos();

        InitFrame init = new InitFrame();
        for (int i = 0; i < NO_ALGOS; i++) {
            init.addPropertyChangeListener(this.mazePanels[i]);
        }
        this.add(init);
        for (int i = 0; i < NO_ALGOS; i++) {
            JLabel label = new JLabel(mazePanels[i].getName());
            label.setSize(new Dimension(mazePanels[i].getWidth(), DEFAULT_TEXT_HEIGHT));
            this.add(label);
        }

        JButton analysis = new JButton("<html>" + "Analyze" + "<br>" + "Algorithms" + "</html>");
        analysis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                analysisWindow = new AnalysisWindow();
                analysisWindow.setVisible(true);
            }
        });
        this.add(analysis);

        for (int i = 0; i < NO_ALGOS; i++) {
            this.add(mazePanels[i]);
        }

        this.setPreferredSize(new Dimension(9 * mazePanels[0].getWidth(), DEFAULT_TEXT_HEIGHT
                + 3 * mazePanels[0].getHeight()));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }

    private void initializeAlgos() {
        this.mazePanels = new MazePanel[NO_ALGOS];
        this.mazePanels[0] = new MazePanel("Depth First");
        this.mazePanels[1] = new MazePanel("Eller");
        this.mazePanels[2] = new MazePanel("Kruskal");
        this.mazePanels[3] = new MazePanel("Prim");
    }

    public static void main(String[] args) {
        Driver driver = new Driver();
        driver.pack();
        driver.setVisible(true);

        boolean running = true;
        while (running) {

        }
    }


    static class MazePanel extends JPanel implements PropertyChangeListener {
        private int[][] grid;
        private static final int DEFAULT_CELL_SIZE = 10;
        private static final String RESIZE = "Resize the Frame";

        public MazePanel(String name) {
            this.setName(name);
            this.setSize(100, 100);
        }

        @Override
        public void propertyChange(PropertyChangeEvent ev) {
            if (ev.getPropertyName().equals(InitFrame.GENERATE)) {
                System.out.println(this.getName() + " received data.");
                List<Integer> dim = (List<Integer>) ev.getNewValue();
                createMaze(dim.get(0), dim.get(1));
                this.paint(this.getGraphics());
            }
        }

        @Override
        public void paintComponent(Graphics g) {
            if (grid != null) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int i, j;

                // w|h = 2^i * 5
                // i = log(w|h / 5) / log(2)
                // scale = DEFAULT_CELL_SIZE * 2 / (2 + i)
                int xScaled = (int)((double)DEFAULT_CELL_SIZE * 2 / Math.sqrt(grid[0].length));
                int yScaled = (int)((double)DEFAULT_CELL_SIZE * 2/ Math.sqrt(grid.length));
                int scale = xScaled > yScaled ? yScaled : xScaled;

                for (i = 0; i < grid.length; i++) {
                    for (j = 0; j < grid[0].length; j++) {
                        g2d.setColor(Color.BLACK);
                        if (grid[i][j] == 1) {
                            g2d.fillRect(i * scale, j * scale,
                                    scale, scale);
                        } else {
                            g2d.drawRect(i * scale, j * scale,
                                    scale, scale);
                        }
                    }
                }
                this.setSize(grid[0].length * grid[0].length * scale * 2,
                        grid.length * grid.length * scale * 2);
                this.revalidate();
            }
        }

        private void createMaze(int width, int height) {
            long time;
            int grid[][] = new int[2 * height + 1][2 * width + 1];
            for (int i = 0; i < grid.length; i++)
                for (int j = 0; j < grid[0].length; j++)
                    grid[i][j] = 1;
            int startX = (int)(Math.random() * grid[0].length);
            if (startX % 2 == 0) {
                if (startX == 0) {
                    startX++;
                } else if (startX == grid[0].length - 1) {
                    startX--;
                } else startX--;
            }
            int startY = (int)(Math.random() * grid.length);
            if (startY % 2 == 0) {
                if (startY == 0) {
                    startY++;
                } else if (startX == grid.length - 1) {
                    startY--;
                } else startY--;
            }
            /** FIX **/
            time = System.currentTimeMillis();
            if (this.getName().equals("Depth First")) {
            	grid[startY][startX] = 0;
                Algorithms.depthFirstRecursive(grid, startX, startY);
            } else if (this.getName().equals("Eller")) {
                Algorithms.Eller(grid);
            } else if (this.getName().equals("Kruskal")) {
            	Algorithms.Kruskal(grid);
            } else if (this.getName().equals("Prim")) {
            	grid[startY][startX] = 0;
                Algorithms.Prim(grid, startX, startY);
            }
            time = System.currentTimeMillis() - time;
            this.grid = grid;
        }
    }

}