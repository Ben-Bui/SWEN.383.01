/*
 * Ben Bui
 * Initial Author
 *      Michael J. Lutz
 *
 * Other Contributers
 *
 * Acknowledgements
 */

/*
 * Swing UI class used for displaying the information from the
 * associated weather station object.
 * This is an extension of JFrame, the outermost container in
 * a Swing application.
 */
 
//  import obesrver
import java.util.Observer;
import java.util.Observable;

import java.awt.Font ;
import java.awt.GridLayout ;

import javax.swing.JFrame ;
import javax.swing.JLabel ;
import javax.swing.JPanel ;

//import java.text.DecimalFormat ;

public class SwingUI extends JFrame implements Observer {
    private JLabel celsiusField ;   // put current celsius reading here
    private JLabel kelvinField ;    // put current kelvin reading here
    private JLabel pressureMillibarsField; // Display pressure in millibars
    private JLabel pressureInchesField;   // Display pressure in inches

    /*
     * A Font object contains information on the font to be used to
     * render text.
     */
    private static Font labelFont =
        new Font(Font.SERIF, Font.PLAIN, 72) ;

    /*
     * Create and populate the SwingUI JFrame with panels and labels to
     * show the temperatures.
     */
    public SwingUI(WeatherStation station) {
        super("Weather Station") ;

        //make swingui an observer of weatherstation
        station.addObserver(this);
        /*
         * WeatherStation frame is a grid of 1 row by an indefinite
         * number of columns.
         */
        this.setLayout(new GridLayout(1,0)) ;

        /*
         * There are two panels, one each for Kelvin and Celsius, added to the
         * frame. Each Panel is a 2 row by 1 column grid, with the temperature
         * name in the first row and the temperature itself in the second row.
         */
        JPanel panel ;

        /*
         * Set up Kelvin display.
         */
        panel = new JPanel(new GridLayout(2,1)) ;
        this.add(panel) ;
        createLabel(" Kelvin ", panel) ;
        kelvinField = createLabel("", panel) ;

        /*
         * Set up Celsius display.
         */
        panel = new JPanel(new GridLayout(2,1)) ;
        this.add(panel) ;
        createLabel(" Celsius ", panel) ;
        celsiusField = createLabel("", panel) ;

         /*
         * Set up Pressure in Millibars display.
         */
        panel = new JPanel(new GridLayout(2, 1));
        this.add(panel);
        createLabel(" Pressure (mb) ", panel);
        pressureMillibarsField = createLabel("", panel);

        /*
         * Set up Pressure in Inches display.
         */
        panel = new JPanel(new GridLayout(2, 1));
        this.add(panel);
        createLabel(" Pressure (in) ", panel);
        pressureInchesField = createLabel("", panel);

         /*
         * Set up the frame's default close operation pack its elements,
         * and make the frame visible.
         */
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
        this.pack() ;
        this.setVisible(true) ;
    }

    /*
     * Set the label holding the Kelvin temperature.
     */
    public void setKelvinJLabel(double temperature) {
        kelvinField.setText(String.format("%6.2f", temperature)) ;
    }

    /*
     * Set the label holding the Celsius temperature.
     */
    public void setCelsiusJLabel(double temperature) {
        celsiusField.setText(String.format("%6.2f", temperature)) ;
    }
    /*
     * Set the label holding the pressure in millibars.
     */
    public void setPressureMillibarsJLabel(double pressure) {
        pressureMillibarsField.setText(String.format("%6.2f", pressure));
    }

    /*
     * Set the label holding the pressure in inches.
     */
    public void setPressureInchesJLabel(double pressure) {
        pressureInchesField.setText(String.format("%6.2f", pressure));
    }

    /*
     * Create a Label with the initial value <title>, place it in
     * the specified <panel>, and return a reference to the Label
     * in case the caller wants to remember it.
     */
    private JLabel createLabel(String title, JPanel panel) {
        JLabel label = new JLabel(title) ;

        label.setHorizontalAlignment(JLabel.CENTER) ;
        label.setVerticalAlignment(JLabel.TOP) ;
        label.setFont(labelFont) ;
        panel.add(label) ;

        return label ;
    }
    
    public void update(Observable obs, Object arg) {
        if (obs instanceof WeatherStation) {
            WeatherStation station = (WeatherStation) obs;
            
            // Update the labels using the provided methods
            setKelvinJLabel(station.getKelvin());
            setCelsiusJLabel(station.getCelsius());
            setPressureMillibarsJLabel(station.getPressureMillibars());
            setPressureInchesJLabel(station.getPressureInches());
        }
    
    }
}
