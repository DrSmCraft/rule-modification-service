package com.cdss4pcp.rulemodificationservice.util;

import org.cqframework.cql.cql2elm.CqlCompilerException;
import org.cqframework.cql.elm.tracking.TrackBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a translation error with details such as message, start and end line numbers, start and end character positions,
 * library ID, library version, and library system.
 */
class TranslationError {
    private String message;
    private int startLine;
    private int startChar;
    private int endLine;
    private int endChar;
    private String libraryId;
    private String libraryVersion;
    private String librarySystem;


    public TranslationError(String message, TrackBack locator) {
        this.message = message;
        this.startLine = locator.getStartLine();
        this.startChar = locator.getStartChar();
        this.endLine = locator.getEndLine();
        this.endChar = locator.getEndChar();
        this.libraryId = locator.getLibrary().getId();
        this.libraryVersion = locator.getLibrary().getVersion();
        this.librarySystem = locator.getLibrary().getSystem();

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStartLine() {
        return startLine;
    }

    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    public int getStartChar() {
        return startChar;
    }

    public void setStartChar(int startChar) {
        this.startChar = startChar;
    }

    public int getEndLine() {
        return endLine;
    }

    public void setEndLine(int endLine) {
        this.endLine = endLine;
    }

    public int getEndChar() {
        return endChar;
    }

    public void setEndChar(int endChar) {
        this.endChar = endChar;
    }

    public String getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(String libraryId) {
        this.libraryId = libraryId;
    }

    public String getLibraryVersion() {
        return libraryVersion;
    }

    public void setLibraryVersion(String libraryVersion) {
        this.libraryVersion = libraryVersion;
    }

    public String getLibrarySystem() {
        return librarySystem;
    }

    public void setLibrarySystem(String librarySystem) {
        this.librarySystem = librarySystem;
    }
}


/**
 * Represents a custom RuntimeException for CQL translation errors, containing a list of compilation errors.
 */
public class TranslationException extends RuntimeException {
    List<CqlCompilerException> compilationErrors;

    /**
     * Initializes a new TranslationException with the provided list of compilation errors.
     *
     * @param compilationErrors the list of compilation errors causing the exception
     */
    public TranslationException(List<CqlCompilerException> compilationErrors) {
        super("CQL Translation error");
        this.compilationErrors = compilationErrors;
    }

    public List<CqlCompilerException> compilationErrors() {
        return compilationErrors;
    }

    public List<TranslationError> getErrors() {
        List<TranslationError> errors = new ArrayList<>();
        for (CqlCompilerException e : compilationErrors) {
            errors.add(new TranslationError(e.getMessage(), e.getLocator()));
        }
        return errors;
    }

}
