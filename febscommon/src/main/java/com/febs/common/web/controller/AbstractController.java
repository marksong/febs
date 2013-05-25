package com.febs.common.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.febs.common.orm.PaginationSupport;
import com.febs.common.service.AbstractCrudService;

public abstract class AbstractController<T> {

	protected Logger log = LoggerFactory.getLogger(getClass());
	protected static final String ENTITY = "entity";
	
	public abstract String modelName();
	public abstract AbstractCrudService<T, String> modelService();
	
	@RequestMapping
	public String list(@RequestParam(value="page", required=false) PaginationSupport<T> page, Model model) {
		if (page == null) {
			page = new PaginationSupport<>();
		}
		modelService().findPage(page);
		model.addAttribute("page", page);
		return modelName() + "/list";
	}
	
	@RequestMapping(value="/{id}")
	public String show(@PathVariable("id") String id, Model model) {
		model.addAttribute(ENTITY, modelService().findById(id));
		return modelName() + "/show";
	}
	
	@RequestMapping(value="/add")
	public String add() {
		return modelName() + "/add";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String addSubmit(T user) {
		return modelName() + "/list";
	}
	
	@RequestMapping(value="/{id}/edit")  
    public String edit(@PathVariable("id") String id, Model model) throws Exception {
		model.addAttribute(ENTITY, modelService().findById(id));
        return modelName() + "/edit";
    }
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public String editSubmit(@PathVariable("id") String id, T user) throws Exception {  
        return modelName() + "/edit";
    }
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)  
    public String delete(@PathVariable("id") String id) {  
        return modelName() + "/list";
    }  
  
    @RequestMapping(method=RequestMethod.DELETE)
    public String batchDelete(@RequestParam("ids") String[] ids) {  
        return modelName() + "/list"; 
    }  
}
