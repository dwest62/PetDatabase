package cli_helpers;

import java.util.ArrayList;

public class TableBuilder
{
    private final ArrayList<Field> fields;

    public TableBuilder(ArrayList<Field> fields)
    {
        this.fields = fields;
    }

    public String buildHeader()
    {
        String[] entries = StringIntPair.extractStrings(fields.toArray(new Field[0]));
        return buildHorizontalBar() +
                buildRow(entries) +
                buildHorizontalBar();
    }

    public String buildHorizontalBar()
    {
        StringBuilder s = new StringBuilder("+");
        int totalWidth = 0;
        for (Field field : fields)
            totalWidth += field.getWidth();
        return s.append("-".repeat(Math.max(0, totalWidth + fields.size() - 1))).append("+\n").toString();
    }

    public String buildRow(String[] row)
    {
        StringBuilder s = new StringBuilder("|");
        for (int i = 0; i < row.length; i++)
            s.append(" ")
                    .append(row[i])
                    .append(" ".repeat(Math.max(0, fields.get(i).getWidth() - row[i].length() - 1)))
                    .append("|");
        return s.append('\n').toString();
    }

    public String buildFooter(int nRows)
    {
        return buildHorizontalBar() + nRows + " entries in table.\n";
    }

    public String buildAutoSizeTable(String[][] table)
    {
        autoFitWidths(table);
        StringBuilder s = new StringBuilder(buildHeader());
        for (String[] row : table)
            s.append(buildRow(row));

        return s.append(buildFooter(table.length)).toString();
    }

    public void autoFitWidths(String[][] rows)
    {
        for (String[] row : rows)
            for (int i = 0; i < row.length; i++)
                fields.get(i).setWidth(Math.max(row[i].length() + 2, fields.get(i).getWidth()));
    }

    public void addField(Field field)
    {
        fields.add(field);
    }

    public void addField(Field field, int index)
    {
        fields.add(index, field);
    }

    public void removeField(Field field)
    {
        fields.remove(field);
    }

    public void removeField(int index)
    {
        fields.remove(index);
    }
}
