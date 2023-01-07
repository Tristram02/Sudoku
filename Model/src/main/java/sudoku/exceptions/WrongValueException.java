package sudoku.exceptions;

public class WrongValueException extends IllegalArgumentException {
    public WrongValueException(String msg) {
        super(msg);
    }
}
