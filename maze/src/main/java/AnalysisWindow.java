import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnalysisWindow extends JFrame {
	private static final int START_SIZE = 0;
	private static final int DEFAULT_WIDTH = 865;
	private static final int DEFAULT_HEIGHT = 504;
	private static final Color COLOR_PRIM = Color.RED;
	private static final Color COLOR_KRUSKAL = Color.BLUE;
	private static final Color COLOR_ELLER = Color.GREEN;
	private static final Color COLOR_DEPTH = Color.ORANGE;

	private JFreeChart chart;
	private JCheckBox[] algorithms;
	private JTextField numRuns;

	public AnalysisWindow() {
		super("Analysis");
		this.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		algorithms = new JCheckBox[Driver.NO_ALGOS];
		algorithms[0] = new JCheckBox("Depth First");
		algorithms[0].setForeground(COLOR_DEPTH);
		algorithms[1] = new JCheckBox("Eller");
		algorithms[1].setForeground(COLOR_ELLER);
		algorithms[2] = new JCheckBox("Kruskal");
		algorithms[2].setForeground(COLOR_KRUSKAL);
		algorithms[3] = new JCheckBox("Prim");
		algorithms[3].setForeground(COLOR_PRIM);
		JButton initiate = new JButton("Start Simulation");
		initiate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				performAnalysis();
			}
		});

		JPanel checkList = new JPanel();
		checkList.setName("Algorithms");
		checkList.setLayout(new GridLayout(1, Driver.NO_ALGOS, 0, 10));
		for (int i = 0; i < Driver.NO_ALGOS; i++) {
			checkList.add(algorithms[i]);
		}

		JPanel simulatorOptions = new JPanel();
		JLabel label = new JLabel("Maximum size");
		numRuns = new JTextField("10");
		numRuns.setEnabled(true);
		numRuns.setSize(30, 10);
		simulatorOptions.add(label);
		simulatorOptions.add(numRuns);
		simulatorOptions.add(checkList);
		simulatorOptions.add(initiate);

		this.add(simulatorOptions, BorderLayout.EAST);
		this.pack();
	}

	public void performAnalysis() {
		int num;
		try {
			num = Integer.valueOf(numRuns.getText());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "Invalid size input.");
			return;
		}

		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries depth = new XYSeries("Depth First");
		XYSeries eller = new XYSeries("Eller");
		XYSeries kruskal = new XYSeries("Kruskal");
		XYSeries prim = new XYSeries("Prim");
		for (int i = 1; i <= num; i++) {
			int grid[][] = new int[2 * (START_SIZE + i) + 1][2 * (START_SIZE + i) + 1];
			for (int j = 0; j < grid.length; j++)
				for (int k = 0; k < grid[0].length; k++)
					grid[j][k] = 1;
			int startX = (int) (Math.random() * grid[0].length);
			if (startX % 2 == 0) {
				if (startX == 0) {
					startX++;
				} else if (startX == grid[0].length - 1) {
					startX--;
				} else
					startX--;
			}
			int startY = (int) (Math.random() * grid.length);
			if (startY % 2 == 0) {
				if (startY == 0) {
					startY++;
				} else if (startX == grid.length - 1) {
					startY--;
				} else
					startY--;
			}

			long startTime;
			if (algorithms[0].isSelected()) {
				startTime = System.nanoTime();
				Algorithms.depthFirstRecursive(grid, startX, startY);
				depth.add(START_SIZE + i, System.nanoTime() - startTime);
				startTime = System.nanoTime();
			}
			if (algorithms[1].isSelected()) {
				startTime = System.nanoTime();
				Algorithms.Eller(grid);
				eller.add(START_SIZE + i, System.nanoTime() - startTime);
			}
			if (algorithms[2].isSelected()) {
				startTime = System.nanoTime();
				Algorithms.Kruskal(grid);
				kruskal.add(START_SIZE + i, System.nanoTime() - startTime);
			}
			if (algorithms[3].isSelected()) {
				startTime = System.nanoTime();
				Algorithms.Prim(grid, startX, startY);
				prim.add(START_SIZE + i, System.nanoTime() - startTime);
			}
		}
		dataset.addSeries(depth);
		dataset.addSeries(eller);
		dataset.addSeries(kruskal);
		dataset.addSeries(prim);

		initializeGraph(dataset);
	}

	// initialize time vs. size graph
	private void initializeGraph(XYSeriesCollection sc) {
		NumberAxis xAxis = new NumberAxis("Size");
		xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		LogAxis yAxis = new LogAxis("Time (nanoseconds in base 2)");
		yAxis.setBase(2);
		yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		XYPlot plot = new XYPlot(sc, xAxis, yAxis, new XYLineAndShapeRenderer(true, false));
		chart = new JFreeChart("Chart", JFreeChart.DEFAULT_TITLE_FONT, plot, false);
		customizeChart(chart);
		this.add(new ChartPanel(chart), BorderLayout.NORTH);
		this.pack();
		this.revalidate();
		this.setVisible(true);
	}

	// following code customizes the look of the chart
	private void customizeChart(JFreeChart chart) {
		XYPlot plot = chart.getXYPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

		// sets paint color for each series
		renderer.setSeriesPaint(0, COLOR_PRIM);
		renderer.setSeriesPaint(1, COLOR_KRUSKAL);
		renderer.setSeriesPaint(2, COLOR_ELLER);
		renderer.setSeriesPaint(3, COLOR_DEPTH);

		// sets line thickness for each series
		renderer.setSeriesStroke(0, new BasicStroke(4.0f));
		renderer.setSeriesStroke(1, new BasicStroke(3.0f));
		renderer.setSeriesStroke(2, new BasicStroke(2.0f));

		// sets paint color for plot outlines
		plot.setOutlinePaint(Color.BLUE);
		plot.setOutlineStroke(new BasicStroke(2.0f));

		// sets renderer for lines
		plot.setRenderer(renderer);

		// sets plot background
		plot.setBackgroundPaint(Color.DARK_GRAY);

		// sets paint color for grid lines
		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.BLACK);

		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);

	}
}
