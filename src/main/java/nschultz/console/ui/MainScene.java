/*
 *
 * The MIT License
 *
 * Copyright 2017 Niklas Schultz.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

package nschultz.console.ui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextFlow;

public class MainScene extends Scene {

    private static final int WIDTH = 820;
    private static final int HEIGHT = 480;
    private static TextFlow outputArea;
    private static TextField inputField;

    public MainScene() {
        super(constructContent(), WIDTH, HEIGHT);
        applyCSS();
    }

    private void applyCSS() {
        getStylesheets().add(getClass().getResource("/css/dark.css").toExternalForm());
        getRoot().setEffect(new Glow(0.2));
    }

    private static Parent constructContent() {
        final BorderPane borderPane = new BorderPane();

        outputArea = new OutputArea(2000);
        final ScrollPane scrollPane = new ScrollPane(outputArea);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.vvalueProperty().bind(outputArea.heightProperty());

        inputField = new InputField(outputArea);
        borderPane.setCenter(scrollPane);
        borderPane.setBottom(inputField);

        return borderPane;
    }

    public TextFlow getOutputArea() {
        return outputArea;
    }

    public TextField getInputField() {
        return inputField;
    }
}
