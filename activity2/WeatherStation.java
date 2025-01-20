/*
 * Ben Bui
 * Class for a simple computer-based weather station that reports the current
 * temperature (in Celsius and Kelvin) every second. The station is attached to a
 * sensor that reports the temperature as a 16-bit number (0 to 65535)
 * representing the Kelvin temperature to the nearest 1/100th of a degree.
 *
 * This class implements Runnable so that it can be embedded in a Thread
 * which runs the periodic sensing.
 */

 public class WeatherStation implements Runnable {

    private final KelvinTempSensor sensor; // Temperature sensor.

    private final long PERIOD = 1000; // 1 sec = 1000 ms.
    private final Object UI; //UI for AWT, Swing, Text

    /*
     * When a WeatherStation object is created, it in turn creates the sensor
     * object it will use.
     */
    public WeatherStation(Object ui) {
        sensor = new KelvinTempSensor();
        ui = ui;
    }

    /*
     * The "run" method called by the enclosing Thread object when started.
     * Repeatedly sleeps a second, acquires the current temperature from
     * its sensor, and reports this as a formatted output string.
     */
    @Override
    public void run() {
        int reading;           // actual sensor reading.
        double celsius;        // sensor reading transformed to Celsius
        double kelvin;         // sensor reading in Kelvin
        final int KTOC = -27315; // Convert raw Kelvin reading to Celsius

        while (true) {
            try {
                Thread.sleep(PERIOD);
            } catch (Exception e) {
                // Ignore exceptions
            }

            reading = sensor.reading();
            celsius = (reading + KTOC) / 100.0;
            kelvin = reading / 100.0;

            if (ui instanceof AWTUI) {
                AWTUI awtui = (AWTUI) ui;
                awtui.celsiusField.setText(String.format("%6.2f", celsius));
                awtui.kelvinField.setText(String.format("%6.2f", kelvin));
            } else if(ui instanceof SwingUI){
                SwingUI swingui = (SwingUI) ui,
                swingui.setCelsiusJLabel(celsius);
                swingui.setKelvinJLabel(kelvin);
            }else
            /*
             * Print both Celsius and Kelvin readings.
             */
            System.out.printf("Reading is %6.2f degrees C and %6.2f degrees K%n", celsius, kelvin);
        }
    }

    /*
     * Initial main method.
     * Create the WeatherStation (Runnable).
     * Embed the WeatherStation in a Thread.
     * Start the Thread.
     */
    public static void main(String[] args) {
        if (args.length <1){
            System.err.println(("Usage: java WeatherStation <AWT|Swing|Text>"));
            System.exit(1);
        }

        String uiType = args[0];
        Object ui = null;

        switch ((uiType)) {
            case value:
                
                break;
        
            default:
                break;
        }
    }
}
