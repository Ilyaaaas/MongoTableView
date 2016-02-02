package mongotableview;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import static mongotableview.MongoTableView.printOne;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.bson.types.ObjectId;

public class FXMLDocumentController implements Initializable {
    
    private ObservableList<Student> studentsData = FXCollections.observableArrayList();
    private PreparedStatement prep;
    private static Connection studentss;
    
    
    @FXML
    private ListView tableStudents;
    
    @FXML
    private TableColumn id;
    
    @FXML
    private TableColumn name;
        
    @FXML
    private TableColumn gruppa;
    
    @FXML
    private TextField tfName;
    
    @FXML
    private TextField tfGruppa;
    
    @FXML
    private TextField search;
    
    @FXML
    private Button btn;
    
    @FXML
    private Button btnUp;
    
    @FXML
    private void id(ActionEvent event) {
    }
    
    @FXML
    private void name(ActionEvent event) {
        
    }
    
    @FXML
    private void tfName(ActionEvent event) {
        
    }
    
    @FXML
    private void tfGruppa(ActionEvent event) {
        
    }
    
        @FXML
    private void search(ActionEvent event) {
        
    }
    
    @FXML
    private void btn(ActionEvent event) {
        System.out.println("Добавление");
            
         try {
            DB db = new MongoClient("localhost", 27017).getDB("UchCentre");
            
            Jongo jongo = new Jongo(db);
            
            MongoCollection studentss = jongo.getCollection("students");
            System.out.println("Найдено объектов: " + studentss.count());

            MongoCursor<Student> all = studentss.find("{}").as(Student.class);
            
            //создаем новый обьект "СТУДЕНТ"
            studentsData.add(new Student(new ObjectId(), tfName.getText(), tfGruppa.getText()));
            
            //отправляем в монго
            studentss.save(new Student(new ObjectId(), tfName.getText(), tfGruppa.getText()));
            
            //отпарвляем обект "студент" в TableView 
            tableStudents.setItems(studentsData);
                        
            //чистим поля ввода
            tfName.setText(null);
            tfGruppa.setText(null); 
            
        
    }   catch (UnknownHostException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void btnUp(ActionEvent event) {
        System.out.println("Обновление");
            
         try {
            DB db = new MongoClient("localhost", 27017).getDB("UchCentre");
            
            Jongo jongo = new Jongo(db);
            
            MongoCollection studentss = jongo.getCollection("students");
            System.out.println("Найдено объектов: " + studentss.count());
            
            MongoCursor<Student> all = studentss.find("{}").as(Student.class);
            
            //создаем новый обьект "СТУДЕНТ"
            studentsData.add(new Student(new ObjectId(), tfName.getText(), tfGruppa.getText()));
            
            //обновляем в монго
            studentss.update("{name: '"+search.getText()+ "'}").with(new Student(new ObjectId(), tfName.getText(), tfGruppa.getText()));
            //studentss.save(new Student(new ObjectId(), tfName.getText(), tfGruppa.getText()));
            
            //отпарвляем обьект "студент" в TableView 
            tableStudents.setItems(studentsData);
                        
            //чистим поля ввода
            tfName.setText(null);
            tfGruppa.setText(null); 
            
            id.setCellValueFactory(new PropertyValueFactory<Student, Integer>("firstName"));
            name.setCellValueFactory(new PropertyValueFactory<Student, String>("secondName"));
            gruppa.setCellValueFactory(new PropertyValueFactory<Student, String>("age"));
        
    }   catch (UnknownHostException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void btn1(ActionEvent event) {
        System.out.println("Удаление");
    }
    
    
    
    @FXML
    private void gruppa(ActionEvent event) {
       
    }
    
    @FXML
    private void tableStudents(ActionEvent event) {
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initData();
        try {
            DB db = new MongoClient("localhost", 27017).getDB("UchCentre");
            
            Jongo jongo = new Jongo(db);
            
            MongoCollection studentss = jongo.getCollection("students");
            System.out.println("Найдено объектов: " + studentss.count());

            MongoCursor<Student> all = studentss.find("{}").as(Student.class);
            
            
//            Student one = studentss.findOne("{name: 'Nurbek'}").as(Student.class);
            
            
            tableStudents.setItems((ObservableList) all);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        }
    
        static void printAll(MongoCursor<Student> cursor) {
        try {
            System.out.println("----------- printAll -------------");
            while (cursor.hasNext()) {       
                Student doc = cursor.next(); // извлекаем текущий документ и перемещаем курсор к следующему документу в наборе
                System.out.println("_id: " + doc.getId() + ", name: " + doc.getName() + ", gruppa: "+doc.getGruppa());
                
                //(new StudentData(cursor.getInt("id"), cursor.getString("name"), cursor.getString("gruppa")));
                
            }
        } finally {
            System.out.println("----------- ******** -------------");
                try { 
                    cursor.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        private void initData(){
            
        
        }
    }
