package ask.model;

import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatementStream implements PreparedStatementCreator {
    private final String sqlStatement;

    public StatementStream(String sql) {
        this.sqlStatement = sql;
    }

    @Override
    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        final PreparedStatement statement = connection.prepareStatement(sqlStatement, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        statement.setFetchSize(100);
        return statement;
    }
}