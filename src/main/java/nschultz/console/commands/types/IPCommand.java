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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IPCommand implements Command {

    private static final Logger logger = Logger.getLogger(IPCommand.class.getName());
    private static final String LOCAL_HOST = "127.0.0.1";

    @Override
    public void execute(List<String> arguments, Window cli) {
        if (isArgumentCountValid(arguments.size())) {
            try {
                final String HOST_ADDRESS = InetAddress.getLocalHost().getHostAddress();
                if (HOST_ADDRESS.equals(LOCAL_HOST)) {
                    display(cli, "The execution of this command returned ", Color.ORANGE, false);
                    display(cli, LOCAL_HOST + " (localhost).", Color.GREEN, true);
                    display(cli, "That could indicate that there is no network connection right now.", Color.ORANGE, true);
                } else {
                    display(cli, HOST_ADDRESS, Color.GREEN, true);
                }
            } catch (UnknownHostException ex) {
                display(cli, "Error: " + ex.getMessage(), Color.RED, true);
                logger.log(Level.SEVERE, "Error while fetching local ip.", ex);
            }
        } else {
            displayInvalidArgumentCount(cli, getName(), getMinArgumentCount(), getMaxArgumentCount());
        }
    }

    @Override
    public String getName() {
        return "ip";
    }

    @Override
    public String getInfo() {
        return "Displays the local ip of this machine.";
    }

    @Override
    public int getMaxArgumentCount() {
        return 0;
    }

    @Override
    public int getMinArgumentCount() {
        return 0;
    }
}
