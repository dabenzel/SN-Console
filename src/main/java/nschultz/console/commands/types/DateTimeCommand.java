package nschultz.console.commands.types;

import javafx.scene.paint.Color;
import javafx.stage.Window;
import nschultz.console.commands.core.Command;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DateTimeCommand implements Command {
    @Override
    public void execute(List<String> arguments, Window cli) {
        String dateTime = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date());

        if (isArgumentCountValid(arguments.size())) {
            display(cli, dateTime, Color.GREEN, true);
        } else {
            displayInvalidArgumentCount(cli, getName(), getMinArgumentCount(), getMaxArgumentCount());
        }
    }

    @Override
    public String getName() {
        return "datetime";
    }

    @Override
    public String getInfo() {
        return "Outputs the current date and time";
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
