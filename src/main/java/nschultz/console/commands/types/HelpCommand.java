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

import javafx.application.HostServices;
import javafx.scene.paint.Color;
import javafx.stage.Window;
import nschultz.console.commands.core.Command;
import nschultz.console.commands.core.CommandMap;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelpCommand implements Command {

    private static final Logger logger = Logger.getLogger(HelpCommand.class.getName());
    private static final int CMD_PARM_INDEX = 0;
    private final List<Path> tempFiles = new ArrayList<>();
    private final CommandMap commandMap;

    public HelpCommand(CommandMap commandMap) {
        this.commandMap = commandMap;
        addDeleteTempFilesShutdownHook();
    }

    @Override
    public void execute(List<String> arguments, Window cli) {
        if (isArgumentCountValid(arguments.size())) {
            if (arguments.size() == getMaxArgumentCount()) {
                displayDetailedHelpToSpecificCommand(arguments.get(CMD_PARM_INDEX), cli);
            } else {
                displayShortHelpOfAllCommands(cli);
            }
        } else {
            displayInvalidArgumentCount(cli, getName(), getMinArgumentCount(), getMaxArgumentCount());
        }
    }

    private void displayDetailedHelpToSpecificCommand(String commandName, Window cli) {
        final Command command = commandMap.getCommand(commandName);
        if (command != null) {
            final InputStream inp = getClass().getResourceAsStream("/help/" + commandName + ".html");
            if (inp == null) {
                display(cli, "Help file for specified command is not yet present.", Color.ORANGE, true);
                return;
            }
            try {
                final Path tempHelpFile = Files.createTempFile(null, ".html");
                tempFiles.add(tempHelpFile);

                transferDataFromInputStream(inp, tempHelpFile);

                HostServices hostServices = (HostServices) cli.getScene().getWindow().getProperties().get("hostservices");
                hostServices.showDocument(tempHelpFile.toAbsolutePath().toString());
            } catch (IOException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        } else {
            display(cli, "Could not find specified command ", Color.RED, false);
            display(cli, commandName, Color.YELLOW, true);
        }
    }

    private void addDeleteTempFilesShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                for (Path tmpFile : tempFiles) {
                    Files.deleteIfExists(tmpFile);
                }
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "Error while trying to delete temp files.");
            }
        }));
    }

    private void transferDataFromInputStream(InputStream inp, Path tempHelpFile) throws IOException {
        final int BUFFER_SIZE = 8192; // 8KB
        for (; ; ) {
            final byte[] buffer = new byte[BUFFER_SIZE];
            if (inp.read(buffer) == -1) {
                break;
            }
            Files.write(tempHelpFile, buffer, StandardOpenOption.APPEND);
        }
    }

    private void displayShortHelpOfAllCommands(Window cli) {
        final List<Command> commands = commandMap.getAllCommands();
        for (Command cmd : commands) {
            display(cli, cmd.getName() + ": ", Color.MAGENTA, false);
            display(cli, cmd.getInfo(), Color.GREEN, true);
        }
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getInfo() {
        return "Displays information of every available command.";
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
