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

import nschultz.console.commands.types.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CommandMap {

    private final Map<String, Command> availableCommands = new TreeMap<>();

    public CommandMap() {
        final Command exitCommand = new ExitCommand();
        final Command helpCommand = new HelpCommand(this);
        final Command sayCommand = new SayCommand();
        final Command clearCommand = new ClearCommand();
        final Command osCommand = new OperatingSystemCommand();
        final Command versionCommand = new VersionCommand();
        final Command changeDirectoryCommand = new ChangeDirectoryCommand();
        final Command readDirCommand = new ReadDirectoryCommand();
        final Command ipCommand = new IPCommand();
        final Command reachCommand = new ReachCommand();
        final Command createDirCommand = new CreateDirectoryCommand();
        final Command dateTimeCommand = new DateTimeCommand();
        final Command resolutionCommand = new ResolutionCommand();
        final Command browseCommand = new OpenCommand();
        final Command userNameCommand = new UserNameCommand();
        final Command createFileCommand = new CreateFileCommand();
        final Command readFileCommand = new ReadFileCommand();

        availableCommands.put(exitCommand.getName(), exitCommand);
        availableCommands.put(helpCommand.getName(), helpCommand);
        availableCommands.put(sayCommand.getName(), sayCommand);
        availableCommands.put(clearCommand.getName(), clearCommand);
        availableCommands.put(osCommand.getName(), osCommand);
        availableCommands.put(versionCommand.getName(), versionCommand);
        availableCommands.put(changeDirectoryCommand.getName(), changeDirectoryCommand);
        availableCommands.put(readDirCommand.getName(), readDirCommand);
        availableCommands.put(ipCommand.getName(), ipCommand);
        availableCommands.put(reachCommand.getName(), reachCommand);
        availableCommands.put(createDirCommand.getName(), createDirCommand);
        availableCommands.put(dateTimeCommand.getName(), dateTimeCommand);
        availableCommands.put(resolutionCommand.getName(), resolutionCommand);
        availableCommands.put(browseCommand.getName(), browseCommand);
        availableCommands.put(userNameCommand.getName(), userNameCommand);
        availableCommands.put(createFileCommand.getName(), createFileCommand);
        availableCommands.put(readFileCommand.getName(), readFileCommand);
    }

    public Command getCommand(String name) {
        return availableCommands.get(name);
    }

    public List<Command> getAllCommands() {
        final List<Command> commands = new ArrayList<>();
        availableCommands.forEach((name, command) -> commands.add(command));
        return commands;
    }

    public List<String> getAllNames() {
        final List<String> names = new ArrayList<>();
        availableCommands.forEach((name, command) -> names.add(name));
        return names;
    }
}
