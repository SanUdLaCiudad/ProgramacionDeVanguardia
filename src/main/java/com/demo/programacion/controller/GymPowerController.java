package com.demo.programacion.controller;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.demo.programacion.service.*;
import com.demo.programacion.model.Clase;
import com.demo.programacion.model.Usuario;

@Controller
//@RestMapping(("/gympower")
public class GymPowerController {
	
	@Autowired
	GymPowerService gymPowerService;
	
	
		@GetMapping("/")
		public String home() {
			return "index";
		}
		
		
		@GetMapping("/login")
		public String login() {
			//model.addAttribute("nombre", nombre);//esto es para tomar el parametro que recibimos de la vista y enviarlo al archivo html en este caso el de hola			
			return "login";//aca se retorna el mismo nombre del html
		}
		
		@PostMapping("/login")
		public String login(@ModelAttribute ("usuario") Usuario usuario) throws EncryptedDocumentException, IOException {			
			System.out.println("Us" + usuario.getEmail());
			System.out.println("pas" + usuario.getPassword());
			Usuario user = gymPowerService.obtenerUsuario(usuario);
			if(user != null)
			{
				if(usuario.getPerfil().equals("Entrenador"))
				{
					System.out.println("lo encontre y es entrenador");
					return "entrenador";
				} else {
					System.out.println("lo encontre y es alumno");
					return "alumno";
				}
			}
			System.out.println("no lo encontre");
			return "login";//aca se retorna el mismo nombre del html
		}
		
		
		@GetMapping("/entrenador")
		public String entrenador() {
			return "entrenador";
		}
		
		
		
		@GetMapping("/alumno")
		public String alumno() {
			return "alumno";
		}
		
		@PostMapping("/alumno")
		public String seleccionarClase(@ModelAttribute ("clase") Clase clase) throws EncryptedDocumentException, IOException {			
			System.out.println("Clase seleccionada" + clase.getDisciplina());
			return "alumno";//aca se retorna el mismo nombre del html
		}
		
		@GetMapping("/contacto")
		public String contacto() {
			return "contacto";
		}

}
