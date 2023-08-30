package Data;

import com.example.server.Stats;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.TreeMap;

public class Repository {

    private HikariCP data = new HikariCP();
    private Connection con;
    private PreparedStatement updateStatement;

    public Repository() throws SQLException {
        Connection con = data.getConnection();
         updateStatement = con.prepareStatement("UPDATE tank.stats SET total_shots = ?, hits = ?, missed = ? WHERE id = ?;");
    }

    public TreeMap<String, Integer> getStats(int id){
        try {
            TreeMap<String, Integer> stats = new TreeMap<>();
            Connection con = data.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT total_shots,hits,missed FROM tank.stats WHERE id = " + id + ";");
            rs.next();
            System.out.println("ABOUT TO GO INTO BOU");
            if(rs.getRow() > 0){
                System.out.println("GOT IN BOY " + rs.getInt(1));
                stats.put("total_shots", rs.getInt(1));
                stats.put("hits", rs.getInt(2));
                stats.put("missed", rs.getInt(3));
            }
            return stats;
        }catch (SQLException e){

        }
        return null;
    }

    public void saveStats(@NotNull Stats stat)  {
        try {
            Connection con = data.getConnection();
            PreparedStatement st = con.prepareStatement("INSERT INTO tank.stats(id,total_shots,hits,missed) VALUES(?,?,?,?);");
            st.setInt(1,stat.id);
            st.setInt(2,stat.total_shots);
            st.setInt(3,stat.hits);
            st.setInt(4,stat.missed);
            st.execute();
        } catch(SQLException e){

        }

    }


    public void updateStats(@NotNull Stats stats){
        try {

            updateStatement.setInt(1,stats.total_shots);
            updateStatement.setInt(2,stats.hits);
            updateStatement.setInt(3,stats.missed);
            updateStatement.setInt(4,stats.id);
            updateStatement.execute();
            System.out.println("SQL:update");
        }catch (SQLException e){
            System.out.println("SOMETHING WENT WRONG WITH UPDATE: "+ e.getMessage());
        }
    }

    public void deleteStats(){
        try {
            Connection con = data.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("DELETE FROM TABLE tank.stats;");
        }catch (SQLException e){

        }

    }

}
