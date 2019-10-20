package shortener;

import javax.inject.Singleton;

import com.google.common.collect.ImmutableMap;
import play.Logger;
import play.db.Database;
import play.db.Databases;
import com.typesafe.config.ConfigFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class URLDao {

    private Database db;

    private Logger.ALogger logger = Logger.of(URLDao.class);

    private QueryHelper queryHelper = new QueryHelper();

    public URLDao() {
        String DRIVER_NAME = ConfigFactory.load().getString("db.default.driver");
        String URL = ConfigFactory.load().getString("db.default.url");
        String USER_NAME = ConfigFactory.load().getString("db.default.username");
        String PASSWORD = ConfigFactory.load().getString("db.default.password");
        db = Databases.createFrom(DRIVER_NAME, URL, ImmutableMap.of("username", USER_NAME, "password", PASSWORD));
    }

    public long getIdByInsertLongURL(String longURL) {
        Connection connection = db.getConnection();
        try {
            String query = queryHelper.insertLongURL(longURL);
            PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            long id = rs.getLong(1);
            logger.debug("Insert the long url and get the id {}.", id);
            connection.close();
            return id;
        } catch (Exception e) {
            logger.error("getIdByInsertLongURL: found Exception {}", e);
        }
        return -1;
    }

    public void populateShortURL(long id, String shortURL) {
        Connection connection = db.getConnection();
        logger.debug("Populate short url {} to url_profile tables.", shortURL);
        try {
            Statement stmt = connection.createStatement();
            String query = queryHelper.populateShortURLById(id, shortURL);
            stmt.execute(query);
            connection.close();
        } catch (Exception e) {
            logger.error("populateShortURL: found Exception {}", e);
        }
    }

    public String getLongURLById(long id) {
        Connection connection = db.getConnection();
        try {
            Statement stmt = connection.createStatement();
            String query = queryHelper.getLongURLById(id);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                return rs.getString(URLConstants.Long_URL);
            }
        } catch (Exception e) {
            logger.error("getLongURLById: found Exception {}", e);
        }
        return "";
    }

    public void logURLInsertion(long id, String shortURL) {
        Connection connection = db.getConnection();
        try {
            String query = queryHelper.insertURLLog();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, id);
            stmt.setString(2, shortURL);
            stmt.execute();
            connection.close();
        } catch (Exception e) {
            logger.error("logURLInsertion: found Exception {}", e);
        }
    }

    public List<URLInfo> getAllLogInfoFromId(long id) {
        Connection connection = db.getConnection();
        List<URLInfo> urlInfoList = new ArrayList<>();
        try {
            String query = queryHelper.getShortURLLogFromId(id);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            logger.debug("Start get all log info by id.");
            while (rs.next()) {
                String shortURL = rs.getString(URLConstants.SHORT_URL);
                LocalDateTime createdDatetime = rs.getTimestamp(URLConstants.CREATED_DATETIME).toLocalDateTime();
                urlInfoList.add(new URLInfo(shortURL, createdDatetime));
            }
            connection.close();
        } catch (Exception e) {
            logger.error("getAllLogInfoFromId: found Exception {}", e);
        }
        return urlInfoList;
    }
}
