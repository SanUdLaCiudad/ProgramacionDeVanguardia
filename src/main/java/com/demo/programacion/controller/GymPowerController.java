package com.demo.programacion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
//@RestMapping(("/gympower")
public class GymPowerController {
	
	
		@GetMapping("/")
		public String home() {
			return "index";
		}
		
		
		@GetMapping("/login")
		public String holaMundo(@RequestParam (name="nombre", required = false, defaultValue = "Mundo") 
		String nombre, Model model) {
			model.addAttribute("nombre", nombre);//esto es para tomar el parametro que recibimos de la vista y enviarlo al archivo html en este caso el de hola			
			return "login";//aca se retorna el mismo nombre del html
		}

}
