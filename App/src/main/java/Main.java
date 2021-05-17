import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Manager;
import model.Spectator;
import model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.*;
import service.MasterService;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class Main extends Application {
    private static SessionFactory getFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            return new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            System.out.println(e.getMessage() + "\n\n\n");
            StandardServiceRegistryBuilder.destroy(registry);
        }
        return null;
    }
    public static void main(String[] args) {
       launch(args);
    }

    private void showLoginWindow(MasterService service){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/loginView.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage loginStage = new Stage();
            loginStage.setTitle("Login window ");
            loginStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            loginStage.setScene(scene);
            LoginController loginController = loader.getController();
            loginController.setService(loginStage, service);
            loginStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        SessionFactory sessionFactory = getFactory();
        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.properties"));
        } catch (IOException e) {
            System.out.println("Can't find bd.properties!!!");
        }
        //IUserRepository userRepository = new UserRepository(props);
        IUserRepository userRepository = new UserHibernateRepository(sessionFactory);
        //IShowRepository showRepository = new ShowRepository(props);
        IShowRepository showRepository = new ShowHibernateRepository(sessionFactory);
        MasterService service = new MasterService(userRepository, showRepository);
        showLoginWindow(service);
    }
}
