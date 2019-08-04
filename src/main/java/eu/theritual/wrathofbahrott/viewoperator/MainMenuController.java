package eu.theritual.wrathofbahrott.viewoperator;
import org.springframework.stereotype.Controller;

@Controller
public class MainMenuController {
    private ViewOperator viewOperator;
    void setViewOperator(ViewOperator viewOperator) {
        this.viewOperator= viewOperator;
    }
}
