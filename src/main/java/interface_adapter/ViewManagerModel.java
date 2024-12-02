package interface_adapter;

/**
 * The model for the View Manager. Its state is the name of the View
 * that is currently active. An initial state of "" is used.
 */
public class ViewManagerModel extends ViewModel<String> {
    public ViewManagerModel() {
        super("view manager");
        this.setState("");
    }
}
