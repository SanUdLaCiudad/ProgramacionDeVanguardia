package com.demo.programacion.service;


import org.springframework.stereotype.Service;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
	
	
	
	public HashMap<Integer, String> obtenerDiasYHorario(String clase) throws IOException{
		FileInputStream inputStream = new FileInputStream(new File("src/main/resources/clases.xlsx"));
        Workbook workbook = new XSSFWorkbook(inputStream);
        int disciplina = Integer.parseInt(clase);
        Sheet sheet = workbook.getSheetAt(disciplina); 
        System.out.println("esto tiene int: " + disciplina);
        HashMap<Integer, String> lista = new HashMap<>();
        int id = 0;
        for (Row row : sheet) 
        {
            Cell diaCell = row.getCell(0);
            Cell horarioCell = row.getCell(1);
           
            if (diaCell != null && horarioCell != null) 
            {
                String fileDia = diaCell.getStringCellValue();
                Date fileHorario = horarioCell.getDateCellValue();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                String horaString = sdf.format(fileHorario);
                lista.put(id, fileDia + " " + horaString);
            }
            id++;
         }
        workbook.close();
        return lista;  
	}
	
	
	
	
}
