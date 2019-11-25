package com.prisonerprice.jdbc;

import com.prisonerprice.model.Artist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArtistDAO {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private MyConnection myConnection = new MyConnection();
    private ResultSet rs = null;

    public List<Artist> getArtists(){
        logger.info("Enter the method getArtists");
        List<Artist> artists = new ArrayList<>();

        try {
            rs = myConnection.connectAndFetchResult("SELECT * FROM artist");
            while(rs.next()){
                artists.add(new Artist(
                        rs.getString("name"),
                        rs.getInt("start_year"),
                        rs.getInt("end_year"),
                        rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                myConnection.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        logger.info("Exit the method getArtists");
        return artists;
    }

    public void insertArtists(Artist artist){
        logger.info("Enter the method insertArtists");
        String sqlQuery = "INSERT INTO artist (name, start_year, end_year, description) " +
                "VALUES (" +
                "'" + artist.getName() + "', " +
                artist.getStart_year() + ", " +
                artist.getEnd_year() + ", " +
                "'" + artist.getDescription() + "');";
        doAQuery(sqlQuery);
    }

    public void deleteArtists(){
        logger.info("Enter the method deleteArtists");
        String sqlQuery = "DELETE FROM artist WHERE id > 7";
        doAQuery(sqlQuery);
    }

    public void updateArtists(){
        logger.info("Enter the method updateArtists");
        String sqlQuery = "UPDATE artist SET description = 'Need to update' WHERE description IS NULL;";
        doAQuery(sqlQuery);
    }

    public void doAQuery(String sqlQuery){
        try {
            myConnection.connectAndFetchResult(sqlQuery);
        } catch (SQLException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                myConnection.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
