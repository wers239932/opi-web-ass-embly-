package beans;

import model.Attempt;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.sql.*;
import java.util.Properties;

public class Results {
    private int currentPage = 1;
    private final int pageSize;
    private int maxPage;
    private final Connection connection;
    private List<Attempt> results;
    private int totalSize;

    public Results() {
        try(InputStream inputStream = Results.class.getClassLoader().getResourceAsStream("web3.properties");) {
            Properties properties = new Properties();

            properties.load(inputStream);
            this.pageSize = Integer.parseInt(properties.getProperty("pageSize"));
            this.connection = DriverManager.getConnection(
                    properties.getProperty("url"),
                    properties.getProperty("login"),
                    properties.getProperty("password"));
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) as count FROM Results");
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            this.totalSize = resultSet.getInt("count");
            this.maxPage = (int) Math.ceil((double)this.totalSize / this.pageSize);
            this.results = loadResults(this.pageSize);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Attempt> getResults() {
        return results;
    }

    private List<Attempt> loadResults(int limit) {
        List<Attempt> results = new LinkedList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Results ORDER BY sentAt DESC LIMIT ? OFFSET ?");
            statement.setInt(1, limit);
            statement.setInt(2, (this.currentPage - 1) * this.pageSize);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Attempt attempt = getAttemptFromResultSet(rs);
                results.add(attempt);
            }
            statement.close();

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return results;
    }

    public void addResult(Attempt attempt) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Results(x, y, r, result, sentAt, workingTime) VALUES(?, ?, ?, ?, ?, ?)");
            statement.setInt(1, attempt.getX());
            statement.setDouble(2, attempt.getY());
            statement.setDouble(3, attempt.getR());
            statement.setBoolean(4, attempt.getResult());
            statement.setTimestamp(5, Timestamp.valueOf(attempt.getSentAt()));
            statement.setLong(6, attempt.getWorkingTime());
            statement.executeUpdate();
            statement.close();
            this.totalSize++;
            if (this.totalSize % this.pageSize == 1) {
                this.maxPage++;
            }
            this.results.add(0, loadResults(1).get(0));
            if(this.results.size() == this.pageSize) {
                this.results.remove(this.results.size() - 1);
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void nextPage() {
        if(this.currentPage >= this.maxPage)
            return;
        this.currentPage++;
        this.results = this.loadResults(this.pageSize);
    }
    public void previousPage() {
        if (this.currentPage == 1)
            return;
        this.currentPage--;
        this.results = this.loadResults(this.pageSize);
    }

    private Attempt getAttemptFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int x = rs.getInt("x");
        double y = rs.getDouble("y");
        double r = rs.getDouble("r");
        boolean result = rs.getBoolean("result");
        LocalDateTime sentAt = rs.getTimestamp("sentAt").toLocalDateTime();
        long workingTime = rs.getLong("workingTime");
        Attempt attempt = new Attempt(x, y, r, sentAt);
        attempt.setResult(result);
        attempt.setId(id);
        attempt.setWorkingTime(workingTime);
        return attempt;
    }
}
