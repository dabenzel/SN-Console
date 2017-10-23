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

import javafx.stage.Window;

import java.util.List;

/**
 * This interface defines the required methods for every command type.
 * The {@code {@link Command}} interface {@code extends} the {@code {@link Displayable}}
 * interface to provide the command implementations the possibility to output text to the cli more easily.
 *
 * @see Displayable
 */
public interface Command extends Displayable {

    /**
     * Executes this command.
     *
     * @param arguments the arguments to execute this command with
     * @param cli       the command line interface
     */
    void execute(List<String> arguments, Window cli);

    /**
     * Returns the name of this command. The name is the input the user
     * has to make in the cli to execute this command.
     *
     * @return the name of this command
     */
    String getName();

    /**
     * Returns information about this command. The info is printed when executing the
     * the {@code {@link nschultz.console.commands.types.HelpCommand}}.
     *
     * @return info about this command
     */
    String getInfo();

    /**
     * Returns the maximum of arguments this command can process.
     *
     * @return the maximum of arguments
     */
    int getMaxArgumentCount();

    /**
     * Returns the minimum of arguments this command can process.
     *
     * @return the minimum of arguments
     */
    int getMinArgumentCount();

    /**
     * Checks whether the given arguments length is valid for this command. The default implementation
     * checks if the given arguments are neither less than {@code getMinArgumentCount} and neither higher than
     * {@code getMaxArgumentCount}.
     *
     * @param argumentCount the argument count this command is supposed to process
     * @return true if the argument length is valid; false otherwise
     */
    default boolean isArgumentCountValid(int argumentCount) {
        return argumentCount <= getMaxArgumentCount() && argumentCount >= getMinArgumentCount();
    }
}
