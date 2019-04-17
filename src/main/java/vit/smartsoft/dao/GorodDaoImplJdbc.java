package vit.smartsoft.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import vit.smartsoft.entity.Gorod;

@Repository
public class GorodDaoImplJdbc extends NamedParameterJdbcDaoSupport implements GorodDao {

	@Autowired
	DataSource dataSource;
	
	@PostConstruct
	private void init() {
		setDataSource(dataSource);
	}

	@Override
	public List<Gorod> getLastHourActive() {
			String sql = "select distinct ssoid, formid from " + 
				"(select ssoid, formid, ts from gorod) a, " + 
				"(select max(ts) mts from gorod) b " + 
				"where mts - ts <=3600000";
			List<Gorod> activeUsersList = getNamedParameterJdbcTemplate().query(sql, new ActiveMapper());
			return activeUsersList;
	}

	@Override
	public List<Gorod> getPending() {
		String sql = "select distinct a.ssoid, b.subtype from " +
				"(select ssoid, grp, max(ts) mts from gorod " +
				"group by ssoid, grp) a join " +
				"(select ssoid, grp, subtype, ts from gorod) b " +
				"on a.ssoid = b.ssoid and a.grp = b.grp and mts = b.ts " +
				"where b.subtype <> 'send';";
		List<Gorod> pendingList = getNamedParameterJdbcTemplate()
				.query(sql, new PendingMapper());
		
		System.out.println("===============================================");
		System.out.println(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
		System.out.println(System.getProperty("user.dir"));
		System.out.println(System.getProperty("user.home"));
		System.out.println("===============================================");
		return pendingList;
	}

	@Override
	public List<String> getTop5Forms() {
		String sql = "select formid from " +
		"(select formid, count(formid) as cnt from gorod " +
		 "where formid <> '' " +
		 "group by formid " +
		 "order by cnt desc limit 5) d";
		List<String> topForms = getNamedParameterJdbcTemplate().queryForList(sql, new HashMap());
		return topForms;
	}

	@Override
	public Integer populate() throws IOException{
		String ssoid, grp, type, subtype, url, orgid, formid, code, ltpa, sudirresponse, ymdh;
		Long ts;
		String path = System.getProperty("user.home") + "\\test_case.csv";
//		File file = new File("C:\\_VIT\\test_case.csv");
		File file = new File(path);
		Scanner scanner = new Scanner(file);
		scanner.nextLine();
		int lineNumber = 1;
		
		while(scanner.hasNextLine()) {
			lineNumber++;
			String line = scanner.nextLine();
			String [] dataRow = line.split(";");
			int len = dataRow.length;
			if (len < 12) {
				throw new IOException("Bad data split in line " + lineNumber);
			} else if (len > 12) {//extra comas can be found in url
				int dif = len - 12;
				StringBuilder sb = new StringBuilder();
				for (int i = 5; i < 5 + dif; i++) {
					sb.append(dataRow[i]);
				}
				url = sb.toString();
				orgid = dataRow[6+dif];
				formid = dataRow[7+dif];
				code = dataRow[8+dif];
				ltpa = dataRow[9+dif];
				sudirresponse = dataRow[10+dif];
				ymdh = dataRow[11+dif];
			} else {
				url = dataRow[5];
				orgid = dataRow[6];
				formid = dataRow[7];
				code = dataRow[8];
				ltpa = dataRow[9];
				sudirresponse = dataRow[10];
				ymdh = dataRow[11];
			}
			ssoid = dataRow[0];
			ts = Long.valueOf(dataRow[1]);
			grp = dataRow[2];
			type = dataRow[3];
			subtype = dataRow[4];
					
			InsertGorod igorod = new InsertGorod(dataSource);
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("ssoid", ssoid);
			paramMap.put("ts", ts);
			paramMap.put("grp", grp);
			paramMap.put("type", type);
			paramMap.put("subtype", subtype);
			paramMap.put("url", url);
			paramMap.put("orgid", orgid);
			paramMap.put("formid", formid);
			paramMap.put("code", code);
			paramMap.put("ltpa", ltpa);
			paramMap.put("sudirresponse", sudirresponse);
			paramMap.put("ymdh", ymdh);
			
			igorod.updateByNamedParam(paramMap);
			if (lineNumber % 50 == 0) {
				igorod.flush();
			}
		}		
		return lineNumber - 1;
	}
	
	public static final class ActiveMapper implements RowMapper<Gorod> {

		@Override
		public Gorod mapRow(ResultSet rs, int rowNum) throws SQLException {
			Gorod gorod = new Gorod();
			gorod.setSsoid(rs.getString("ssoid"));
			gorod.setFormid(rs.getString("formid"));
			return gorod;
		}
		
	}
	public static final class PendingMapper implements RowMapper<Gorod> {

		@Override
		public Gorod mapRow(ResultSet rs, int rowNum) throws SQLException {
			Gorod gorod = new Gorod();
			gorod.setSsoid(rs.getString("ssoid"));
			gorod.setSubtype(rs.getString("subtype"));
			return gorod;
		}
		
	}
}
