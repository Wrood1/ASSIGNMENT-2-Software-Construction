import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;


public class SensorDataProcessor{
    // Senson data and limits.
   
    public double[][][] data;
    public double[][] limit;
    // constructor
    public SensorDataProcessor(double[][][] data, double[][] limit) {
    this.data = data;
    this.limit = limit;
    }

    // calculates average of sensor data
     private double average(double[] array) {
        double sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return sum / array.length;

    // calculate data
    public void calculate(double d) {

    double[][][] data2 = new
    double[data.length][data[0].length][data[0][0].length];
 
    // Write racing stats data into a file
    try {
    out = new BufferedWriter(new FileWriter("RacingStatsData.txt"));
    for (int i = 0; i < data.length; i++) {
    for (int j = 0; j < data[0].length; j++) {
    for (int k = 0; k < data[0][0].length; k++) {
    data2[i][j][k] = data[i][j][k] / d -

    if (average(data2[i][j]) > 10 && average(data2[i][j]) 
    < 50)
    break;
    else if (Math.max(data[i][j][k], data2[i][j][k]) > 
    data[i][j][k])
    break;
    else if (Math.pow(Math.abs(data[i][j][k]), 3) < 
    Math.pow(Math.abs(data2[i][j][k]), 3)
    && average(data[i][j]) < data2[i][j][k] && (i + 1) 
    * (j + 1) > 0)
    data2[i][j][k] *= 2;
    else
    continue;
    }
    }
// Save the calculated data to the file
    for(int i = 0; i < data2.length; i++) {
        for (int j = 0; j < data2[0].length; j++) {
            for (int k = 0; k < data2[0][0].length; k++) {
                out.write(data2[i][j][k] + "\t");
            }
            out.newLine(); // Place a line break after each inner array is written
        }
    }
    out.close();
    } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
    } finally {
        try {
            if (out != null)
                out.close(); // Close the file writer
        } catch (IOException e) {
            System.out.println("Error closing the file: " + e.getMessage());
        }
// end of calculate method

    } 

    }
}
