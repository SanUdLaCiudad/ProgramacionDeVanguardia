package com.demo.programacion.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

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
import org.springframework.web.bind.annotation.SessionAttributes;

import com.demo.programacion.service.*;
import com.demo.programacion.model.Clase;
import com.demo.programacion.model.Reserva;
import com.demo.programacion.model.Usuario;

@Controller
@SessionAttributes("usuario")
public class GymPowerController {
	
	@Autowired
	GymPowerService gymPowerService;
	
	
		@GetMapping("/")
		public String home() {
			return "index";
		}
		
		
		@GetMapping("/login")
		public String login(Model model) {
			//model.addAttribute("nombre", nombre);//esto es para tomar el parametro que recibimos de la vista y enviarlo al archivo html en este caso el de hola			
			model.addAttribute("usuario", new Usuario());//para crear el nuevo usuario
			return "login";//aca se retorna el mismo nombre del html
		}
		
		
		
		@PostMapping("/login")
		public String login(@ModelAttribute ("usuario") Usuario usuario, Model model) throws EncryptedDocumentException, IOException {			
			System.out.println("Us" + usuario.getEmail());
			System.out.println("pas" + usuario.getPassword());
			Usuario user = gymPowerService.obtenerUsuario(usuario);
			if(user != null)
			{
				if(usuario.getPerfil().equals("Entrenador"))
				{
					System.out.println("lo encontre y es entrenador");
					System.out.println("En login esto tiene nombre: " + usuario.getNombre());
					model.addAttribute("usuario", usuario);
					return "redirect:/entrenador";
				} else {
					System.out.println("lo encontre y es alumno");
					model.addAttribute("usuario", usuario);
					return "redirect:/alumno";
				}
			}
			System.out.println("no lo encontre");
			return "login";//aca se retorna el mismo nombre del html
		}
		
		
		
		@GetMapping("/entrenador")
		public String entrenador(@ModelAttribute ("usuario") Usuario usuario, Model model) throws IOException {
			System.out.println("Esto tiene nombre: " + usuario.getNombre());
			if(usuario.getNombre().equals("SUAREZ JULIETA"))
			{
				HashMap<Integer, Clase> listaAerobox = gymPowerService.obtenerDiasYHorario("1");
				HashMap<Integer, Clase> listaAerolocal = gymPowerService.obtenerDiasYHorario("2");
				HashMap<Integer, Clase> listaMusculacion = gymPowerService.obtenerDiasYHorario("5");
				HashMap<Integer, Clase> listaZumba = gymPowerService.obtenerDiasYHorario("8");
				model.addAttribute("nombre", usuario.getNombre());
				model.addAttribute("entrenador", 1);
				model.addAttribute("listaAerobox", listaAerobox);
				model.addAttribute("listaAerolocal", listaAerolocal);
				model.addAttribute("listaMusculacion", listaMusculacion);
				model.addAttribute("listaZumba", listaZumba);
			}
			if(usuario.getNombre().equals("PEREZ JUAN"))
			{
				HashMap<Integer, Clase> listaFuncional = gymPowerService.obtenerDiasYHorario("3");
				HashMap<Integer, Clase> listaLocalizada = gymPowerService.obtenerDiasYHorario("4");
				HashMap<Integer, Clase> listaPesas = gymPowerService.obtenerDiasYHorario("6");
				HashMap<Integer, Clase> listaSpinning = gymPowerService.obtenerDiasYHorario("7");
				model.addAttribute("nombre", usuario.getNombre());
				model.addAttribute("entrenador", 2);
				model.addAttribute("listaFuncional", listaFuncional);
				model.addAttribute("listaLocalizada", listaLocalizada);
				model.addAttribute("listaPesas", listaPesas);
				model.addAttribute("listaSpinning", listaSpinning);
			}
			
			return "entrenador";
		}
		
	
		
		
		
		@GetMapping("/alumno")
		public String alumno(@ModelAttribute ("usuario") Usuario usuario, Model model) {
			model.addAttribute("alumnoLogueado", usuario);
			System.out.println("Este es el alumno logueado en get" + usuario.getNombre());
			return "alumno";
		}
		
		Clase claseReservada = new Clase();
		HashMap<Integer, Clase> listaReservada = new HashMap<>();
		
		@PostMapping("/alumno")
		public String seleccionarClase(@ModelAttribute ("clase") Clase clase, @ModelAttribute ("usuario") Usuario usuario, Model model) throws EncryptedDocumentException, IOException {			
			System.out.println("Clase seleccionada" + clase.getDisciplina());
			model.addAttribute("alumnoLogueado", usuario);
			int alumno = usuario.getNombre().equals("GIMENEZ ZAIRA") ? 9 : 10;
			System.out.println("Este es el alumno logueado" + usuario.getNombre());
			System.out.println("Esto tiene int alumno " + alumno);
			if(clase.getDisciplina() != null) 
			{
				HashMap<Integer, Clase> lista = gymPowerService.obtenerDiasYHorario(clase.getDisciplina());
				int numero = Integer.parseInt(clase.getDisciplina());
				model.addAttribute("numero", numero);
				model.addAttribute("ocultarBoton", "display: none;");//para ocultar el boton de enviar
				model.addAttribute("lista", lista);
				System.out.println("lista: " +lista.size());
				claseReservada.setDisciplina(clase.getDisciplina());
				listaReservada.putAll(lista);
			}
			
			System.out.println("Dia seleccionado" + clase.getDia());
			if(clase.getDia()!= null) 
			{
				int diaSeleccionado = Integer.parseInt(clase.getDia());
				claseReservada.setDia(listaReservada.get(diaSeleccionado).getDia());
				HashMap<String, String> listaClases = gymPowerService.obtenerClases();
				String claseAnotada = gymPowerService.buscarClase(listaClases, claseReservada.getDisciplina());
				boolean cupo = gymPowerService.guardarCupo(claseReservada.getDisciplina(), diaSeleccionado);
				if(cupo) 
				{
					gymPowerService.guardarReserva(alumno, claseAnotada, claseReservada.getDia());
					model.addAttribute("mensaje", "Te registrate correctamente a la clase de " + claseAnotada + " el " + claseReservada.getDia() + " horas. ");
				} else {
					model.addAttribute("mensaje", "Lo sentimos, no hay mas cupo para la clase de " + claseAnotada + " el " + claseReservada.getDia() + " horas. ");
				}
				
			}
			
			return "alumno";//aca se retorna el mismo nombre del html
		}
		
		@GetMapping("/contacto")
		public String contacto() {
			return "contacto";
		}
		
		
		@GetMapping("/reservas")
		public String reservasAlumno(@ModelAttribute ("usuario") Usuario usuario, Model model) throws IOException {
			System.out.println("Esto tiene nombre: " + usuario.getNombre());
			model.addAttribute("alumnoLogueado", usuario);
			if(usuario.getNombre().equals("GIMENEZ ZAIRA"))
			{
				List<Reserva> listaAlumno1 = gymPowerService.obtenerReservasAlumno(9);
				model.addAttribute("nombre", usuario.getNombre());
				model.addAttribute("alumno", 1);
				model.addAttribute("listaAlumno1", listaAlumno1);
			}
			if(usuario.getNombre().equals("SANCHEZ ROBERTO"))
			{
				List<Reserva> listaAlumno2 = gymPowerService.obtenerReservasAlumno(10);
				model.addAttribute("nombre", usuario.getNombre());
				model.addAttribute("alumno", 2);
				model.addAttribute("listaAlumno2", listaAlumno2);
			}
			
			return "entrenador";
		}

}
