import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {
    public static List<String> readFile(String path) throws Exception {
        Path fullPath = Paths.get(path);
        System.out.println("Reading file: " + fullPath);
        try(Stream<String> lines = Files.lines(fullPath)) {
            return lines.collect(Collectors.toList());
        }catch(Exception e) {
            throw new Exception("Error reading file: " + fullPath, e);
        }
    }
}
