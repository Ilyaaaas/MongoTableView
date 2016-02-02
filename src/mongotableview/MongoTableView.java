package mongotableview;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

public class MongoTableView extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
        try {
            DB db = new MongoClient("localhost", 27017).getDB("UchCentre");
            
            Jongo jongo = new Jongo(db);
            
            MongoCollection studentss = jongo.getCollection("students");
            System.out.println("Найдено объектов: " + studentss.count());

            MongoCursor<Student> all = studentss.find("{}").as(Student.class);
            Student one = studentss.findOne("{name: 'Nurbek'}").as(Student.class);
            
            
        } catch(Exception e) {
            e.printStackTrace();
        }

       
    }
        static void printOne(Student doc) {
        System.out.println("_id: " + doc.getId() + ", name: " + doc.getName() + ", gruppa: "+doc.getGruppa());
        System.out.println("----------- ******** -------------");
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


    public static void main(String[] args) {
        launch(args);
     
}
}