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

package nschultz.console.util;

import javafx.geometry.Dimension2D;
import javafx.stage.Screen;

public class ResolutionHandler {

    private static final Dimension2D HVGA = new Dimension2D(480, 320);
    private static final Dimension2D WVGA = new Dimension2D(720, 480);
    private static final Dimension2D XGA = new Dimension2D(1024, 768);
    private static final Dimension2D HD720 = new Dimension2D(1280, 720);
    private static final Dimension2D HD1080 = new Dimension2D(1920, 1080);
    private static final Dimension2D UHD = new Dimension2D(3840, 2160);

    public Dimension2D determineStartupApplicationResolution() {
        final double SCREEN_WIDTH = Screen.getPrimary().getBounds().getWidth();
        final double SCREEN_HEIGHT = Screen.getPrimary().getBounds().getHeight();

        Dimension2D applicationSize = HVGA;

        if (SCREEN_WIDTH <= WVGA.getWidth() && SCREEN_HEIGHT <= WVGA.getHeight()) {
            applicationSize = HVGA;
        } else if (SCREEN_WIDTH <= XGA.getWidth() && SCREEN_HEIGHT <= XGA.getHeight()) {
            applicationSize = WVGA;
        } else if (SCREEN_WIDTH <= HD720.getWidth() && SCREEN_HEIGHT <= HD720.getHeight()) {
            applicationSize = XGA;
        } else if (SCREEN_WIDTH <= HD1080.getWidth() && SCREEN_HEIGHT <= HD1080.getHeight()) {
            applicationSize = HD720;
        } else if (SCREEN_WIDTH <= UHD.getWidth() && SCREEN_HEIGHT <= UHD.getHeight()) {
            applicationSize = HD1080;
        }
        return applicationSize;
    }
}
