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

import java.util.ArrayList;
import java.util.List;

public class CommandHistory {

    private final List<String> history = new ArrayList<>();
    private final int MAX_HISTORY_SIZE;

    private int currentIndex = 0;
    private boolean frequentlyAdded = false;

    public CommandHistory(int maxHistorySize) {
        if (maxHistorySize < 0) {
            throw new IllegalArgumentException("maxSize can not be negative.");
        }
        MAX_HISTORY_SIZE = maxHistorySize;
    }

    public void add(String text) {
        if (history.contains(text) || text.isEmpty()) {
            return;
        }
        if (history.size() >= MAX_HISTORY_SIZE) {
            history.remove(history.size() - 1);
        }

        history.add(text);
        currentIndex = history.size() - 1;
        frequentlyAdded = true;
    }

    public String getNext() {
        if (history.isEmpty()) {
            return "";
        }

        final String NEXT_COMMAND = history.get(currentIndex);

        if (!frequentlyAdded) {
            currentIndex++;
            if (currentIndex > history.size() - 1) {
                currentIndex = 0;
            }
        }

        frequentlyAdded = false;
        return NEXT_COMMAND;
    }

    public String getPrevious() {
        if (history.isEmpty()) {
            return "";
        }

        final String PREVIOUS_COMMAND = history.get(currentIndex);

        if (!frequentlyAdded) {
            currentIndex--;
            if (currentIndex < 0) {
                currentIndex = history.size() - 1;
            }
        }

        frequentlyAdded = false;
        return PREVIOUS_COMMAND;
    }
}
