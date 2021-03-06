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

package nschultz.console.io;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class WorkingDirectory {

    private Path currentWorkingDirectory;

    WorkingDirectory(Path currentWorkingDirectory) {
        this.currentWorkingDirectory = currentWorkingDirectory;
    }

    public void resolve(Path name) throws FileNotFoundException {
        final Path resolvedName = currentWorkingDirectory.resolve(name);
        if (Files.exists(resolvedName) && Files.isDirectory(resolvedName)) {
            currentWorkingDirectory = resolvedName;
        } else {
            throw new FileNotFoundException("Directory does not exist.");
        }
    }

    public void goOneDirectoryBackwards() {
        if (currentWorkingDirectory.getParent() != null) {
            currentWorkingDirectory = currentWorkingDirectory.getParent().toAbsolutePath();
        }
    }

    public void goStartingDirectory() {
        currentWorkingDirectory = Paths.get("").toAbsolutePath();
    }

    public void goUserDirectory() {
        currentWorkingDirectory = Paths.get(System.getProperty("user.home"));
    }

    public Path getPath() {
        return currentWorkingDirectory;
    }
}
