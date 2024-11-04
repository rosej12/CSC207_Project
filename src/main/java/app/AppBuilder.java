package app;

import javax.swing.*;

public class AppBuilder {
    public AppBuilder() {

    }

    public AppBuilder addView() {
        return this;
    }

    public AppBuilder addUseCase() {
        return this;
    }

    public JFrame build() {
        return new JFrame();
    }
}
