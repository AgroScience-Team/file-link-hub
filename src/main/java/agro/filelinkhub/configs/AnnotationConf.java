package agro.filelinkhub.configs;

import agro.filelinkhub.configs.annotations.Command;
import agro.filelinkhub.configs.annotations.DomainService;
import agro.filelinkhub.configs.annotations.Query;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
    basePackages = "agro.filelinkhub",
    includeFilters = {
      @ComponentScan.Filter(type = FilterType.ANNOTATION,
        value = DomainService.class),
      @ComponentScan.Filter(type = FilterType.ANNOTATION,
        value = Command.class),
      @ComponentScan.Filter(type = FilterType.ANNOTATION,
        value = Query.class),
    }
)
public class AnnotationConf {

}