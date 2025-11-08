package com.demo.programacion.service;


import org.springframework.stereotype.Service;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.demo.programacion.model.Clase;
import com.demo.programacion.model.Usuario;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Service
public class GymPowerService {
	
	
	public Usuario obtenerUsuario(Usuario usuario) throws EncryptedDocumentException, IOException {
		FileInputStream inputStream = new FileInputStream(new File("src/main/resources/login.xlsx"));
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0); // Asume que los datos están en la primera hoja

        for (Row row : sheet) 
        {
            // Asume que la primera columna es el nombre de usuario y la segunda es la contraseña
            Cell emailCell = row.getCell(0);
            Cell passwordCell = row.getCell(1);
            Cell perfilCell = row.getCell(2);

            if (emailCell != null && passwordCell != null) 
            {
                String fileEmail = emailCell.getStringCellValue();
                int filePassword = (int)passwordCell.getNumericCellValue();
                String filePerfil = perfilCell.getStringCellValue();

                if (fileEmail.equals(usuario.getEmail()) && filePassword == usuario.getPassword())
                {
                	usuario.setPerfil(filePerfil);
                	workbook.close();
                	return usuario;
                }

            }
        }
        
        workbook.close();
        return null;
	}
	
	
	
	public HashMap<Integer, Clase> obtenerDiasYHorario(String disciplina) throws IOException{
		FileInputStream inputStream = new FileInputStream(new File("src/main/resources/clases.xlsx"));
        Workbook workbook = new XSSFWorkbook(inputStream);
        int hojaDisciplina = Integer.parseInt(disciplina);
        Sheet sheet = workbook.getSheetAt(hojaDisciplina); 
        
        HashMap<Integer, Clase> lista = new HashMap<>();
        int id = 0;
        for (Row row : sheet) 
        {
            Cell diaCell = row.getCell(0);
            Cell horarioCell = row.getCell(1);
            Cell reservasCell = row.getCell(2);
            Cell entrenadorCell = row.getCell(3);
           
            if (diaCell != null && horarioCell != null && reservasCell != null && entrenadorCell != null) 
            {
                String fileDia = diaCell.getStringCellValue();
                Date fileHorario = horarioCell.getDateCellValue();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                String horaString = sdf.format(fileHorario);
                String dia = fileDia + " " + horaString;
                int fileReserva = (int)reservasCell.getNumericCellValue();
                String fileEntrenador = entrenadorCell.getStringCellValue();
                Clase clase = new Clase(disciplina, dia, fileReserva, fileEntrenador);
                System.out.println("Esto tiene clase: " + disciplina + dia + fileReserva + fileEntrenador);
                lista.put(id, clase);
            }
            id++;
         }
        workbook.close();
        return lista;  
	}
	
	
	
	
}
