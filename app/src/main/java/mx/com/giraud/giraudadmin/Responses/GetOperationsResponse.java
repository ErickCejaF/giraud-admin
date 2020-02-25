package mx.com.giraud.giraudadmin.Responses;

import java.util.ArrayList;

import mx.com.giraud.giraudadmin.Models.OperationModel;

public class GetOperationsResponse {
    ArrayList<OperationModel> data;

    public ArrayList<OperationModel> getData() {
        return data;
    }

    public void setData(ArrayList<OperationModel> data) {
        this.data = data;
    }
}
