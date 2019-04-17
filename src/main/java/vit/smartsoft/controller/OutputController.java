package vit.smartsoft.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import vit.smartsoft.dao.GorodDaoImplJdbc;
import vit.smartsoft.entity.Gorod;

@Controller
public class OutputController {
	
	@Autowired
	private GorodDaoImplJdbc dao;
	
	@RequestMapping("/top")
	public String getTop5Forms(Model uiModel) {
		List<String> forms = dao.getTop5Forms();		
		uiModel.addAttribute("forms", forms);
		return "topforms";
		
	}
	
	@RequestMapping("/import")
	public String doImport(Model uiModel) throws IOException {
		Integer lines = dao.populate();
		uiModel.addAttribute("lines", lines);
		return "importresult";
	}
	
	@RequestMapping("/pend")
	public String getPending(Model uiModel) throws IOException {
		List<Gorod> pending = dao.getPending();
		uiModel.addAttribute("pending", pending);
		return "pendingusers";
	}
	
	@RequestMapping("/active")
	public String getActive(Model uiModel) throws IOException {
		List<Gorod> active = dao.getLastHourActive();
		uiModel.addAttribute("active", active);
		return "activeusers";
	}
}
