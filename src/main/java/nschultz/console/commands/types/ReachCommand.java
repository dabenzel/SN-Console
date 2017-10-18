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

package nschultz.console.commands.types;

import javafx.scene.paint.Color;
import javafx.stage.Window;
import nschultz.console.commands.core.Command;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReachCommand implements Command {

    private static final Logger logger = Logger.getLogger(ReachCommand.class.getName());

    @Override
    @SuppressWarnings("all")
    public void execute(List<String> arguments, Window cli) {
        if (isArgumentCountValid(arguments.size())) {
            try {
                final InetAddress adr = InetAddress.getByName(arguments.get(0));
                final long START = System.nanoTime();
                if (adr.isReachable(10000)) {
                    long end = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - START);
                    display(cli, "Took ", Color.GREEN, false);
                    display(cli, String.valueOf(end), Color.YELLOW, false);
                    display(cli, " ms to reach ", Color.GREEN, false);
                    display(cli, arguments.get(0), Color.MAGENTA, true);
                } else {
                    display(cli, "Timeout.", Color.RED, true);
                }
            } catch (IOException ex) {
                display(cli, "ERROR while trying to reach given IP.", Color.RED, true);
                logger.log(Level.SEVERE, "Error while trying to reach ip", ex);
            }
        } else {
            displayInvalidArgumentCount(cli, getName(), getMaxArgumentCount(), arguments.size());
        }
    }

    @Override
    public String getName() {
        return "reach";
    }

    @Override
    public String getInfo() {
        return "Measures the time it takes to reach a given IP.";
    }

    @Override
    public int getMaxArgumentCount() {
        return 1;
    }
}
