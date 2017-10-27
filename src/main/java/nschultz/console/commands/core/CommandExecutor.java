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

import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import javafx.stage.Window;
import nschultz.console.ui.ColoredText;
import nschultz.console.ui.MainScene;

import java.io.IOException;
import java.util.List;

public class CommandExecutor {

    private final CommandMap commandMap;
    private final Window cli;

    public CommandExecutor(CommandMap commandMap, Window cli) {
        this.commandMap = commandMap;
        this.cli = cli;
    }

    public void checkAndExecute(String rawInput) {
        final ArgumentParser argumentParser = new ArgumentParser(rawInput);
        final List<String> arguments = argumentParser.fetchArguments();
        final TextFlow outputArea = ((MainScene) cli.getScene()).getOutputArea();
        final Command cmd = commandMap.getCommand(argumentParser.fetchCommandName());

        if (cmd != null) {
            cmd.execute(arguments, cli);
        } else {
            executeExternalCommand(rawInput, outputArea);
        }
    }

    private void executeExternalCommand(String rawInput, TextFlow outputArea) {
        try {
            final ExternalCommandExecutor externalCommandExecutor = new ExternalCommandExecutor(cli);
            externalCommandExecutor.execute(rawInput);
        } catch (IOException ignore) {
            if (!rawInput.isEmpty()) {
                outputArea.getChildren().add(new ColoredText("The command ", Color.RED, false));
                outputArea.getChildren().add(new ColoredText(rawInput, Color.MAGENTA, false));
                outputArea.getChildren().add(new ColoredText(" could not be found.", Color.RED, true));
            }
        }
    }
}
