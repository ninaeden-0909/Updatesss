/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package all_requests;

import java.util.ArrayList;

/**
 *
 * @author Lenovo
 */
public interface RequestDAO {
    public boolean create(Request request);
    public Request read_one(int request_id);
    public ArrayList<Request> read_all();
    public boolean approveRequest(int requestId, String decision, String remarks, int approvedBy);
    public ArrayList<Request> readFiltered(String type,String priority);
    /*public boolean delete(int request_id);
    public boolean update(Request request);*/
    public ArrayList<Request> searchRequest(String str);
    public ArrayList<Request> readByStatus(String status);

}
