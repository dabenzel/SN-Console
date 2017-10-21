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

import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import nschultz.console.SNConsoleApp;

public class OutputArea extends TextFlow {

    private int maxAmountOfItems;

    public OutputArea(int maxAmountOfItems) {
        this.maxAmountOfItems = maxAmountOfItems;
        setupDefaultProperties();
        addStartupText();
    }

    private void setupDefaultProperties() {
        setFocusTraversable(false);
        addListChangeListener();
        getStyleClass().add("text-flow");
    }

    private void addStartupText() {
        getChildren().add(new ColoredText(SNConsoleApp.TITLE, Color.GREEN, true));
        getChildren().add(new ColoredText("Type ", Color.GREEN, false));
        getChildren().add(new ColoredText("help ", Color.MAGENTA, false));
        getChildren().add(new ColoredText("for a list of available commands.", Color.GREEN, true));
    }

    private void addListChangeListener() {
        getChildren().addListener(this::removeFirstItemWhenMaxAmountOfItemsIsReached);
    }

    private void removeFirstItemWhenMaxAmountOfItemsIsReached(ListChangeListener.Change<? extends Node> c) {
        if (c.getList().size() > maxAmountOfItems) {
            getChildren().remove(0);
        }
    }
}
