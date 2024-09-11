package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.component;

import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.component.model.ParsedValuePair;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Article;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ReferencesSeparator {

    public String removeNewLines(String input) {
        return input.replaceAll("[\n\r]", " ").replace("  ", " ");
    }

    private List<String> parseStringReferences(String text) {
        List<String> result = new ArrayList<>();

        String[] parts = text.split("(?<=\\.)\\s*(?=\\d+\\.\\s*[a-zA-ZА-Яа-я])");

        for (String part : parts) {
            result.add(part.trim());
        }

        return result;
    }

    private String cutFullText(String ref) {
        try {
            String references = removeNewLines(ref);
            String[] separated = references.split("СПИСОК ЛИТЕРАТУРЫ");

            return separated[1].split("REFERENCES")[0];
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private List<ParsedValuePair> parseBibliography(List<String> bibliography) {
        List<ParsedValuePair> parsedEntries = new ArrayList<>();

        String regex = "([А-ЯЁA-Z][а-яёa-zA-Z-]+\\s+[А-ЯA-Z]\\.\\s?(?:[А-ЯA-Z]\\.)?)\\s(.+?)\\s/\\s";
        Pattern pattern = Pattern.compile(regex);

        for (String entry : bibliography) {
            Matcher matcher = pattern.matcher(entry);
            if (matcher.find()) {
                String authors = matcher.group(1);
                String title = matcher.group(2);

                title = title.split("\\s:\\s")[0];
                title = title.replace("- ", "").toUpperCase();
                ParsedValuePair pair = new ParsedValuePair(authors, title);

                parsedEntries.add(pair);
            }
        }

        return parsedEntries;
    }

    public List<ParsedValuePair> getReferences(Article article) {
        try {
            String cutReferences = cutFullText(article.getFullText());

            List<String> stringReferences = parseStringReferences(cutReferences);

            return parseBibliography(stringReferences);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}
