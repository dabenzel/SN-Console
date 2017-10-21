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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class NavigateToCommand implements Command {

    private WorkingDirectory workingDirectory;

    public NavigateToCommand(WorkingDirectory workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    @Override
    public void execute(List<String> arguments, Window cli) {
        if (isArgumentCountValid(arguments.size())) {
            Path dir = Paths.get(arguments.get(0));
            if (Files.notExists(dir)) {
                display(cli, "Directory does not exist.", Color.RED, true);
            } else {
                workingDirectory.setCurrentWorkingDirectory(dir);
                display(cli, "Navigated to ", Color.GREEN, false);
                display(cli, workingDirectory.getCurrentWorkingDirectory().toString(), Color.YELLOW, true);
            }
        } else {
            displayInvalidArgumentCount(cli, getName(), getMinArgumentCount(), getMaxArgumentCount());
        }
    }

    @Override
    public String getName() {
        return "navto";
    }

    @Override
    public String getInfo() {
        return "Navigates to specified directory.";
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
