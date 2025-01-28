/*
 * Initial Author
 *      Michael J. Lutz
 *
 * Other Contributers
 *
 * Acknowledgements
 */
 
/*
 * The TextUI class is an observer of the WeatherStation that,
 * when it receives an update message, prints the temperature
 * in Celsius and Kelvin.
 *
 * The main method for the text based monitoring application
 * is here as well.
 */
import java.util.Observer ;
import java.util.Observable ;

public class TextUI implements Observer {
    private final WeatherStation station ;

    /*
     * Remember the station we're attached to and
     * add ourselves as an observer.
     */
    public TextUI(WeatherStation station) {
        this.station = station ;
        this.station.addObserver(this) ;
    }

    /*
     * Called when WeatherStation gets another reading.
     * The Observable should be the station; the Object
     * argument is ignored.
     */
    public void update(Observable obs, Object ignore) {
        /*
         * Check for spurious updates from unrelated objects.
         */
        if( station != obs ) {
            return ;
        }
        /*
         * Retrieve and print the temperatures.
         */
        double celsius = station.getCelsius();
        double kelvin = station.getKelvin();
        double pressureInMillibars = station.getPressureMillibars();
        double pressureInInches = station.getPressureInches();
        System.out.printf("Temperature: %.2f°C (%.2fK)\n", celsius, kelvin);
        System.out.printf("Pressure: %.2f millibars (%.2f inches)\n", pressureInMillibars, pressureInInches);

    }

    /*
     * Start the application.
     */
    public static void main(String[] args) {
        WeatherStation ws = new WeatherStation() ;
        Thread thread = new Thread(ws) ;
        TextUI ui = new TextUI(ws) ;

        thread.start() ;
    }
}
