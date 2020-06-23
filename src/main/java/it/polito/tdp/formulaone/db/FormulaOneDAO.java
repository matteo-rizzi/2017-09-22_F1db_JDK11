package it.polito.tdp.formulaone.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.formulaone.model.Adiacenza;
import it.polito.tdp.formulaone.model.Driver;
import it.polito.tdp.formulaone.model.LapTime;
import it.polito.tdp.formulaone.model.Race;
import it.polito.tdp.formulaone.model.Season;

public class FormulaOneDAO {

	public List<Season> getAllSeasons() {
		String sql = "SELECT year, url FROM seasons ORDER BY year";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			List<Season> list = new ArrayList<>();
			while (rs.next()) {
				list.add(new Season(rs.getInt("year"), rs.getString("url")));
			}
			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void getRaces(Map<Integer, Race> idMap) {
		String sql = "SELECT raceId, year, round, circuitId, name, date, time, url FROM races";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				if (!idMap.containsKey(rs.getInt("raceId"))) {
					LocalTime time;
					if (rs.getTime("time") == null) {
						time = null;
					} else
						time = rs.getTime("time").toLocalTime();
					Race race = new Race(rs.getInt("raceId"), Year.of(rs.getInt("year")), rs.getInt("round"),
							rs.getInt("circuitId"), rs.getString("name"), rs.getDate("date").toLocalDate(), time,
							rs.getString("url"));
					idMap.put(rs.getInt("raceId"), race);
				}
			}
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Race> getRacesByYear(Map<Integer, Race> idMap, int anno) {
		String sql = "SELECT * FROM races WHERE year = ?";
		List<Race> list = new ArrayList<Race>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);

			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				list.add(idMap.get(rs.getInt("raceId")));
			}
			conn.close();
			return list;

		} catch (

		SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Adiacenza> getAdiacenze(Map<Integer, Race> idMap, int anno) {
		String sql = "SELECT r1.raceId AS primo, r2.raceId AS secondo, COUNT(DISTINCT r1.driverId) AS peso FROM results AS r1, results AS r2, races AS race1, races AS race2 WHERE r1.driverId = r2.driverId AND r1.raceId < r2.raceId AND r1.statusId = 1 AND r2.statusId = 1 AND r1.raceId = race1.raceId AND r2.raceId = race2.raceId AND race1.year = ? AND race1.year = race2.year GROUP BY r1.raceId, r2.raceId";
		List<Adiacenza> list = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);

			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Adiacenza a = new Adiacenza(idMap.get(rs.getInt("primo")), idMap.get(rs.getInt("secondo")),
						rs.getInt("peso"));
				list.add(a);
			}
			conn.close();
			return list;

		} catch (

		SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public LapTime getLapTimes(Race race, Driver driver, int giro) {
		String sql = "SELECT * FROM laptimes WHERE raceId = ? AND driverId = ? AND lap = ?";
		try {
			LapTime lt = null;
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, race.getRaceId());
			st.setInt(2, driver.getDriverId());
			st.setInt(3, giro);

			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				lt = new LapTime(race, driver, rs.getInt("lap"),
						rs.getInt("position"), rs.getString("time"), rs.getInt("milliseconds"));
			}
			conn.close();
			return lt;

		} catch (

		SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Driver> getDriversByRace(Race race, Map<Integer, Driver> idDriver) {
		String sql = "SELECT driverId FROM driverstandings WHERE raceId = ?";
		List<Driver> list = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, race.getRaceId());

			ResultSet rs = st.executeQuery();
			while (rs.next()) {

				list.add(idDriver.get(rs.getInt("driverId")));
			}
			conn.close();
			return list;

		} catch (

		SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void getDrivers(Map<Integer, Driver> idDriver) {
		String sql = "SELECT * FROM drivers";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				if (!idDriver.containsKey(rs.getInt("driverId"))) {
					LocalDate dob;
					if (rs.getDate("dob") == null) {
						dob = null;
					} else
						dob = rs.getDate("dob").toLocalDate();
					Driver driver = new Driver(rs.getInt("driverId"), rs.getString("driverRef"), rs.getInt("number"),
							rs.getString("code"), rs.getString("forename"), rs.getString("surname"), dob,
							rs.getString("nationality"), rs.getString("url"));
					idDriver.put(rs.getInt("driverId"), driver);
				}
			}
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
