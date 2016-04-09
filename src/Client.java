import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;

/**
 * Created by Victor on 2015-12-11.
 */
public class Client {

    private DisplayManager displayManager;

    private void initializeGui() {
        displayManager.run();
    }

    public Client(){
        displayManager = new DisplayManager();
        initializeGui();
    }

    public static void main(String[] args) {
        new Client();
    }

    public DisplayManager getDisplayManager(){
        return displayManager;
    }

}
