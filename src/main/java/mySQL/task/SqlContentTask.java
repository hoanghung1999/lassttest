package mySQL.task;



import mySQL.ConnectSQL.CreatConnector;
import oop.Content;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlContentTask {
    public int insertContent(Content content){
        int result=-1;
        String sql="INSERT IGNORE INTO content(content_name,content_type)" +
                "Values(?,?)";
        CreatConnector con=new CreatConnector();
        Connection connection= con.getConnect();
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,content.getContent_name());
            preparedStatement.setString(2,content.getContent_type());

             result=preparedStatement.executeUpdate();
             preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
                System.out.println("disconneted");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public int updateContent(Content content){
        int result=-1;
        String sql="Update Content SET " +

                "content_name ="+"'"+content.getContent_name()+"',"+
                "content_type="+"'"+content.getContent_type()+"' "+
                "where content_id ="+content.getContent_id();


        CreatConnector con=new CreatConnector();
        Connection connection=con.getConnect();
        try {
            PreparedStatement ps= connection.prepareStatement(sql);
            result= ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
                System.out.println("disconnected");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    };

    public int deleteContent(int id){
        int result=-1;
        String sql="DELETE FROM Content WHERE content_id="+id;
        CreatConnector con= new CreatConnector();
        Connection connection= con.getConnect();
        try {
            PreparedStatement ps= connection.prepareStatement(sql);
            result= ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
                System.out.println("disconnected");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public Content getContentByID(int id){
        String sql="SELECT * FROM Content WHERE content_id="+id;
        CreatConnector con= new CreatConnector();
        Connection connection= con.getConnect();
        Content content=null;
        try {
            PreparedStatement ps= connection.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                content= new Content(rs.getInt(1),rs.getString(2),rs.getString(3));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
                System.out.println("disconnected");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return content;
    }
    public List<Content> getAllContent(){
        String sql="SELECT * FROM content ";

        List<Content> list= new ArrayList<>();
        CreatConnector con= new CreatConnector();
        Connection connection= con.getConnect();
        try {
            PreparedStatement ps= connection.prepareStatement(sql);
            ResultSet rs= ps.executeQuery();
            while (rs.next()){
               Content content= new Content(rs.getInt(1),rs.getString(2),rs.getString(3));
                list.add(content);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
                System.out.println("disconnected");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        }
    }
    public List<Content> getAllContentByTourId(int tour_id){
        String sql="SELECT c.* \n" +
                "FROM content c JOIN content_tour ct on c.content_id=ct.content_id\n" +
                "WHERE ct.tour_id="+tour_id;
        List<Content> list= new ArrayList<>();
        CreatConnector con= new CreatConnector();
        Connection connection= con.getConnect();
        try {
            PreparedStatement ps= connection.prepareStatement(sql);
            ResultSet rs= ps.executeQuery();
            while (rs.next()){
                Content content= new Content(rs.getInt(1),rs.getString(2),rs.getString(3));
                list.add(content);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
                System.out.println("disconnected");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        }
    }
    // cap nhat noi dung cho bang content_tour khi cai dat tournament
    public int insertContentTour(List<Integer> list,int tour_id){
        String sql="INSERT IGNORE INTO content_tour(tour_id,content_id) " +
                "Values(?,?)";
        // cap nhat noi dung cho bang content tour
        CreatConnector con=new CreatConnector();
        Connection connection= con.getConnect();
        try {
            PreparedStatement preparedStatement=null;
            // cap nhat bang content_tour bang tung content duoc chon cua tournament khi cai dat tournament
            for(int i:list) {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1,tour_id);
                preparedStatement.setInt(2,i);

                preparedStatement.executeUpdate();

            }
            preparedStatement.close();
             return 1;// Thêm nội dung thi đấu cho giả đấu thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
                System.out.println("disconneted");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;// lỗi kết nối or thêm ko thành công
    }



}
