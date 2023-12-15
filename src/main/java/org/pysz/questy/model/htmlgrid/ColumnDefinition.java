package org.pysz.questy.model.htmlgrid;

public record ColumnDefinition(String field,
                               String title,
                               String tooltip,
                               boolean frozen) {
}
