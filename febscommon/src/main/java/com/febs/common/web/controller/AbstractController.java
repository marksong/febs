package com.febs.common.web.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public abstract class AbstractController<T> {

	public abstract String modelName();
	
	@RequestMapping
	public String list() {
		System.out.println("test");
		return modelName() + "/list";
	}
	
	@RequestMapping(value="/{id}")
	public String show(@PathVariable("id") String id) {
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
    public String edit(@PathVariable("id") String id) throws Exception {  
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
