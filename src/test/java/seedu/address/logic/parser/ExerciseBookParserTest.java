package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.exercise.Exercise;
import seedu.address.testutil.ExerciseBuilder;


public class ExerciseBookParserTest {
    private final ExerciseBookParser parser = new ExerciseBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Exercise exercise = new ExerciseBuilder().build();
        AddCommand command = (AddCommand) parser
                .parseCommand(AddCommand.COMMAND_WORD + " "
                        + "n/Push up d/Testing 2254 at/09-10-2020 c/224");
        AddCommand test = new AddCommand(exercise);
        assertEquals(new AddCommand(exercise), command);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        Exercise exercise = new ExerciseBuilder().build();
        DeleteCommand command = (DeleteCommand) parser
                .parseCommand(DeleteCommand.COMMAND_WORD + " " + "1");
        DeleteCommand test = new DeleteCommand(Index.fromOneBased(1));
        assertEquals(new DeleteCommand(Index.fromOneBased(1)), command);
    }
}
