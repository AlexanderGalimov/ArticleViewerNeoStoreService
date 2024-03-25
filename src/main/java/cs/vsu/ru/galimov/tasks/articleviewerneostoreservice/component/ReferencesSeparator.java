package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.component;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ReferencesSeparator {
    public Set<String> getRelatedAuthors(String references){
        String[] separated = references.split("СПИСОК ЛИТЕРАТУРЫ");
        String rawRefs = separated[1].split("REFERENCES")[0];

        Set<String> surnames = new HashSet<>();
        Pattern patternCyrillic = Pattern.compile("[А-Я][а-я]+\\s[А-Я]\\.\\s*[А-Яа-я]+");

        Matcher matcherCyrillic = patternCyrillic.matcher(rawRefs);
        while (matcherCyrillic.find()) {
            String surname = matcherCyrillic.group();
            surnames.add(surname);
        }

        return surnames;
    }
}
