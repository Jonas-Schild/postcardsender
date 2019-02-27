package ch.schildj.postcardsender.domain.model.dto;

import java.util.List;

/**
 * Transferobject
 * This class contains a list for a pagination and the count of all rows in the database.
 */
public class DataSizeAndDataDTO<T> {
    private int dataSize;
    private List<T> data;

    public DataSizeAndDataDTO(int dataSize, List<T> data) {
        this.dataSize = dataSize;
        this.data = data;
    }

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}