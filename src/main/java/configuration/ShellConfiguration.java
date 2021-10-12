package configuration;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.PromptProvider;

@Configuration
public class ShellConfiguration {
    @Bean
    public PromptProvider myPromptProvider() {
        return () -> new AttributedString("plantuml-generator:>", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
    }
}
