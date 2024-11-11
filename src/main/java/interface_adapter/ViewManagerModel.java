package interface_adapter;

public class ViewManagerModel extends ViewModel<String>{

    public ViewManagerModel() {
        super("view manager");
        this.setState("");
    }

    public void setActiveView(String viewName){
        this.setState(viewName);
        this.firePropertyChanged("activeView");
    }
}
