package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Controller
public class DemoController {
	@Autowired
	UserRepository repos;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView index(@ModelAttribute("formModel") User user, ModelAndView mav) {
		mav.setViewName("index");
		Iterable<User> list = repos.findAll();
		mav.addObject("data", list);
		return mav;
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	@Transactional
	public ModelAndView form(@ModelAttribute("formModel") User user, ModelAndView mav) {
		repos.saveAndFlush(user);
		return new ModelAndView("redirect:/");
	}
	
	@PostConstruct
	public void init() {
		//初期データ
		User user1 = new User();
		user1.setName("佐藤　翔太");
		repos.saveAndFlush(user1);
		
		user1 = new User();
		user1.setName("田中　隆志");
		repos.saveAndFlush(user1);
	}
}
