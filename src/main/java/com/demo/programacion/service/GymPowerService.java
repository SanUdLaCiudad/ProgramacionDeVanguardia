package com.demo.programacion.service;


import org.springframework.stereotype.Service;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.demo.programacion.model.Usuario;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
	
	
	
	public HashMap<String, Integer> obtenerDiasYHorario(String clase) throws IOException{
		FileInputStream inputStream = new FileInputStream(new File("src/main/resources/clases.xlsx"));
        Workbook workbook = new XSSFWorkbook(inputStream);
        int disciplina = Integer.parseInt(clase);
        Sheet sheet = workbook.getSheetAt(disciplina); 
        System.out.println("esto tiene int: " + disciplina);
        HashMap<String, Integer> lista = new HashMap<>();
        for (Row row : sheet) 
        {
            Cell diaCell = row.getCell(0);
            Cell horarioCell = row.getCell(1);
            System.out.println("dia cell: " + diaCell);
            System.out.println("horario cell: " + horarioCell);
            if (diaCell != null && horarioCell != null) 
            {
                String fileDia = diaCell.getStringCellValue();
                int fileHorario = (int) horarioCell.getNumericCellValue();
                
                lista.put(fileDia, fileHorario);
            }
         }
        workbook.close();
        return lista;  
	}
	
	
	
	
}
