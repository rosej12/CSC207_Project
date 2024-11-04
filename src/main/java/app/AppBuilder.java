package app;

import javax.swing.*;

public class AppBuilder {

    /**
     * Adds a View to the application.
     * @return this builder
     */
    public AppBuilder addView() {
        return this;
    }

    /**
     * Adds a Use Case to the application.
     * @return this builder
     */
    public AppBuilder addUseCase() {
        return this;
    }

    /**
     * Creates the JFrame for the application and sets the initial view to be displayed.
     * @return the application
     */
    public JFrame build() {
        return new JFrame();
    }
}
