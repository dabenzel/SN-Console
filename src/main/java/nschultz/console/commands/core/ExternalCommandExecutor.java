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

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import javafx.stage.Window;
import nschultz.console.io.WorkingDirectoryProvider;
import nschultz.console.ui.ColoredText;
import nschultz.console.ui.InputField;
import nschultz.console.ui.MainScene;
import nschultz.console.util.DaemonThreadFactory;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExternalCommandExecutor {

    private final Window cli;

    public ExternalCommandExecutor(Window cli) {
        this.cli = cli;
    }

    public void execute(String rawInput) throws IOException {
        if (rawInput.isEmpty()) {
            return;
        }

        final File workingDir = WorkingDirectoryProvider.getWorkingDirectory().getPath().toFile();
        final List<String> splittedRawInput = Arrays.asList(rawInput.split(" "));
        final ProcessBuilder pb = new ProcessBuilder(splittedRawInput).directory(workingDir);
        final Process process = pb.start();

        processStream(process.getInputStream(), Color.CYAN);
        processStream(process.getErrorStream(), Color.RED);
    }

    private void processStream(InputStream inp, Color outputColor) throws IOException {
        final int DEFAULT_BUFFER_SIZE = 8192;
        final MainScene mainScene = (MainScene) cli.getScene();
        final TextFlow outputArea = mainScene.getOutputArea();
        final InputField inputField = (InputField) mainScene.getInputField();
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inp, "CP437"));

        final int THREAD_POOL_SIZE = 1;
        final ExecutorService execService = Executors.newFixedThreadPool(THREAD_POOL_SIZE, new DaemonThreadFactory());

        inputField.setUsable(false);
        execService.submit((Callable<Void>) () -> {
            for (; ; ) {
                final char[] charBuffer = new char[DEFAULT_BUFFER_SIZE];
                if (bufferedReader.read(charBuffer) == -1) {
                    break;
                }
                displayToCommandLine(outputColor, outputArea, charBuffer);
            }
            execService.shutdown();
            inputField.setUsable(true);
            return null;
        });
    }

    private void displayToCommandLine(Color outputColor, TextFlow outputArea, char[] charBuffer) {
        Platform.runLater(() -> {
            final ColoredText texToAdd = new ColoredText(new String(charBuffer), outputColor, false);
            outputArea.getChildren().add(texToAdd);
        });
    }
}
