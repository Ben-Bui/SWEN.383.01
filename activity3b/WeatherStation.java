/*
Ben Bui
 * Initial Author
 *      Michael J. Lutz
 *
 * Other Contributers
 *
 * Acknowledgements
 */
 
/*
 * Class for a simple computer based weather station that reports the current
 * temperature (in Celsius) every second. The station is attached to a
 * sensor that reports the temperature as a 16-bit number (0 to 65535)
 * representing the Kelvin temperature to the nearest 1/100th of a degree.
 *
 * This class is implements Runnable so that it can be embedded in a Thread
 * which runs the periodic sensing.
 *
 * The class also extends Observable so that it can notify registered
 * objects whenever its state changes. Convenience functions are provided
 * to access the temperature in different schemes (Celsius, Kelvin, etc.)
 */
import java.util.Observable ;

public class WeatherStation extends Observable implements Runnable {

    private final KelvinTempSensor tempSensor ; // Temperature sensor.
    private final Barometer barometer;         // Barometric pressure sensor
    private double celsius;                    // Current temperature in Celsius
    private double kelvin;                     // Current temperature in Kelvin
    private double pressureInches;             // Current pressure in inches of mercury
    private double pressureMillibars;          // Current pressure in millibars

    private final long PERIOD = 1000 ;      // 1 sec = 1000 ms
    private final int KTOC = -27315 ;       // Kelvin to Celsius conversion.
    private static final double INCHESTM = 33.864; // Conversion factor

    private int currentReading ;

    /*
     * When a WeatherStation object is created, it in turn creates the sensor
     * object it will use.
     */
    public WeatherStation() {
        // sensor = new KelvinTempSensor() ;
        // currentReading = sensor.reading() ;
        this.tempSensor = new KelvinTempSensor();
        this.barometer = new Barometer();
    }
    /*
     * Return the current reading in degrees celsius as a
     * double precision number.
     */
    public double getCelsius() {
        return celsius ;
    }

    /*
     * Return the current reading in degrees Kelvin as a
     * double precision number.
     */
    public double getKelvin() {
        return kelvin;
    }
    // Get fahrenheit
    public double getFahrenheit(){
        return(celsius * 9/5) +32;
    }
    // Get pressure in inches 
    public double getPressureInches(){
        return pressureInches;
    }
    // get pressure in milibars
    public double getPressureMillibars(){
        return pressureMillibars;
    }

    /*
     * The "run" method called by the enclosing Thread object when started.
     * Repeatedly sleeps a second, acquires the current temperature from its
     * sensor, and notifies registered Observers of the change.
     */
    public void run() {
        while (true) {
            /*
             * Get a new temperature reading from the sensor and calculate
             * Celsius and Kelvin values.
             */
            int rawTemp = tempSensor.reading();
            kelvin = rawTemp / 100.0;
            celsius = kelvin - 273.15;

            /*
             * Get a new pressure reading from the barometer and calculate
             * values in inches and millibars.
             */
            pressureInches = barometer.pressure();
            pressureMillibars = pressureInches * INCHESTM;

            /*
             * Notify observers of the updated readings.
             */
            setChanged();
            notifyObservers();

            /*
             * Sleep for 1 second before updating again.
             */
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println("WeatherStation interrupted.");
                return;
            }
        }
    }
}

