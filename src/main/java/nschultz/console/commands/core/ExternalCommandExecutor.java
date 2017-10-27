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
import nschultz.console.io.WorkingDirectoryProvider;
import nschultz.console.ui.ColoredText;
import nschultz.console.ui.MainScene;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ExternalCommandExecutor {

    private static final int DEFAULT_BUFFER_SIZE = 8192;
    private final Window cli;

    public ExternalCommandExecutor(Window cli) {
        this.cli = cli;
    }

    public void execute(String rawInput) throws IOException {
        if (rawInput.isEmpty()) {
            return;
        }

        final File workingDir = WorkingDirectoryProvider.getWorkingDirectory().getPath().toFile();
        final Process process = Runtime.getRuntime().exec(rawInput, null, workingDir);
        final InputStream inp = process.getInputStream();
        final InputStream err = process.getErrorStream();
        final TextFlow outputArea = ((MainScene) cli.getScene()).getOutputArea();

        readStream(inp, outputArea, Color.CYAN);
        readStream(err, outputArea, Color.RED);

    }

    private void readStream(InputStream inp, TextFlow outputArea, Color color) throws IOException {
        for (; ; ) {
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            if (inp.read(buffer) == -1) {
                break;
            }
            outputArea.getChildren().add(new ColoredText(new String(buffer), color, true));
        }
    }
}
