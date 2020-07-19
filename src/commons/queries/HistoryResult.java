package commons.queries;

import commons.dataTypes.History;

import java.io.Serializable;
import java.util.LinkedList;

public class HistoryResult implements Serializable {
    LinkedList<History> historyList;

    public HistoryResult(LinkedList<History> historyList) {
        this.historyList = historyList;
    }
}
