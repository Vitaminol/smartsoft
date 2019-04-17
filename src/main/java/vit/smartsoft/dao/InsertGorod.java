package vit.smartsoft.dao;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.BatchSqlUpdate;

public class InsertGorod extends BatchSqlUpdate {
	
	private static final String INSERT_GOROD = "INSERT INTO gorod "
			+ "(ssoid, ts, grp, type, subtype, url, orgid, formid, "
			+ "code, ltpa, sudirresponse, ymdh) VALUES (:ssoid, :ts, "
			+ ":grp, :type, :subtype, :url, :orgid, :formid, :code, "
			+ ":ltpa, :sudirresponse, :ymdh)";
	
	private static final int BATCH_SIZE = 50;
	public InsertGorod(DataSource dataSource) {
		super(dataSource, INSERT_GOROD);
		
		declareParameter(new SqlParameter("ssoid", Types.VARCHAR));
		declareParameter(new SqlParameter("ts", Types.BIGINT));
		declareParameter(new SqlParameter("grp", Types.VARCHAR));
		declareParameter(new SqlParameter("type", Types.VARCHAR));
		declareParameter(new SqlParameter("subtype", Types.VARCHAR));
		declareParameter(new SqlParameter("url", Types.VARCHAR));
		declareParameter(new SqlParameter("orgid", Types.VARCHAR));
		declareParameter(new SqlParameter("formid", Types.VARCHAR));
		declareParameter(new SqlParameter("code", Types.VARCHAR));
		declareParameter(new SqlParameter("ltpa", Types.VARCHAR));
		declareParameter(new SqlParameter("sudirresponse", Types.VARCHAR));
		declareParameter(new SqlParameter("ymdh", Types.VARCHAR));
		
		setBatchSize(BATCH_SIZE);
	}
}
