package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.ArchiveCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ExerciseBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyExerciseBook;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.exercise.Template;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageForGoal;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {

    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final StorageForGoal goalStorage;
    private final ExerciseBookParser exerciseBookParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage, StorageForGoal goalStorage) {
        this.model = model;
        this.storage = storage;
        this.goalStorage = goalStorage;
        exerciseBookParser = new ExerciseBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException, IOException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult = null;
        Command command = exerciseBookParser.parseCommand(commandText);

        if (command instanceof ArchiveCommand) {
            ArchiveCommand cmd = (ArchiveCommand) command;
            cmd.setStorage(storage);
        }

        commandResult = command.execute(model);

        try {
            storage.saveExerciseBook(model.getExerciseBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        try {
            goalStorage.saveGoalBook(model.getGoalBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyExerciseBook getExerciseBook() {
        return model.getExerciseBook();
    }

    @Override
    public HashMap<String, Integer> getCaloriesByDay() {
        return model.getCaloriesByDay();
    }

    @Override
    public ObservableList<Exercise> getFilteredExerciseList() {
        return model.getFilteredExerciseList();
    }

    @Override
    public ObservableList<Template> getFilteredTemplateList() {
        return model.getFilteredTemplateList();
    }

    @Override
    public Path getExerciseBookFilePath() {
        return model.getExerciseBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}

