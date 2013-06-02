package com.febs.common.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.febs.common.orm.PaginationProperty;
import com.febs.common.orm.PaginationSupport;
import com.febs.common.service.AbstractCrudService;

public abstract class AbstractController<T> {

	protected Logger log = LoggerFactory.getLogger(getClass());
	protected static final String ENTITY = "entity";
	
	public abstract String modelName();
	public abstract AbstractCrudService<T, String> modelService();
	
	protected static final String REDIRECT = "redirect:/";
	
	@RequestMapping
	public String list(@ModelAttribute(PaginationProperty.PAGE) PaginationSupport<T> page, Model model) {
		modelService().findPage(page);
		//model.addAttribute(PaginationProperty.PAGE, page);
		return modelName() + "/list";
	}
	
	@RequestMapping(value="/{filterId}")
	public String show(@PathVariable("filterId") String filterId, Model model) {
		model.addAttribute(ENTITY, modelService().findById(filterId));
		return modelName() + "/show";
	}
	
	@RequestMapping(value="/add")
	public String add() {
		return modelName() + "/add";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String addSubmit(@Valid T entity, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return modelName() + "/add";
		} else {
			modelService().saveOrUpdate(entity);
			return REDIRECT + modelName();
		}
	}
	
	@ModelAttribute(ENTITY)
	public void getEntity(@RequestParam(value="filterId", required=false) String filterId, Model model) {
		if (StringUtils.isNotBlank(filterId)) {
			model.addAttribute(ENTITY, modelService().findById(filterId));
		}
	}
	
	@RequestMapping(value="/edit")
    public String edit(@RequestParam(value="filterId") String filterId, Model model) throws Exception {
		model.addAttribute("filterId", filterId);
        return modelName() + "/edit";
    }
	
	@RequestMapping(value="/edit", method=RequestMethod.POST)
    public String editSubmit(@Valid @ModelAttribute(ENTITY) T entity) throws Exception {
		modelService().saveOrUpdate(entity);
        return REDIRECT + modelName();
    }
	
	@RequestMapping(value="/{filterId}/delete")
    public String delete(@PathVariable("filterId") String filterId) {
		modelService().delete(filterId);
        return REDIRECT + modelName();
    }  
  
    @RequestMapping(value="/delete")
    public String batchDelete(@RequestParam("filterIds") String[] filterIds) {
        return REDIRECT + modelName();
    }  
}
