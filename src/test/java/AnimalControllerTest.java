import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.example.dao.AnimalDAO;
import org.example.service.Animal;
import org.example.service.AnimalController;
import org.example.service.CSVManager;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AnimalControllerTest {

    @Test
    public void AnimalControllerHandleCsv() {
        Request mockRequest = mock(Request.class);
        Response mockResponse = mock(Response.class);
        HttpServletRequest mockServlet = mock(HttpServletRequest.class);
        Part mockPart = mock(Part.class);


        String csvContent = "name,type,sex,weight,cost\nBuddy,cat,female,41,78\nDuke,cat,male,33,108";
        InputStream inputStream = new ByteArrayInputStream(csvContent.getBytes());

        try {
            when(mockRequest.contentType()).thenReturn("text/csv");
            when(mockRequest.raw()).thenReturn(mockServlet);
            when(mockServlet.getPart("uploaded_file")).thenReturn(mockPart);
            when(mockPart.getInputStream()).thenReturn(inputStream);

            when(CSVManager.readFromCSVRequest(inputStream)).thenReturn(anyList());
            mockResponse.status(200);

            assertEquals(200, AnimalController.handleCsv(mockRequest, mockResponse));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void AnimalControllerHandleXml() throws FileNotFoundException {
        Request mockRequest = mock(Request.class);
        Response mockResponse = mock(Response.class);
        HttpServletRequest mockServlet = mock(HttpServletRequest.class);
        Part mockPart = mock(Part.class);
        XmlMapper xmlMapper = mock(XmlMapper.class);

        File file = new File("testData/animals.xml");
        InputStream inputStream = new FileInputStream(file);

        try {
            when(mockRequest.contentType()).thenReturn("text/xml");
            when(mockRequest.raw()).thenReturn(mockServlet);
            when(mockServlet.getPart("uploaded_file")).thenReturn(mockPart);
            when(mockPart.getInputStream()).thenReturn(inputStream);
            when(xmlMapper.readValue(inputStream, any(TypeReference.class))).thenReturn(anyList());

            mockResponse.status(200);
            assertEquals(200, AnimalController.handleXml(mockRequest, mockResponse));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void handleGet() throws JsonProcessingException {
        Request mockRequest = mock(Request.class);
        Response mockResponse = mock(Response.class);

        AnimalDAO daoMock = mock(AnimalDAO.class);
        Animal animal = new Animal();
        animal.setType("cat");
        animal.setWeight(40);
        animal.setSex("male");
        animal.setCost(51);
        animal.setCategory(3);
        animal.setId(1);
        animal.setName("Milo");

        String expectedJson = new ObjectMapper().writeValueAsString(animal);
        String result = AnimalController.handleGet(mockRequest, mockResponse);
        when(daoMock.getAnimals(mockRequest,mockResponse)).thenReturn(expectedJson);
        assertTrue(result.contains(expectedJson));
    }

    @Test
    public void isValidAnimal_valid_Test(){
        Animal animal = new Animal();
        animal.setType("cat");
        animal.setWeight(40);
        animal.setSex("male");
        animal.setCost(51);
        animal.setCategory(3);
        animal.setId(1);
        animal.setName("Milo");
        assertTrue(AnimalController.isValidAnimal(animal));
    }

    @Test
    public void isValidAnimal_Novalid_Test(){
        Animal animal = new Animal();
        assertFalse(AnimalController.isValidAnimal(animal));
    }


}
