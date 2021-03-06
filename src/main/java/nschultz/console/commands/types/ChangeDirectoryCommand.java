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

package nschultz.console.commands.types;

import javafx.scene.paint.Color;
import javafx.stage.Window;
import nschultz.console.commands.core.Command;
import nschultz.console.io.WorkingDirectory;
import nschultz.console.io.WorkingDirectoryProvider;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ChangeDirectoryCommand implements Command {

    private static final int DIR_PARM_INDEX = 0;

    @Override
    public void execute(List<String> arguments, Window cli) {
        final WorkingDirectory workingDirectory = WorkingDirectoryProvider.getWorkingDirectory();

        if (isArgumentCountValid(arguments.size())) {
            if (!arguments.isEmpty()) {
                final Path name = Paths.get(arguments.get(DIR_PARM_INDEX));

                if (name.toString().equals(".")) {
                    workingDirectory.goStartingDirectory();
                    display(cli, "Navigated to ", Color.GREEN, false);
                    display(cli, workingDirectory.getPath().toString(), Color.YELLOW, true);
                } else if (name.toString().equals("..")) {
                    workingDirectory.goOneDirectoryBackwards();
                    display(cli, "Navigated to ", Color.GREEN, false);
                    display(cli, workingDirectory.getPath().toString(), Color.YELLOW, true);
                } else if (name.toString().equals("~")) {
                    workingDirectory.goUserDirectory();
                    display(cli, "Navigated to ", Color.GREEN, false);
                    display(cli, workingDirectory.getPath().toString(), Color.YELLOW, true);
                } else {
                    try {
                        workingDirectory.resolve(name);
                        display(cli, "Navigated to ", Color.GREEN, false);
                        display(cli, workingDirectory.getPath().toString(), Color.YELLOW, true);
                    } catch (FileNotFoundException ex) {
                        display(cli, ex.getMessage(), Color.RED, true);
                    }
                }
            } else {
                display(cli, workingDirectory.getPath().toString(), Color.GREEN, true);

            }
        } else {
            displayInvalidArgumentCount(cli, getName(), getMinArgumentCount(), getMaxArgumentCount());
        }
    }

    @Override
    public String getName() {
        return "cd";
    }

    @Override
    public String getInfo() {
        return "Changes the directory.";
    }

    @Override
    public int getMaxArgumentCount() {
        return 1;
    }

    @Override
    public int getMinArgumentCount() {
        return 0;
    }
}
