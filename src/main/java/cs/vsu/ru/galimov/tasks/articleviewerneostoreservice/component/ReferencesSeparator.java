package cs.vsu.ru.galimov.tasks.articleviewerneostoreservice.component;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ReferencesSeparator {

    public String removeNewLines(String input) {
        return input.replaceAll("[\n\r]", " ").replace("  ", " ");
    }

    public List<String> parseReferences(String text) {
        text = cutFullText(text);

        List<String> result = new ArrayList<>();

        String[] parts = text.split("(?<=\\.)\\s*(?=\\d+\\.\\s*[a-zA-ZА-Яа-я])");

        for (String part : parts) {
            result.add(part.trim());
        }

        return result;
    }

    public String cutFullText(String ref){
        try{
            String references = removeNewLines(ref);
            String[] separated = references.split("СПИСОК ЛИТЕРАТУРЫ");

            return separated[1].split("REFERENCES")[0];
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static List<String[]> parseBibliography(List<String> bibliography) {
        List<String[]> parsedEntries = new ArrayList<>();

        String regex = "([А-ЯЁA-Z][а-яёa-zA-Z-]+\\s+[А-ЯA-Z]\\.\\s?(?:[А-ЯA-Z]\\.)?)\\s(.+?)\\s/\\s";
        Pattern pattern = Pattern.compile(regex);

        for (String entry : bibliography) {
            Matcher matcher = pattern.matcher(entry);
            if (matcher.find()) {
                String authors = matcher.group(1);
                String title = matcher.group(2);

                title = title.split("\\s:\\s")[0];
                title = title.replace("- ", "");

                parsedEntries.add(new String[]{authors, title});
            } else {
                parsedEntries.add(new String[]{"Не удалось извлечь автора", "Не удалось извлечь название"});
            }
        }

        return parsedEntries;
    }
}
