import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.shell.jline.PromptProvider;

@SpringBootApplication
@ComponentScan(basePackages = {
        "controller",
        "service",
        "configuration"
})
public class ErdPlantUmlApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ErdPlantUmlApplication.class, args);
    }
}
