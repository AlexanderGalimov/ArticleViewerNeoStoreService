package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.component;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ReferencesSeparator {

    public String removeNewLines(String input) {
        return input.replaceAll("[\n\r]", "");
    }

    public Set<String> getRelatedAuthors(String ref){
        try{
            String references = removeNewLines(ref);
            String[] separated = references.split("СПИСОК ЛИТЕРАТУРЫ");
            String rawRefs = separated[1].split("REFERENCES")[0];

            Set<String> surnames = new HashSet<>();
            Pattern patternCyrillic1 = Pattern.compile("[А-Я][а-я]+\\s[А-Я]\\.\\s*[А-Яа-я]+");
            Pattern patternCyrillic2 = Pattern.compile("[А-Я]\\.(?:\\s*[А-Я]\\.?)+\\s*[А-Я][а-я]+");

            Matcher matcherCyrillic1 = patternCyrillic1.matcher(rawRefs);
            while (matcherCyrillic1.find()) {
                String surname = matcherCyrillic1.group();
                surnames.add(surname);
            }

            Matcher matcherCyrillic2 = patternCyrillic2.matcher(rawRefs);
            while (matcherCyrillic2.find()) {
                String surname = matcherCyrillic2.group();
                surnames.add(surname);
            }

            return surnames;
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return new HashSet<>();
    }
}
