import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

public class SensorDataProcessor {
    // Sensor data and limits.
    public double[][][] data;
    public double[][] limit;

    // Constructor
    public SensorDataProcessor(double[][][] data, double[][] limit) {
        this.data = data;
        this.limit = limit;
    }

    // Calculates the average of a sensor data array
    private double average(double[] array) {
        double sum = 0;
        for (double value : array) {
            sum += value;
        }
        return sum / array.length;
    }

    // Calculates and processes sensor data
    public void calculate(double d) {
        double[][][] data2 = new double[data.length][data[0].length][data[0][0].length];
        BufferedWriter out = null;

        // Calculate the average of data2 array once
        double avgData2 = 0;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                for (int k = 0; k < data[0][0].length; k++) {
                    data2[i][j][k] = data[i][j][k] / d - Math.pow(limit[i][j], 2.0);
                    avgData2 += data2[i][j][k];
                }
            }
        }
        avgData2 /= data.length * data[0].length * data[0][0].length;

        // Process each data point
        try {
            out = new BufferedWriter(new FileWriter("RacingStatsData.txt"));

            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[0].length; j++) {
                    boolean shouldSkipDataPoint = false;
                    for (int k = 0; k < data[0][0].length; k++) {
                        // Check for average condition
                        if (average(data2[i][j]) > 10 && average(data2[i][j]) < 50) {
                            shouldSkipDataPoint = true;
                            break; // Skip to the next data point
                        }

                        // Check for maximum and data2 value comparison
                        if (Math.max(data[i][j][k], data2[i][j][k]) > data[i][j][k]) {
                            shouldSkipDataPoint = true;
                            break; // Skip to the next data point
                        }

                        // Check for power of absolute values and average
                        if (Math.pow(Math.abs(data[i][j][k]), 3) < Math.pow(Math.abs(data2[i][j][k]), 3) &&
                                average(data[i][j]) < data2[i][j][k] && (i + 1) * (j + 1) > 0) {
                            data2[i][j][k] *= 2; // Double the value
                        } else {
                            continue; // Skip to the next iteration
                        }
                    }

                    if (shouldSkipDataPoint) {
                        break; // Skip to the next data point
                    }
                }
            }

            // Save the calculated data to the file
            for (int i = 0; i < data2.length; i++) {
                for (int j = 0; j < data2[0].length; j++) {
                    for (int k = 0; k < data2[0][0].length; k++) {
                        out.write(data2[i][j][k] + "\t");
                    }
                    out.newLine(); // Place a line break after each inner array is written
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (out != null) {
                    out.close(); // Close the file writer
                }
            } catch (IOException e) {
                System.out.println("Error closing the file: " + e.getMessage());
            }
        }
    }//I am Dania and I wrote this comment to make sure my commit is correct

}