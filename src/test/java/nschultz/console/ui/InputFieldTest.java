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

package nschultz.console.ui;

import javafx.embed.swing.JFXPanel;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InputFieldTest {

    @BeforeClass
    public static void setup() {
        initFxEnv();
    }

    private static void initFxEnv() {
        new JFXPanel();
    }

    @Test
    @Ignore
    public void shouldHaveNoCharactersWhenEnterKeyWasPressed() {
        final InputField inputField = new InputField(new OutputArea(100));
        inputField.setText("Clear this text, please!");
        inputField.fireEvent(new KeyEvent(
                KeyEvent.KEY_PRESSED, KeyCode.ENTER.getChar(), "", KeyCode.ENTER, false, false, false, false));

        assertEquals(0, inputField.getText().length());
    }
}
