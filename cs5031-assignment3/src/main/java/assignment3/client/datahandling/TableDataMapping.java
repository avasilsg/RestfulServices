package assignment3.client.datahandling;


import java.util.Vector;

import javax.swing.table.AbstractTableModel;

/**
 * @author asif this class is handling the data table for rendering
 * 
 */
public class TableDataMapping extends AbstractTableModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    Vector<Vector<String>> model = new Vector<Vector<String>>();
    Vector<String> header = new Vector<String>();

    public TableDataMapping(Vector<Vector<String>> model, Vector<String> header) {

        this.model = model;
        this.header = header;
    }

    @Override
    public int getColumnCount() {
        return header.size();
    }

    @Override
    public String getColumnName(int column) {
        return header.get(column);
    }

    @Override
    public int getRowCount() {
        // TODO Auto-generated method stub
        return model.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // TODO Auto-generated method stub
        return model.get(rowIndex).get(columnIndex);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        // Only the third column
        return row > 0;
    }

}
