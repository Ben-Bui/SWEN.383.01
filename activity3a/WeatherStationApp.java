public class WeatherStationApp {
    public static void main(String[] args) {
        // Create a new WeatherStation object
        WeatherStation ws = new WeatherStation();
        
        // Start the thread to read and update weather data
        Thread thread = new Thread(ws);
        thread.start();
        
        // Create the SwingUI object, which observes the WeatherStation
        SwingUI ui = new SwingUI(ws);
    }
}
