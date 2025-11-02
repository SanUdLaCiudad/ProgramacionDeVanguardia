package com.demo.programacion.service;


import org.springframework.stereotype.Service;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.demo.programacion.model.Usuario;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
	
	
	
	
}
