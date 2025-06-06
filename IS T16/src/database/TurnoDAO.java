package database;

import entity.EntityTurno;
import exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;


public class TurnoDAO {

  private static final String INSERT_TURNO = "INSERT INTO Turno (id, giorno, tipo_turno, mail_farmacista) VALUES (?, ?, ?, ?)";


  public void addTurno(EntityTurno t) {
    try (Connection connection = DBManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TURNO)) {
            preparedStatement.setInt(1, t.getId());
            preparedStatement.setDate(2, new java.sql.Date(t.getGiorno().getTime()));
            preparedStatement.setInt(3, t.getTipoTurno());
            preparedStatement.setString(4, t.getMailFarmacista());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error adding Farmacia: " + e.getMessage());
        }
  }
  public List<EntityTurno> getTurniFarmacista(String mail, Date g) {
    // TODO: implement this method
    return null;
  }

}