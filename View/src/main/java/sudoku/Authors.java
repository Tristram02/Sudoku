package sudoku;

import java.util.ListResourceBundle;

public class Authors extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"1", "Kamil Ruszkiewicz"},
                {"2", "Kamil Włodarczyk"},
        };
    }
}
