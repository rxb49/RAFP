package fr.univangers.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Sql {

	private static final Logger logger = LoggerFactory.getLogger(Sql.class);

	public static void close(Connection con) throws SQLException {
		logger.info("Deconnexion BDD.");
		if (con != null)
			con.close();
	}
	public static void close(PreparedStatement cstmt) throws SQLException {
		if (cstmt != null)
			cstmt.close();
	}

	public static void close(ResultSet rs) throws SQLException {
		if (rs != null)
			rs.close();
	}
}