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

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.TextFlow;
import nschultz.console.commands.core.AutoCompleter;
import nschultz.console.commands.core.CommandExecutor;
import nschultz.console.commands.core.CommandHistory;
import nschultz.console.commands.core.CommandMap;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputField extends TextField {

    private final CommandMap commandMap = new CommandMap();
    private final int MAX_HISTORY_SIZE = 10;
    private final CommandHistory commandHistory = new CommandHistory(MAX_HISTORY_SIZE);
    private final TextFlow outputArea;

    private boolean isUsable = true;

    public InputField(TextFlow outputArea) {
        this.outputArea = outputArea;
        handleKeyInput();
    }

    private void handleKeyInput() {
        setOnKeyPressed(event -> {
            final CommandExecutor cmdExecutor = new CommandExecutor(commandMap, outputArea.getScene().getWindow());
            final KeyCode keyCode = event.getCode();

            switch (keyCode) {
                case ENTER:
                    handleEnterKeyPressed(cmdExecutor);
                    break;
                case UP:
                    handleUpKeyPressed();
                    break;
                case DOWN:
                    handleDownKeyPressed();
                    break;
                case TAB:
                    handleTabPressed();
                    break;
            }
        });
    }

    public void setUsable(boolean isUsable) {
        this.isUsable = isUsable;
        setEditable(isUsable);
    }

    private void handleUpKeyPressed() {
        if (isUsable) {
            setText(commandHistory.getNext());
            moveCaretToLastPosition();
        }
    }

    private void handleDownKeyPressed() {
        if (isUsable) {
            setText(commandHistory.getPrevious());
            moveCaretToLastPosition();
        }
    }

    private void handleTabPressed() {
        setText(new AutoCompleter(commandMap.getAllNames()).autoComplete(getText()));
        moveCaretToLastPosition();
    }

    private void handleEnterKeyPressed(CommandExecutor cmdExecutor) {
        if (isUsable) {
            String input = getText();
            commandHistory.add(input);
            outputArea.getChildren().add(new ColoredText(">" + input));
            cmdExecutor.checkAndExecute(input);
            clear();
        }
    }

    private void moveCaretToLastPosition() {
        positionCaret(getText().length());
    }
}
