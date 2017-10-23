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

import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Window;
import nschultz.console.commands.core.Command;

import java.util.List;

public class ResolutionCommand implements Command {

    private static final String ALL_PARM = "--all";
    private static final int ALL_PARM_INDEX = 0;

    @Override
    public void execute(List<String> arguments, Window cli) {
        if (isArgumentCountValid(arguments.size())) {

            final Screen primaryScreen = Screen.getPrimary();
            final Rectangle2D primaryResolution = primaryScreen.getBounds();

            if (arguments.size() == getMaxArgumentCount()) {
                if (arguments.get(ALL_PARM_INDEX).equals(ALL_PARM)) {
                    final ObservableList<Screen> screens = Screen.getScreens();
                    for (final Screen screen : screens) {
                        if (screen.equals(primaryScreen)) {
                            continue; // skip because we do not wan't to print same monitor twice
                        }
                        final Rectangle2D resolution = screen.getBounds();
                        display(cli, resolution.getWidth() + " x " + resolution.getHeight(), Color.GREEN, true);
                    }
                } else {
                    display(cli, "Bad second argument", Color.RED, true);
                    return;
                }
            }
            display(cli, primaryResolution.getWidth() + " x " + primaryResolution.getHeight(), Color.GREEN, false);
            display(cli, " (Primary)", Color.YELLOW, true);
        } else {
            displayInvalidArgumentCount(cli, getName(), getMinArgumentCount(), getMaxArgumentCount());
        }
    }

    @Override
    public String getName() {
        return "res";
    }

    @Override
    public String getInfo() {
        return "Display the resolutions of user's monitors.";
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
