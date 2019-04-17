package vit.smartsoft.dao;

import java.io.IOException;
import java.util.List;

import vit.smartsoft.entity.Gorod;

public interface GorodDao {
	List<Gorod> getLastHourActive();
	List<Gorod> getPending();
	List<String> getTop5Forms();
	Integer populate() throws IOException;
}
