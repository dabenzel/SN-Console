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

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateFileCommand implements Command {

    private static final Logger logger = Logger.getLogger(CreateFileCommand.class.getName());

    private static final int NAME_PARM_INDEX = 0;

    @Override
    public void execute(List<String> arguments, Window cli) {
        if (isArgumentCountValid(arguments.size())) {
            final WorkingDirectory workingDirectory = WorkingDirectoryProvider.getWorkingDirectory();
            final String fileName = arguments.get(NAME_PARM_INDEX);
            try {
                Files.createFile(workingDirectory.getPath().resolve(Paths.get(fileName)));
            } catch (FileAlreadyExistsException ex) {
                display(cli, "Specified file already exists.", Color.RED, true);
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "Error while creating file.", ex);
                display(cli, "Error while creating file: " + ex.getMessage(), Color.RED, true);
            }
        } else {
            displayInvalidArgumentCount(cli, getName(), getMinArgumentCount(), getMaxArgumentCount());
        }
    }

    @Override
    public String getName() {
        return "wfile";
    }

    @Override
    public String getInfo() {
        return "Creates a file in the current working directory.";
    }

    @Override
    public int getMaxArgumentCount() {
        return 1;
    }

    @Override
    public int getMinArgumentCount() {
        return 1;
    }
}
