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

package nschultz.console.ui;


import javafx.embed.swing.JFXPanel;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OutputAreaTest {

    @BeforeClass
    public static void setup() {
        initFxEnv();
    }

    private static void initFxEnv() {
        new JFXPanel();
    }

    @Test
    public void shouldRemoveFirstItemWhenMaxAmountOfItemsIsReached() {
        final OutputArea outputArea = new OutputArea(10);
        for (int i = 0; i < 50; i++) {
            outputArea.getChildren().add(new ColoredText(String.valueOf(i)));
        }
        assertEquals(10, outputArea.getChildren().size());
    }
}