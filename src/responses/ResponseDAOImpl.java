/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package responses;
import database.DBConnection;
import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDateTime;
/**
 *
 * @author Lenovo
 */
public class ResponseDAOImpl implements ResponseDAO{
   
    public Response getResponseByUserId(int response_id){
        String query = "SELECT * FROM responses WHERE response_id = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, response_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Response(
                    rs.getInt("response_id"),
                    rs.getInt("request_id"),
                    rs.getInt("responder_id"),
                    rs.getString("comment"),
                    rs.getString("status_update"),
                    rs.getString("schedule_datetime"),
                    rs.getString("date_responded")
                );
            }
        } catch (SQLException e) { 
                e.printStackTrace(); 
        }
        return null;
    }
    public ArrayList<Response> getAllResponses(int user_id) {
    ArrayList<Response> responses = new ArrayList<>();
    String query = "SELECT response.* FROM responses response " +
                   "JOIN all_requests request ON response.request_id = request.request_id " +
                   "WHERE request.user_id = ? ORDER BY response.date_responded DESC";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
         
        pstmt.setInt(1, user_id);
        ResultSet rs = pstmt.executeQuery();  // ✅ No query argument here

        while (rs.next()) {
            Response response = new Response(
                rs.getInt("response_id"),
                rs.getInt("request_id"),
                rs.getInt("responder_id"),
                rs.getString("comment"),
                rs.getString("status_update"),
                rs.getString("schedule_datetime"),
                rs.getString("date_responded")
            );
            responses.add(response);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return responses;
}
    public boolean saveResponse(Response response) {
    String sql = "INSERT INTO responses (request_id, responder_id, comment, status_update, schedule_datetime, date_responded) VALUES (?, ?, ?, ?, ?, NOW())";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, response.getRequest_id());
        stmt.setInt(2, response.getResponder_id());
        stmt.setString(3, response.getComment());
        stmt.setString(4, response.getStatus_update()); // e.g. "Approved", "Rejected"
        stmt.setTimestamp(5, Timestamp.valueOf(response.getSchedule_Datetime()));
        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

}