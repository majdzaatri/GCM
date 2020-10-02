package g6.gcm.server.manager.daos;

import g6.gcm.core.interfaces.AbstractDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class gives a template for implementing Data Access Objects.
 */
public abstract class AbstractDAO {

    /**
     * This is the only method that should be called for data retrieval/insertion/deletion/... Sets
     * the right query and activates the needed method.
     *
     * @param theDTO is the theDTO to be processed
     * @return the queries answer as a DTO (Object)
     */
    public abstract Object processQuery(AbstractDTO theDTO);

    /**
     * each connection must be closed after each execution of a SQL query. Also, each Resultset must
     * be closed. The way it happens here is by declaring them inside "()" of the try clause.
     */
    public AbstractDTO getOne(PreparedStatement statement) {

        AbstractDTO abstractDTO = null;

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return extractFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return abstractDTO;
    }

    public int InsertInto(PreparedStatement statement) {

        AbstractDTO abstractDTO = null;

        int inserted = 0;
        try {
            inserted = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inserted;
    }

    /**
     * @return Set of all users in database.
     */
    public List<? extends AbstractDTO> getAll(PreparedStatement statement) {

        List<AbstractDTO> dtoList = null;

        try (ResultSet resultSet = statement.executeQuery()) {
            dtoList = new ArrayList<>();

            while (resultSet.next()) {
                AbstractDTO abstractDTO = extractFromResultSet(resultSet);
                dtoList.add(abstractDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dtoList;
    }

    /**
     * This method extracts all columns from database to the DTO.
     *
     * @param resultSet is the result set that came from the SQL query.
     * @return The DTO.
     */
    abstract protected AbstractDTO extractFromResultSet(ResultSet resultSet);

    public boolean insert(PreparedStatement statement) {

        boolean resultSet = false;
        try {
            statement.execute();
            statement.close();
            resultSet = true;
        } catch (SQLIntegrityConstraintViolationException integrityConstraintViolationException) {
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        }
        return resultSet;
    }

    public int update(PreparedStatement statement) {
        int resultSet = -1;
        try {
            resultSet = statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        }
        return resultSet;
    }

    public int delete(PreparedStatement statement) {
        int resultSet = -1;
        try {
            resultSet = statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        }
        return resultSet;
    }

    public Integer getCount(PreparedStatement statement) {

        /* Indicating it's invalid */
        int answer = -1;

        try (ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                answer = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answer;
    }


}
