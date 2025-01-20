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
    private final Object ui; // UI object (AWTUI, SwingUI, or null for Text)

    /*
     * Constructor accepts a UI object (AWTUI or SwingUI) or null for text UI.
     */
    public WeatherStation(Object ui) {
        this.sensor = new KelvinTempSensor();
        this.ui = ui;
    }

    @Override
    public void run() {
        int reading;
        double celsius;
        double kelvin;
        final int KTOC = -27315; // Convert raw Kelvin reading to Celsius

        while (true) {
            try {
                Thread.sleep(PERIOD);
            } catch (InterruptedException e) {
                System.err.println("Thread interrupted: " + e.getMessage());
                break;
            }

            reading = sensor.reading();
            celsius = (reading + KTOC) / 100.0;
            kelvin = reading / 100.0;

            // Update UI based on the type of interface
            if (ui instanceof AWTUI) {
                AWTUI awtui = (AWTUI) ui;
                awtui.celsiusField.setText(String.format("%6.2f", celsius));
                awtui.kelvinField.setText(String.format("%6.2f", kelvin));
            } else if (ui instanceof SwingUI) {
                SwingUI swingui = (SwingUI) ui;
                swingui.setCelsiusJLabel(celsius);
                swingui.setKelvinJLabel(kelvin);
            } else {
                // Default to text-based UI
                System.out.printf("Reading is %6.2f degrees C and %6.2f degrees K%n", celsius, kelvin);
            }
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java WeatherStation <AWT|Swing|Text>");
            System.exit(1);
        }

        String uiType = args[0];
        Object ui = null;

        switch (uiType) {
            case "AWT":
                ui = new AWTUI();
                break;
            case "Swing":
                ui = new SwingUI();
                break;
            case "Text":
                // No UI needed for text-based output
                break;
            default:
                System.err.println("Invalid UI type. Use 'AWT', 'Swing', or 'Text'.");
                System.exit(1);
        }

        WeatherStation ws = new WeatherStation(ui);
        Thread thread = new Thread(ws);
        thread.start();
    }
}
