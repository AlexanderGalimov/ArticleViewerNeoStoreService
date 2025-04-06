package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.relationship;

import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.model.Article;
import cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.relationship.model.ParsedValuePair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
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
            String[] separated = references.split("СПИСОК\\s*ЛИТЕРАТУРЫ", 2);

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
            entry = entry.replaceAll("\u00AD\\s?", "");

            entry = entry.replaceAll("[\\u200B\\u200C\\u200D\\u2060\\uFEFF]", "");

            entry = entry.replace('\u00A0', ' ');

            entry = Normalizer.normalize(entry, Normalizer.Form.NFC);

            entry = entry.replaceAll("[\\-–—]\\s", "");

            Matcher matcher = pattern.matcher(entry);
            if (matcher.find()) {
                String authors = matcher.group(1);
                String title = matcher.group(2);

                title = title.split("\\s:\\s")[0];

                title = title.toUpperCase();

                parsedEntries.add(new ParsedValuePair(authors, title));
            }
        }

        return parsedEntries;
    }


    public List<ParsedValuePair> getReferences(Article article) {
        try {
            String cutReferences = cutFullText(article.getFullText());
            if (cutReferences != null) {
                List<String> stringReferences = parseStringReferences(cutReferences);

                return parseBibliography(stringReferences);
            }
        } catch (Exception e) {
            log.error("Error in get references: " + e.getMessage());
        }
        return null;
    }
}
