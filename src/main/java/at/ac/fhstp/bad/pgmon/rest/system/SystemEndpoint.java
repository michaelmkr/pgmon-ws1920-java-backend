package at.ac.fhstp.bad.pgmon.rest.system;

import at.ac.fhstp.bad.pgmon.rest.system.responsemodel.Language;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/system")
public class SystemEndpoint {


    @GetMapping("/languages")
    public List<Language> getSupportedLanguages(){
        List<Language> languages = new ArrayList<>();
        Language languageDe = new Language();
        languageDe.setLanguage("de");
        Language languageEn = new Language();
        languageEn.setLanguage("en");
        languages.add(languageDe);
        languages.add(languageEn);
        return languages;
    }

}
