package com.apap.tutorial3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial3.model.PilotModel;
import com.apap.tutorial3.service.PilotService;

@Controller
public class PilotController {
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping("/pilot/add")
	public String add(	@RequestParam(value = "id", required = true) String id,
						@RequestParam(value = "licenseNumber", required = true) String licenseNumber,
						@RequestParam(value = "name", required = true) String name,
						@RequestParam(value = "flyHour", required = true) int flyHour) {
		PilotModel pilot = new PilotModel(id, licenseNumber, name, flyHour);
		pilotService.addPilot(pilot);
		return "add";
	}
	
	@RequestMapping("/pilot/view")
	public String view(@RequestParam("licenseNumber") String licenseNumber, Model model) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		
		model.addAttribute("pilot", archive);
		return "view-pilot";
	}
	
	@RequestMapping("/pilot/viewall")
	public String viewall(Model model) {
		List<PilotModel> archive = pilotService.getPilotList();
		
		model.addAttribute("listPilot", archive);
		return "viewall-pilot";
	}
	
	@RequestMapping("/pilot/view/license-number/{licenseNumber}")
	public String viewPilot(@PathVariable String licenseNumber, Model model) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		
		if(archive != null) {
			model.addAttribute("pilot", archive);
			return "view-pilot";
		} else {
			return "error-view";
		}
	}
	
	@RequestMapping("/pilot/update/license-number/{licenseNumber}/fly-hour/{newFlyHour}")
	public String updatePilot(	@PathVariable String licenseNumber,
								@PathVariable int newFlyHour,
								Model model) {
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		if(pilot != null) {
			pilotService.getPilotDetailByLicenseNumber(licenseNumber).setFlyHour(newFlyHour);
			
			model.addAttribute("licenseNumber", licenseNumber);
			return "update-pilot";
		} else {
			return "error-view";
		}
	}
	
	@RequestMapping("/pilot/delete/id/{id}")
	public String deletePilot(@PathVariable String id,Model model) {
		
		boolean deleteSuccess = pilotService.deletePilot(id);
		
		if(deleteSuccess) {
			model.addAttribute("id", id);
			return "delete-pilot";
		} else {
			return "error-view";
		}
	}
}
