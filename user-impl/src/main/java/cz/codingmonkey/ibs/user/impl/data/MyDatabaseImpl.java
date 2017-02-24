package cz.codingmonkey.ibs.user.impl.data;

import cz.codingmonkey.ibs.user.impl.ClientEvent;
import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author rstefanca
 */
public class MyDatabaseImpl implements MyDatabase {

	private final Logger log = LoggerFactory.getLogger(MyDatabaseImpl.class);
	private final QueryRunner queryRunner = new QueryRunner();

	@Override
	public void createTables(Connection connection) throws SQLException {
		log.info("creating tables");
		try (PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS clients ( " +
				"id VARCHAR(64), externalId VARCHAR(256), sms VARCHAR(15), email VARCHAR(255), PRIMARY KEY (id))")) {
			ps.execute();
		}
	}

	@Override
	public void createClient(Connection connection, ClientEvent.ClientCreated e) throws SQLException {
		log.info("handling db create client");
		int updated = queryRunner.update(
				connection,
				"INSERT INTO CLIENTS(id, externalId, sms, email) values (?,?,?,?)",
				e.id, e.client.externalId, e.client.sms, e.client.email);

		log.info("{} rows updated", updated);
	}

	@Override
	public void deactivateClient(Connection connection, ClientEvent.ClientDeactivated e) throws SQLException {

	}
}