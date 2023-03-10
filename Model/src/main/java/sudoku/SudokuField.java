package sudoku;

/*-
 * #%L
 * FirstProject
 * %%
 * Copyright (C) 2022 PROKOMP
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.io.Serializable;
import java.util.ResourceBundle;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import sudoku.exceptions.WrongValueException;

public class SudokuField implements Serializable, Cloneable, Comparable<SudokuField> {

    private int value;
    private final transient ResourceBundle bundle = ResourceBundle.getBundle("Language");

    public int getValue() {
        return value;
    }

    public void setValue(int newValue) {

        if (newValue < 0 || newValue > 9) {
            throw new WrongValueException(bundle.getString("_badValue"));
        }
        this.value = newValue;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(value).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return new EqualsBuilder().append(value, ((SudokuField) obj).value).isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("value", value).toString();
    }

    @Override
    public int compareTo(SudokuField obj) {
        if (obj == null) {
            throw new NullPointerException("Wrong object!");
        }
        if (this.getValue() == obj.getValue()) {
            return 0;
        } else if (this.getValue() > obj.getValue()) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        SudokuField sudokuField = new SudokuField();
        sudokuField.value = this.value;
        return sudokuField;
    }
}
