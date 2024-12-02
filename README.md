# CSC207_Project

## Summary

A drawing app that allows the user to draw a quick sketch using a variety of simple drawing tools and a simple palette. The user can choose to randomly generate a color palette or upload an image to generate the palette from. The sketch can then be converted to a completed, coloured art piece.

This program was created to provide a fun an intuitive way for users to create digital art. The color palette generation features let users explore new color combinations, and the AI rendering features make it easy for users to enhance their drawings and experiment with AI-driven art styles. This program is ideal for anyone interested in exploring digital art.

## Authors & Contributors

Ayushi Verma
Rosanna Jiang
Charles Cai
Roberts Haughton

## Table of Contents

1. [Features](#features)
2. [Installation Instructions](#installation-instructions)
3. [Usage Guide](#usage-guide)
4. [License](#license)
5. [Feedback](#feedback)
6. [Contributions](#contributions)

## Features

This drawing program includes the following features:
- **Basic Drawing Functionality:** 
  - Users can create sketches with a mouse and draw with different colors and brush size
  - Users can draw different shapes.
  - Users can undo and redo brush strokes.
- **Color Palette:**
  - Generate a random color palette for inspiration.
  - Lock certain colors and regenerate or choose colors manually.
  - Upload an image to extract a color palette from it.
- **Render:** Convert your sketch into a complete painting by entering a description of your sketch.
- **Image Export:** Save your sketch as an image file for easy sharing and printing.

## APIs Used

- Imagga API: https://imagga.com/
  - This is used to extract color palettes from images.
- Clipdrop API: https://clipdrop.co/
  - This is used to convert sketches to images.


## Installation

To get started with this project, follow the instructions below to set it up on your local machine.

### Prerequisites

Before installing, make sure you have the following tools installed on your system:

- **Java** (JDK 11 or higher): This project is built using Java. You can download the latest version of Java from [Oracle's website](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) or use [OpenJDK](https://openjdk.java.net/).

- **IntelliJ IDEA**: You can download IntelliJ IDEA from [here](https://www.jetbrains.com/idea/). Any edition (Community or Ultimate) will work.

- **Git**: To clone the repository, you’ll need Git installed. You can download Git from [here](https://git-scm.com/).

### Installation Steps

1. **Open the project in IntelliJ IDEA**:
   - Launch IntelliJ IDEA
   - Go to **File > New > Project From Version Control** and paste  `https://github.com/rosej12/CSC207_Project.git` as the URL.
2. Check project SDK:
   - Ensure that IntelliJ IDEA is using the correct version of the JDK for the project. Go to **File > Project Structure > Project** and set the Project SDK to JDK 11 (or higher), if not already set.  
3. Import Maven dependencies: IntelliJ IDEA should automatically import the dependencies listed in the `pom.xml` file when you open the project. If it doesn't, you can manually refresh Maven:
   - Right-click on the `pom.xml` file and select **"Add as Maven Project"** from the context menu. This will configure the project as a Maven project and allow IntelliJ IDEA to manage dependencies.
4. Run the application: Open the `Main.java` file and press Run.

#### Troubleshooting Installation Issues

- **Maven errors:** If you encounter errors related to Maven dependencies, try refreshing the Maven project in IntelliJ IDEA:
  - Open the Maven tool window.
  - Click the Refresh button (the circular arrow).
  - Alternatively, you can run `mvn clean install -U` in the terminal if necessary.
- **IntelliJ IDEA not recognizing dependencies** 
  - If IntelliJ IDEA doesn't automatically import dependencies from the `pom.xml`, you can manually trigger the import by right-clicking on the `pom.xml` file and selecting **Maven > Reimport**.

## Usage Guide

- **Drawing:**
  - Launch the program and use your mouse to draw on the canvas. Switch between painting and erasing and adjust brush size using the toolbar. 
- **Color Palette:**
  - Click on the "Generate Colors" button to create a random color palette. 
  - Lock the current palette and generate a new one or manually select individual colors. 
  - Click on the "Generate Color from Image" button to then upload an image to extract its dominant colors and use them in your drawing. 
  - Once you have a palette, switch between the colors in the toolbar.
- **Render:**
  - Once your sketch is complete, go to the "Render Screen."
  - Enter a description of your sketch, and you will get a finished drawing based on your sketch and description. 
- **Saving Your Artwork:**
  - Save your sketch by clicking the "Save Image" button.

## License

This project is licensed under the **CC0 1.0 Universal (CC0 1.0) Public Domain Dedication**. For more information, refer to the [LICENSE](./LICENSE) file.

## Feedback

We welcome your feedback! Please share your thoughts, suggestions in [Discussions](https://github.com/your-username/your-repo-name/discussions) or report bugs in the [Issues](https://github.com/your-username/your-repo-name/issues) section of this repository.

### Feedback Guidelines
- Valid feedback should describe the issue clearly, include screenshots if applicable, and suggest possible improvements.
- Expect responses within a week and be sure to check back for updates.

## Contributing

We welcome contributions to this project! If you'd like to help improve the project, follow the steps below to contribute.

### How to Contribute

1. **Fork the repository**: Click the "Fork" button at the top-right corner of this page to create a copy of the repository under your GitHub account.

2. **Clone your fork**:
   After forking, clone your repository to your local machine
3. **Create a new branch:** Always create a new branch for your feature or bug fix
4. **Make your changes:** Work on your changes in your local branch. This could include adding features, fixing bugs, or improving documentation.
5. **Commit your changes:** After making your changes, commit them with a clear and descriptive message
6. **Push to your fork:** Push your changes to your fork on GitHub:
7. **Open a pull request:** Go back to this repository and click the "New pull request" button. Describe your changes in the pull request and link to any relevant issues.

### Pull Request Guidelines
- Provide a clear description of what your pull request does.
- If applicable, reference any issues (e.g., "Fixes #123") in the title.
- Follow the existing coding style and conventions used in the project (Ensure you follow CLEAN Architecture and SOLID principles).
- Ensure that your changes do not break existing functionality and include tests as well as screenshots of test coverage where applicable.
