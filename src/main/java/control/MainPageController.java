package control;
import config.SessionManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import model.Model;
import view.*;

/**
 * Controller for handling actions on the main page.
 *
 * @author 
 */
public class MainPageController implements EventHandler<ActionEvent> {

    private final MainPageView mainPageView;
    private final Model model;

    /**
     * Constructs a MainPageController with a MainPageView instance.
     *
     * @param mainPageView The MainPageView associated with this controller.
     */
    public MainPageController(MainPageView mainPageView) {
        this.mainPageView = mainPageView;
        this.model = new Model();
    }

    /**
     * Handles button click events and other actions from the MainPageView.
     *
     * @param event The ActionEvent representing the button click or action.
     */
    @Override
    public void handle(ActionEvent event) {
        // Handle choose recipe button click
        if (event.getSource() == mainPageView.chooseRecipe) {
            RecipeSelectView recipeSelectView = new RecipeSelectView();
            recipeSelectView.show();
        }
        // Handle add recipe button click
        else if (event.getSource() == mainPageView.addRecipe) {
            RecipeCreateView recipeCreateView = new RecipeCreateView();
            recipeCreateView.show();
        }
    }

}
