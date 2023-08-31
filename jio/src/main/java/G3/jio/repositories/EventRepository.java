package G3.jio.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import G3.jio.entities.Event;

@Repository
public class EventRepository {

    private JdbcTemplate jdbcTemplate;

    // Autowired the JdbcTemplate with constructor injection
    public EventRepository(JdbcTemplate template) {
        this.jdbcTemplate = template;
    }

    /**
     * We need to return the auto-generated id of the insert operation
     * 
     */
    public Long save(Event event) {
        // Use KeyHolder to obtain the auto-generated key from the "insert" statement
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        String sql = "INSERT INTO events (event_id, name, start_date, end_date, description, capacity, event_type, venue) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setLong(1, event.getEventId());
                statement.setString(2, event.getName());
                statement.setDate(3, Date.valueOf(event.getStartDate()));
                statement.setDate(4, Date.valueOf(event.getEndDate()));
                statement.setString(5, event.getDescription());
                statement.setInt(6, event.getCapacity());
                statement.setString(7, event.getEventType());
                statement.setString(8, event.getVenue());
                return statement;
            }
        }, holder);

        Long primaryKey = holder.getKey().longValue();
        return primaryKey;
    }

    /**
     * TODO: Activity 1 - Implement the update method
     * 
     * This method nees to return the number of rows affected by the update
     */
    public int update(Event event) {
        return jdbcTemplate.update(
                "UPDATE events SET name = ?, startDate = ?, endDate = ?, description = ?, capacity = ?, eventType = ?, venue = ? WHERE eventId = ?",
                event.getName(), Date.valueOf(event.getStartDate()), Date.valueOf(event.getEndDate()),
                event.getDescription(), event.getCapacity(), event.getEventType(), event.getVenue(),
                event.getEventId());
    }

    /**
     * Return the number of rows affected by the delete
     */
    public int deleteById(Long eventId) {
        return jdbcTemplate.update(
                "delete events where id = ?", eventId);
    }

    /**
     * TODO: Activity 1 - Add code to return all events
     * Hint: use the "query" method of JdbcTemplate
     * Refer to the below code of "findByID" method on how to implement a RowMapper
     * using a lambda expression
     * 
     */
    public List<Event> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM events",
                (rs, rowNum) -> new Event(
                        rs.getLong("event_id"),
                        rs.getString("name"),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate(),
                        rs.getString("description"),
                        rs.getInt("capacity"),
                        rs.getString("event_type"),
                        rs.getString("venue")));
    }

    /**
     * QueryForObject method: to query a single row in the database
     * 
     * The "select *" returns a ResultSet (rs)
     * The Lambda expression (an instance of RowMapper) returns an object instance
     * using "rs"
     * 
     * Optional: a container which may contain null objects
     * -> To handle the case in which the given id is not found
     */
    public Optional<Event> findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM events WHERE event_id = ?",
                    // implement RowMapper interface to return the event found
                    // using a lambda expression
                    (rs, rowNum) -> Optional.of(new Event(
                            rs.getLong("event_id"),
                            rs.getString("name"),
                            rs.getDate("start_date").toLocalDate(),
                            rs.getDate("end_date").toLocalDate(),
                            rs.getString("description"),
                            rs.getInt("capacity"),
                            rs.getString("event_type"),
                            rs.getString("venue"))),
                    new Object[] { id });
        } catch (EmptyResultDataAccessException e) {
            // event not found - return an empty object
            return Optional.empty();
        }
    }
}
