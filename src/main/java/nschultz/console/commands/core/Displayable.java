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

package nschultz.console.commands.core;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.stage.Window;
import nschultz.console.ui.ColoredText;
import nschultz.console.ui.MainScene;

/**
 * This interface provides default implementations to output text to the command line interface.
 */
public interface Displayable {

    /**
     * Displays a {@code {@link ColoredText}} to the command line interface.
     *
     * @param cli     the command line interface
     * @param text    the text to display
     * @param color   the color of the text
     * @param newLine true if a new line should be added ('\n'); false otherwise
     */
    default void display(Window cli, String text, Color color, boolean newLine) {
        getOutputAreaChildren(cli).add(new ColoredText(text, color, newLine));
    }

    /**
     * Displays a default {@code {@link ColoredText}} to the command line interface in case the given arguments are
     * either to few or to many.
     *
     * @param cli              the command line interface
     * @param commandName      the name of the command
     * @param minArgumentCount the maximum argument count
     * @param maxArgumentCount the minimum argument count
     */
    default void displayInvalidArgumentCount(Window cli, String commandName, int minArgumentCount,
                                             int maxArgumentCount) {
        display(cli, "The given arguments do not match the requirements for ", Color.RED, false);
        display(cli, commandName, Color.MAGENTA, true);
        display(cli, "Min/Max: " + minArgumentCount + "/" + maxArgumentCount, Color.RED, true);
    }

    /**
     * Helper method for this interface to get the current children of the
     * {@code {@link nschultz.console.ui.OutputArea}}.
     *
     * @param cli the command line interface
     * @return the children of the {@code {@link nschultz.console.ui.OutputArea }}
     */
    private ObservableList<Node> getOutputAreaChildren(Window cli) {
        return ((MainScene) cli.getScene()).getOutputArea().getChildren();
    }
}
