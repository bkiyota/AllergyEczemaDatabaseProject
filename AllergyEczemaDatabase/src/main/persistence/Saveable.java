package persistence;

/**
 * This class is part of the means by which data persistence is implemented into the application. This interface
 * is implemented by the Patient class since the patient files are what are stored in the txt file.
 * */

import java.io.PrintWriter;

// Represents data that can be saved to file
public interface Saveable {

    // MODIFIES: printWriter
    // EFFECTS: writes the saveable to printWriter
    void save(PrintWriter printWriter);
}

