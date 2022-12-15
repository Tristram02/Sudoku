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
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class SudokuObject implements Cloneable, Serializable {
    private final List<SudokuField> field;

    public SudokuObject(List<SudokuField> field) {
        if (field.size() != 9) {
            throw new RuntimeException("Wrong object size!");
        }
        this.field = field;
    }

    public List<SudokuField> getField() {
        return field;
    }

    public void setValuesOfObject(List<SudokuField> values) {
        for (int i = 0; i < 9; i++) {
            field.set(i, values.get(i));
        }
    }

    public void printValuesOfObject() {
        for (int i = 0; i < 9; i++) {
            System.out.print(field.get(i).getFieldValue());
        }
    }

    public boolean verify() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if ((i != j && field.get(i).getFieldValue() == field.get(j).getFieldValue()) || field.get(j).getFieldValue() == 0) {
                    return false;
                }

            }
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        return new EqualsBuilder().append(field, ((SudokuObject) obj).field).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17,37).append(field).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("field", field).toString();
    }
}
