/**
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */


package com.sigueros.charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.statistics.Regression;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;

import javax.swing.*;

/**
 * Created by csanchez on 03/02/14.
 */
public class LinearRegressionExample extends JFrame{

    private static final long serialVersionUID = 1L;

    public LinearRegressionExample(String applicationTitle, String chartTitle) {
        super(applicationTitle);

        fillinDataArray();

        // based on the dataset we create the chart
        JFreeChart chart = createChart(chartTitle);
        // we put the chart into a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        // default size
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        // add it to our application
        setContentPane(chartPanel);
    }

    private void fillinDataArray() {

        data [0][0] = 1990;
        data [0][1] = 12.2D;

        data [1][0] = 1991;
        data [1][1] = 19.2D;

        data [2][0] = 1992;
        data [2][1] = 14.2D;

        data [3][0] = 1993;
        data [3][1] = 12.2D;

        data [4][0] = 1994;
        data [4][1] = 18.2D;

        data [5][0] = 1995;
        data [5][1] = 10.2D;

        data [6][0] = 1996;
        data [6][1] = 17.2D;

        data [7][0] = 1997;
        data [7][1] = 15.2D;

        data [8][0] = 1998;
        data [8][1] = 45.2D;

        data [9][0] = 1999;
        data [9][1] = 50.2D;
    }

    /**
     * Creates a sample dataset for Columns data
     */

    private TimeSeriesCollection createDatasetColumns() {

        final TimeSeries columnsData = new TimeSeries("Columns");

        for (int i = 0; i < getData().length; i++) {
            columnsData.add(new Year((int)getData()[i][0]), getData()[i][1]);
        }

        TimeSeriesCollection dataColumnsCollection = new TimeSeriesCollection();
        dataColumnsCollection.addSeries(columnsData);

        return dataColumnsCollection;

    }

    /**
     * Creates a sample dataset for Linear data
     */

    private TimeSeriesCollection createDatasetLinear() {

        final TimeSeries linearData = new TimeSeries("Linear");

        //The Linear dataSet is the Linear Regression:
        double ad[] = Regression.getOLSRegression(getData());

        for (int i = 0; i < getData().length; i++) {
            //The formula to calculate the value is a + b * x, where a = ad[0] and b = ad [1]
            linearData.add(new Year((int)getData()[i][0]), ad[0] + ad[1] * getData()[i][0]);
        }

        TimeSeriesCollection dataLinearCollection = new TimeSeriesCollection();
        dataLinearCollection.addSeries(linearData);

        return dataLinearCollection;

    }


    /**
     * Creates a chart
     */

    private JFreeChart createChart(String title) {

        TimeSeriesCollection dataColumnsCollection = createDatasetColumns();
        TimeSeriesCollection dataLinearCollection = createDatasetLinear();

        JFreeChart chart = ChartFactory.createXYBarChart(title, "Año", true, "granos/m3", dataColumnsCollection, PlotOrientation.VERTICAL,true,false,false);

        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer linearRenderer = new XYLineAndShapeRenderer(true,  false);
        plot.setDataset(1, dataLinearCollection);
        plot.setRenderer(1, linearRenderer);

        return chart;

    }


    public static void main(String[] args) {
        LinearRegressionExample demo = new LinearRegressionExample("Madrid", "Datos de Madrid");
        demo.pack();
        demo.setVisible(true);
    }

    public double[][] getData() {
        return data;
    }

    private double[][] data = new double [10][2];
}
