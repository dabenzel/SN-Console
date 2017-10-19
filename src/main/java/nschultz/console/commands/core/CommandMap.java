/*
 *
 * The MIT License
 *
 * Copyright 2017 Niklas.
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

import nschultz.console.commands.types.*;
import nschultz.console.io.WorkingDirectory;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

public class CommandMap {

    private static final Map<String, Command> availableCommands = new TreeMap<>();
    private static final WorkingDirectory workingDirectory = new WorkingDirectory(Paths.get("").toAbsolutePath());

    public CommandMap() {
        final Command exitCommand = new ExitCommand();
        final Command helpCommand = new HelpCommand(this);
        final Command sayCommand = new SayCommand();
        final Command clearCommand = new ClearCommand();
        final Command osCommand = new OperatingSystemCommand();
        final Command versionCommand = new VersionCommand();
        final Command navigateCommand = new NavigateCommand(workingDirectory);
        final Command navigateToCommand = new NavigateToCommand(workingDirectory);
        final Command readDirCommand = new ReadDirectoryCommand(workingDirectory);
        final Command ipCommand = new IPCommand();
        final Command reachCommand = new ReachCommand();

        availableCommands.put(exitCommand.getName(), exitCommand);
        availableCommands.put(helpCommand.getName(), helpCommand);
        availableCommands.put(sayCommand.getName(), sayCommand);
        availableCommands.put(clearCommand.getName(), clearCommand);
        availableCommands.put(osCommand.getName(), osCommand);
        availableCommands.put(versionCommand.getName(), versionCommand);
        availableCommands.put(navigateCommand.getName(), navigateCommand);
        availableCommands.put(navigateToCommand.getName(), navigateToCommand);
        availableCommands.put(readDirCommand.getName(), readDirCommand);
        availableCommands.put(ipCommand.getName(), ipCommand);
        availableCommands.put(reachCommand.getName(), reachCommand);
    }

    public Command getCommand(String name) {
        return availableCommands.get(name);
    }

    public List<Command> toList() {
        final List<Command> commandList = new ArrayList<>();
        availableCommands.forEach((name, command) -> commandList.add(command));
        return commandList;
    }

    public String autoComplete(String text) {
        if (text.isEmpty()) {
            return text;
        }

        final AtomicReference<String> reference = new AtomicReference<>();
        availableCommands.forEach((name, command) -> {
            if (name.startsWith(text)) {
                reference.set(name);
            }
        });
        final String RESULT = reference.get();
        return RESULT != null ? RESULT : text;
    }
}
